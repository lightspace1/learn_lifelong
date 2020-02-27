package com.lightspace.framework.domain.cms.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryPageRequest {
    @ApiModelProperty("site id")
    private String siteId;

    private String pageId;

    private String pageName;

    private String pageAliase;

    private String templateId;
}
