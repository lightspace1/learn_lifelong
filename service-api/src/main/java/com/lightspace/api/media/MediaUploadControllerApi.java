package com.lightspace.api.media;

import com.lightspace.framework.domain.media.response.CheckChunkResult;
import com.lightspace.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value =
        "Media Asset Management Interface",description =
        "Media asset management interface, providing file upload, processing and other interfaces")
public interface MediaUploadControllerApi {

  //文件上传前的准备工作,校验文件是否存在
  @ApiOperation(
          "File upload registration")
  public ResponseResult register(String fileMd5,
                                 String fileName,
                                 Long fileSize,
                                 String mimetype,
                                 String fileExt);

  @ApiOperation("Verify the existence of chunked files")
  public CheckChunkResult checkchunk(String fileMd5,
                                     Integer chunk,
                                     Integer chunkSize);

  @ApiOperation(
          "Upload chunks")
  public ResponseResult uploadchunk(MultipartFile file,
                                    String fileMd5,
                                    Integer chunk);

  @ApiOperation("Merge chunks")
  public ResponseResult mergechunks(String fileMd5,
                                    String fileName,
                                    Long fileSize,
                                    String mimetype,
                                    String fileExt);

}
