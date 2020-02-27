package com.lightspace.framework.domain.media.request;

import com.lightspace.framework.model.request.RequestData;
import lombok.Data;

@Data
public class QueryMediaFileRequest extends RequestData {

  private String fileOriginalName;
  private String processStatus;
  private String tag;

}
