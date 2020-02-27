package com.lightspace.framework.domain.filesystem.response;

import com.google.common.collect.ImmutableMap;
import com.lightspace.framework.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum FileSystemCode implements ResultCode {
    FS_UPLOADFILE_FILEISNULL(false,25001,"\n" +
            "The uploaded file is null!"),
    FS_UPLOADFILE_BUSINESSISNULL(false,25002,"\n" +
            "Business Id is null!"),
    FS_UPLOADFILE_SERVERFAIL(false,25003,"\n" +
            "Upload file failed!"),
    FS_DELETEFILE_NOTEXISTS(false,25004,"删除的文件不存在!"),
    FS_DELETEFILE_DBFAIL(false,25005,"\n" +
            "Failed to delete file information!"),
    FS_DELETEFILE_SERVERFAIL(false,25006,"\n" +
            "Failed to delete file!"),
    FS_UPLOADFILE_METAERROR(false,25007,"\n" +
            "Please use the json format for the meta data of the uploaded file!"),
    FS_UPLOADFILE_USERISNULL(false,25008,"\n" +
            "Upload fail : user is null!"),
    FS_INITFDFSERROR(false,25009,"\n" +
            "Error: initializing fastDFS environment！");


    boolean success;
    int code;
    String message;
    private FileSystemCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, FileSystemCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, FileSystemCode> builder = ImmutableMap.builder();
        for (FileSystemCode commonCode : values()) {
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
