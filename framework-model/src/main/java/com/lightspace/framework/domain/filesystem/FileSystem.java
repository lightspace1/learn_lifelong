package com.lightspace.framework.domain.filesystem;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {

    @Id
    private String fileId;
    private String filePath;
    private long fileSize;
    private String fileName;
    private String fileType;
    private int fileWidth;
    private int fileHeight;
    private String userId;
    private String businesskey;
    private String filetag;
    private Map metadata;

}

