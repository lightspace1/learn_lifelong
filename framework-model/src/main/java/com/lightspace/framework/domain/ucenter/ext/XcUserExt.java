package com.lightspace.framework.domain.ucenter.ext;

import com.lightspace.framework.domain.ucenter.XcMenu;
import com.lightspace.framework.domain.ucenter.XcUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class XcUserExt extends XcUser {


  private List<XcMenu> permissions;

  private String companyId;
}
