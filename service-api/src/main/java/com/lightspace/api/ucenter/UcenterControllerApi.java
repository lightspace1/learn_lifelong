package com.lightspace.api.ucenter;

import com.lightspace.framework.domain.ucenter.ext.XcUserExt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value =
        "User center",description =
        "User Center Management")
public interface UcenterControllerApi {
    @ApiOperation(
            "Get user information based on user account")
    public XcUserExt getUserext(String username);
}
