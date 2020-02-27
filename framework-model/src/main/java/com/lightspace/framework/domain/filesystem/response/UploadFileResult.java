package com.lightspace.framework.domain.filesystem.response;

import com.lightspace.framework.domain.filesystem.FileSystem;
import com.lightspace.framework.model.response.ResponseResult;
import com.lightspace.framework.model.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UploadFileResult extends ResponseResult {
    @ApiModelProperty(value = "File information", example = "true", required = true)
    FileSystem fileSystem;
    public UploadFileResult(ResultCode resultCode, FileSystem fileSystem) {
        super(resultCode);
        this.fileSystem = fileSystem;
    }

}