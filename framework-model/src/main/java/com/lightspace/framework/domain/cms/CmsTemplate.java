package com.lightspace.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document(collection = "cms_template")
public class CmsTemplate {


    private String siteId;

    @Id
    private String templateId;

    private String templateName;

    private String templateParameter;

    private String templateFileId;
}
