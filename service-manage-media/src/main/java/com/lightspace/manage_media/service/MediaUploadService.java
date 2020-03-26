package com.lightspace.manage_media.service;

import com.alibaba.fastjson.JSON;
import com.lightspace.framework.domain.media.MediaFile;
import com.lightspace.framework.domain.media.response.CheckChunkResult;
import com.lightspace.framework.domain.media.response.MediaCode;
import com.lightspace.framework.exception.ExceptionCast;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.manage_media.config.RabbitMQConfig;
import com.lightspace.manage_media.dao.MediaFileRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
public class MediaUploadService {

  @Autowired
  MediaFileRepository mediaFileRepository;

  @Value("${lightspace.upload-location}")
  String upload_location;
  @Value("${lightspace.mq.routingkey-media-video}")
  String routingkey_media_video;

  @Autowired
  RabbitTemplate rabbitTemplate;


  private String getFileFolderPath(String fileMd5){
    return  upload_location + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/";
  }


  private String getFilePath(String fileMd5,String fileExt){
    return upload_location + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/" + fileMd5 + "." +fileExt;
  }


  private String getChunkFileFolderPath(String fileMd5){
    return  upload_location + fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/chunk/";
  }

  public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {


    String fileFolderPath = this.getFileFolderPath(fileMd5);

    String filePath =this.getFilePath(fileMd5,fileExt);
    File file = new File(filePath);

    boolean exists = file.exists();

    Optional<MediaFile> optional = mediaFileRepository.findById(fileMd5);
    if(exists && optional.isPresent()){

      ExceptionCast.cast(MediaCode.UPLOAD_FILE_REGISTER_EXIST);
    }

    File fileFolder = new File(fileFolderPath);
    if(!fileFolder.exists()){
      fileFolder.mkdirs();
    }

    return new ResponseResult(CommonCode.SUCCESS);
  }


  public CheckChunkResult checkchunk(String fileMd5, Integer chunk, Integer chunkSize) {

    String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);

    File chunkFile = new File(chunkFileFolderPath + chunk);
    if(chunkFile.exists()){

      return new CheckChunkResult(CommonCode.SUCCESS,true);
    }else{

      return new CheckChunkResult(CommonCode.SUCCESS,false);
    }

  }


  public ResponseResult uploadchunk(MultipartFile file, String fileMd5, Integer chunk) {

    String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);

    String chunkFilePath = chunkFileFolderPath + chunk;

    File chunkFileFolder = new File(chunkFileFolderPath);

    if(!chunkFileFolder.exists()){
      chunkFileFolder.mkdirs();
    }

    InputStream inputStream = null;
    FileOutputStream outputStream  =null;
    try {
      inputStream = file.getInputStream();
      outputStream = new FileOutputStream(new File(chunkFilePath));
      IOUtils.copy(inputStream,outputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        inputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        outputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new ResponseResult(CommonCode.SUCCESS);

  }


  private boolean checkFileMd5(File mergeFile,String md5){

    try {
      FileInputStream inputStream = new FileInputStream(mergeFile);
      String md5Hex = DigestUtils.md5Hex(inputStream);
      if(md5.equalsIgnoreCase(md5Hex)){
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;

  }


  public ResponseResult mergechunks(String fileMd5, String fileName, Long fileSize, String mimetype, String fileExt) {


    String chunkFileFolderPath = this.getChunkFileFolderPath(fileMd5);
    File chunkFileFolder = new File(chunkFileFolderPath);
    File[] files = chunkFileFolder.listFiles();
    List<File> fileList = Arrays.asList(files);

    String filePath = this.getFilePath(fileMd5, fileExt);
    File mergeFile = new File(filePath);

    mergeFile = this.mergeFile(fileList, mergeFile);
    if(mergeFile == null){
      ExceptionCast.cast(MediaCode.MERGE_FILE_FAIL);
    }

    boolean checkFileMd5 = this.checkFileMd5(mergeFile, fileMd5);
    if(!checkFileMd5){

      ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
    }

    MediaFile mediaFile = new MediaFile();
    mediaFile.setFileId(fileMd5);
    mediaFile.setFileOriginalName(fileName);
    mediaFile.setFileName(fileMd5 + "." +fileExt);
    String filePath1 = fileMd5.substring(0,1) + "/" + fileMd5.substring(1,2) + "/" + fileMd5 + "/";
    mediaFile.setFilePath(filePath1);
    mediaFile.setFileSize(fileSize);
    mediaFile.setUploadTime(new Date());
    mediaFile.setMimeType(mimetype);
    mediaFile.setFileType(fileExt);
    mediaFile.setFileStatus("301002");
    mediaFileRepository.save(mediaFile);
    sendProcessVideoMsg(mediaFile.getFileId());
    return new ResponseResult(CommonCode.SUCCESS);
  }

  public ResponseResult sendProcessVideoMsg(String mediaId){
    Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
    if(!optional.isPresent()){
      ExceptionCast.cast(CommonCode.FAIL);
    }
    Map<String,String> map = new HashMap<>();
    map.put("mediaId",mediaId);
    String jsonString = JSON.toJSONString(map);
    try {
      rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK,routingkey_media_video,jsonString);
    } catch (AmqpException e) {
      e.printStackTrace();
      return new ResponseResult(CommonCode.FAIL);
    }

    return new ResponseResult(CommonCode.SUCCESS);
  }


  private File mergeFile(List<File> chunkFileList, File mergeFile) {
    try {
      if (mergeFile.exists()) {
        mergeFile.delete();
      } else {
        mergeFile.createNewFile();
      }

      Collections.sort(chunkFileList, new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
          if(Integer.parseInt(o1.getName())>Integer.parseInt(o2.getName())){
            return 1;
          }
          return -1;

        }
      });
      RandomAccessFile raf_write = new RandomAccessFile(mergeFile,"rw");
      byte[] b = new byte[1024];
      for(File chunkFile:chunkFileList){
        RandomAccessFile raf_read = new RandomAccessFile(chunkFile,"r");
        int len = -1;
        while ((len = raf_read.read(b))!=-1){
          raf_write.write(b,0,len);
        }
        raf_read.close();
      }
      raf_write.close();
      return mergeFile;
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
