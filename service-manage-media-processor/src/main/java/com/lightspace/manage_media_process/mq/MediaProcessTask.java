package com.lightspace.manage_media_process.mq;

import com.alibaba.fastjson.JSON;
import com.lightspace.framework.domain.media.MediaFile;
import com.lightspace.framework.domain.media.MediaFileProcess_m3u8;
import com.lightspace.framework.utils.HlsVideoUtil;
import com.lightspace.framework.utils.Mp4VideoUtil;
import com.lightspace.manage_media_process.dao.MediaFileRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MediaProcessTask {

  @Value("${lightspace.ffmpeg-path}")
  String ffmpeg_path;

  @Value("${lightspace.video-location}")
  String serverPath;

  @Autowired
  MediaFileRepository mediaFileRepository;

  @RabbitListener(queues="${lightspace.mq.queue-media-video-processor}",containerFactory = "customContainerFactory")
  public void receiveMediaProcessTask(String msg){

    Map map = JSON.parseObject(msg, Map.class);
    String mediaId = (String) map.get("mediaId");
    Optional<MediaFile> optional = mediaFileRepository.findById(mediaId);
    if(!optional.isPresent()){
      return ;
    }
    MediaFile mediaFile = optional.get();
    String fileType = mediaFile.getFileType();
    if(!fileType.equals("avi")){
      mediaFile.setProcessStatus("303004");
      mediaFileRepository.save(mediaFile);
      return ;
    }else{
      mediaFile.setProcessStatus("303001");
      mediaFileRepository.save(mediaFile);
    }

    String video_path = serverPath + mediaFile.getFilePath() + mediaFile.getFileName();
    String mp4_name = mediaFile.getFileId() + ".mp4";
    String mp4folder_path = serverPath + mediaFile.getFilePath();
    Mp4VideoUtil mp4VideoUtil =new Mp4VideoUtil(ffmpeg_path,video_path,mp4_name,mp4folder_path);
    String result = mp4VideoUtil.generateMp4();
    if(result == null || !result.equals("success")){
      mediaFile.setProcessStatus("303003");
      MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
      mediaFileProcess_m3u8.setErrormsg(result);
      mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
      mediaFileRepository.save(mediaFile);
      return ;
    }

    String mp4_video_path = serverPath + mediaFile.getFilePath() + mp4_name;
    String m3u8_name = mediaFile.getFileId() +".m3u8";
    String m3u8folder_path = serverPath + mediaFile.getFilePath() + "hls/";
    HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(ffmpeg_path,mp4_video_path,m3u8_name,m3u8folder_path);
    String tsResult = hlsVideoUtil.generateM3u8();
    if(tsResult == null || !tsResult.equals("success")){
      mediaFile.setProcessStatus("303003");
      MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
      mediaFileProcess_m3u8.setErrormsg(result);
      mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
      mediaFileRepository.save(mediaFile);
      return ;
    }
    List<String> ts_list = hlsVideoUtil.get_ts_list();

    mediaFile.setProcessStatus("303002");
    MediaFileProcess_m3u8 mediaFileProcess_m3u8 = new MediaFileProcess_m3u8();
    mediaFileProcess_m3u8.setTslist(ts_list);
    mediaFile.setMediaFileProcess_m3u8(mediaFileProcess_m3u8);
    String fileUrl =mediaFile.getFilePath() + "hls/"+m3u8_name;
    mediaFile.setFileUrl(fileUrl);
    mediaFileRepository.save(mediaFile);
  }
}
