package com.lightspace.framework.domain.media;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MediaFileProcess_m3u8 extends MediaFileProcess {

  private List<String> tslist;

}
