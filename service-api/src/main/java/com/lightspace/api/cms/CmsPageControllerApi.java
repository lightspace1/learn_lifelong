package com.lightspace.api.cms;

import com.lightspace.framework.domain.cms.CmsPage;
import com.lightspace.framework.domain.cms.request.QueryPageRequest;
import com.lightspace.framework.domain.cms.response.CmsPageResult;
import com.lightspace.framework.domain.cms.response.CmsPostPageResult;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value=
        "cms page management interface",description =
        "cms page management interface, providing page addition, deletion, modification, check")
public interface CmsPageControllerApi {
    @ApiOperation(
            "Pagination query list")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value =
                    "page number",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value =
                    "Records per page",required=true,paramType="path",dataType="int")
    })
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest) ;

    @ApiOperation("add page")
    public CmsPageResult add(CmsPage cmsPage);

    //根据页面id查询页面信息
    @ApiOperation("Query page information based on page id")
    public CmsPage findById(String id);
    //修改页面
    @ApiOperation(
            "Edit page")
    public CmsPageResult edit(String id,CmsPage cmsPage);
    //删除页面
    @ApiOperation(
            "Delete page")
    public ResponseResult delete(String id);

    @ApiOperation("Publish page")
    public ResponseResult post(String pageId);
    @ApiOperation("Save page")
    public CmsPageResult save(CmsPage cmsPage);


    @ApiOperation("Quick publish page")
    public CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
