package com.lightspace.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@ToString
@Document(collection = "cms_site")
public class CmsSite {


    @Id
    private String siteId;
    private String siteName;
    private String siteDomain;
    private String sitePort;
    private String siteWebPath;
    private Date siteCreateTime;
    private String sitePhysicalPath;

}