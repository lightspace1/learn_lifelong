package com.lightspace.framework.domain.cms;

import com.lightspace.framework.domain.cms.response.CmsConfigModel;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document(collection = "cms_config")
public class CmsConfig {

    @Id
    private String id;
    private String name;
    private List<CmsConfigModel> model;

}

