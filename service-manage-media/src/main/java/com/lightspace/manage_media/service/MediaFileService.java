package com.lightspace.manage_media.service;

import com.lightspace.framework.domain.media.MediaFile;
import com.lightspace.framework.domain.media.request.QueryMediaFileRequest;
import com.lightspace.framework.model.response.CommonCode;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.framework.model.response.QueryResult;
import com.lightspace.manage_media.dao.MediaFileRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaFileService {

  @Autowired
  MediaFileRepository mediaFileRepository;

  public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
    if(queryMediaFileRequest == null){
      queryMediaFileRequest = new QueryMediaFileRequest();
    }

    MediaFile mediaFile = new MediaFile();
    if(StringUtils.isNotEmpty(queryMediaFileRequest.getTag())){
      mediaFile.setTag(queryMediaFileRequest.getTag());
    }
    if(StringUtils.isNotEmpty(queryMediaFileRequest.getFileOriginalName())){
      mediaFile.setFileOriginalName(queryMediaFileRequest.getFileOriginalName());
    }
    if(StringUtils.isNotEmpty(queryMediaFileRequest.getProcessStatus())){
      mediaFile.setProcessStatus(queryMediaFileRequest.getProcessStatus());
    }

    ExampleMatcher exampleMatcher = ExampleMatcher.matching()
      .withMatcher("tag",ExampleMatcher.GenericPropertyMatchers.contains())
      .withMatcher("fileOriginalName",ExampleMatcher.GenericPropertyMatchers.contains());


    Example<MediaFile> example = Example.of(mediaFile,exampleMatcher);
    if(page<=0){
      page = 1;
    }
    page = page-1;
    if(size<=0){
      size = 10;
    }
    Pageable pageable = new PageRequest(page,size);
    Page<MediaFile> all = mediaFileRepository.findAll(example, pageable);

    long total = all.getTotalElements();

    List<MediaFile> content = all.getContent();

    QueryResult<MediaFile> queryResult = new QueryResult<>();
    queryResult.setList(content);
    queryResult.setTotal(total);

    QueryResponseResult queryResponseResult = new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    return queryResponseResult;
  }
}
