package com.lightspace.api.cms;

import com.lightspace.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="cms config management interface",description =
        "cms configuration management interface, providing data model management, query interface")
public interface CmsConfigControllerApi {
    @ApiOperation("Query CMS configuration information based on id")
    public CmsConfig getmodel(String id);
}