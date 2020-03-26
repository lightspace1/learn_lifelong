package com.lightspace.manage_media.controller;

import com.lightspace.api.media.MediaFileControllerApi;
import com.lightspace.framework.domain.media.MediaFile;
import com.lightspace.framework.domain.media.request.QueryMediaFileRequest;
import com.lightspace.framework.model.response.QueryResponseResult;
import com.lightspace.manage_media.service.MediaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/media/file")
public class MediaFileController implements MediaFileControllerApi {
  @Autowired
  MediaFileService mediaFileService;

  @Override
  @GetMapping("/list/{page}/{size}")
  public QueryResponseResult<MediaFile> findList(int page, int size, QueryMediaFileRequest queryMediaFileRequest) {
    return mediaFileService.findList(page,size,queryMediaFileRequest);
  }
}
