package com.lightspace.api.media;

import com.lightspace.framework.domain.media.MediaFile;
import com.lightspace.framework.domain.media.request.QueryMediaFileRequest;
import com.lightspace.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value =
        "Media file management",description =
        "Media file management interface",tags = {
        "Media file management interface"})
public interface MediaFileControllerApi {

  @ApiOperation(
          "My media assets file query list")
  public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest);

}
