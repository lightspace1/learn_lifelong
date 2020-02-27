package com.lightspace.framework.domain.media.response;

import com.google.common.collect.ImmutableMap;
import com.lightspace.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@ToString
public enum MediaCode implements ResultCode {
  UPLOAD_FILE_REGISTER_FAIL(false,22001,
          "The upload file failed to register in the system, please refresh the page and try again!"),
  UPLOAD_FILE_REGISTER_EXIST(false,22002,
          "The uploaded file already exists in the system!"),
  CHUNK_FILE_EXIST_CHECK(true,22003,
          "The file block already exists in the system!"),
  MERGE_FILE_FAIL(false,22004,
          "Failed to merge files, the file already exists in the system!"),
  MERGE_FILE_CHECKFAIL(false,22005,
          "Merge file verification failed!");

  //操作代码
  @ApiModelProperty(value =
          "Whether the operation of the media asset system is successful", example = "true", required = true)
  boolean success;

  //操作代码
  @ApiModelProperty(value = "Media Asset System Operation Code", example = "22001", required = true)
  int code;
  //提示信息
  @ApiModelProperty(value =
          "Tips for media asset system operation", example =
          "The file already exists in the system!", required = true)
  String message;
  private MediaCode(boolean success,int code, String message){
    this.success = success;
    this.code = code;
    this.message = message;
  }
  private static final ImmutableMap<Integer, MediaCode> CACHE;

  static {
    final ImmutableMap.Builder<Integer, MediaCode> builder = ImmutableMap.builder();
    for (MediaCode commonCode : values()) {
      builder.put(commonCode.code(), commonCode);
    }
    CACHE = builder.build();
  }

  @Override
  public boolean success() {
    return success;
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }
}
