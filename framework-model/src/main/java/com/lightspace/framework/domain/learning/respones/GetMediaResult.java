package com.lightspace.framework.domain.learning.respones;

import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {
  String fileUrl;
  public GetMediaResult(ResultCode resultCode, String fileUrl){
    super(resultCode);
    this.fileUrl = fileUrl;
  }
}
