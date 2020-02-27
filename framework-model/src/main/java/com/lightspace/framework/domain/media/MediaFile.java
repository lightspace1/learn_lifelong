package com.lightspace.framework.domain.media;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@Document(collection = "media_file")
public class MediaFile {

  @Id
  private String fileId;
  private String fileName;
  private String fileOriginalName;
  private String filePath;
  private String fileUrl;
  private String fileType;
  private String mimeType;
  private Long fileSize;
  private String fileStatus;
  private Date uploadTime;
  private String processStatus;
  private MediaFileProcess_m3u8 mediaFileProcess_m3u8;

  private String tag;


}
