package com.lightspace.framework.domain.media.response;

import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CheckChunkResult extends ResponseResult {

  public CheckChunkResult(ResultCode resultCode, boolean fileExist) {
    super(resultCode);
    this.fileExist = fileExist;
  }
  @ApiModelProperty(value = "\n" +
          "File block existence mark", example = "true", required = true)
  boolean fileExist;
}
