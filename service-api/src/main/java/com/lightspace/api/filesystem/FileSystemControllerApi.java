package com.lightspace.api.filesystem;

import com.lightspace.framework.domain.filesystem.response.UploadFileResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(value= "File management interface",description =
        "File management interface, providing file addition, deletion, modification and checking")
public interface FileSystemControllerApi {
    @ApiOperation(
            "Upload file interface")
    public UploadFileResult upload(MultipartFile multipartFile,
                                   String filetag,
                                   String businesskey,
                                   String metadata);

}
