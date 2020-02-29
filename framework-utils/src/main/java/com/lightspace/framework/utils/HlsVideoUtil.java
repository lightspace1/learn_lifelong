package com.lightspace.framework.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HlsVideoUtil extends VideoUtil {

  String ffmpeg_path = "D:\\Program Files\\ffmpeg-20180227-fa0c9d6-win64-static\\bin\\ffmpeg.exe";//path for ffmpeg
  String video_path = "D:\\BaiduNetdiskDownload\\test1.avi";
  String m3u8_name = "test1.m3u8";
  String m3u8folder_path = "D:/BaiduNetdiskDownload/Movies/test1/";
  public HlsVideoUtil(String ffmpeg_path, String video_path, String m3u8_name,String m3u8folder_path){
    super(ffmpeg_path);
    this.ffmpeg_path = ffmpeg_path;
    this.video_path = video_path;
    this.m3u8_name = m3u8_name;
    this.m3u8folder_path = m3u8folder_path;
  }

  private void clear_m3u8(String m3u8_path){
    //删除原来已经生成的m3u8及ts文件
    File m3u8dir = new File(m3u8_path);
    if(!m3u8dir.exists()){
      m3u8dir.mkdirs();
    }
  }

  /**
   * generate m3u8 file
   * @return return success if success，return log if fail
   */
  public String generateM3u8(){
    clear_m3u8(m3u8folder_path);
    List<String> commend = new ArrayList<String>();
    commend.add(ffmpeg_path);
    commend.add("-i");
    commend.add(video_path);
    commend.add("-hls_time");
    commend.add("10");
    commend.add("-hls_list_size");
    commend.add("0");
    commend.add("-hls_segment_filename");
    commend.add(m3u8folder_path  + m3u8_name.substring(0,m3u8_name.lastIndexOf(".")) + "_%05d.ts");
    commend.add(m3u8folder_path  + m3u8_name );
    String outstring = null;
    try {
      ProcessBuilder builder = new ProcessBuilder();
      builder.command(commend);
      builder.redirectErrorStream(true);
      Process p = builder.start();
      outstring = waitFor(p);

    } catch (Exception ex) {

      ex.printStackTrace();

    }
    Boolean check_video_time = check_video_time(video_path, m3u8folder_path + m3u8_name);
    if(!check_video_time){
      return outstring;
    }
    List<String> ts_list = get_ts_list();
    if(ts_list == null){
      return outstring;
    }
    return "success";


  }



  /**
   * check the status of video processing
   * @return ts list
   */
  public List<String> get_ts_list() {
    List<String> fileList = new ArrayList<String>();
    List<String> tsList = new ArrayList<String>();
    String m3u8file_path =m3u8folder_path + m3u8_name;
    BufferedReader br = null;
    String str = null;
    String bottomline = "";
    try {
      br = new BufferedReader(new FileReader(m3u8file_path));
      while ((str = br.readLine()) != null) {
        bottomline = str;
        if(bottomline.endsWith(".ts")){
          tsList.add(bottomline);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(br!=null){
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    if (bottomline.contains("#EXT-X-ENDLIST")) {
      fileList.addAll(tsList);
      return fileList;
    }
    return null;

  }
}
