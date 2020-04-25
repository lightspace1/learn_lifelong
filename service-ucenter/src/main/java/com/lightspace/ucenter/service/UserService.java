package com.lightspace.ucenter.service;

import com.lightspace.framework.domain.XcCompanyUser;
import com.lightspace.framework.domain.ucenter.XcMenu;
import com.lightspace.framework.domain.ucenter.XcUser;
import com.lightspace.framework.domain.ucenter.ext.XcUserExt;
import com.lightspace.ucenter.dao.XcCompanyUserRepository;
import com.lightspace.ucenter.dao.XcMenuMapper;
import com.lightspace.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  XcUserRepository xcUserRepository;

  @Autowired
  XcCompanyUserRepository xcCompanyUserRepository;
  @Autowired
  XcMenuMapper xcMenuMapper;

  public XcUser findXcUserByUsername(String username){
    return xcUserRepository.findByUsername(username);
  }

  public XcUserExt getUserExt(String username){
    XcUser xcUser = this.findXcUserByUsername(username);
    if(xcUser == null){
      return null;
    }
    String userId = xcUser.getId();

    List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userId);
    XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findByUserId(userId);
    String companyId = null;
    if(xcCompanyUser!=null){
      companyId = xcCompanyUser.getCompanyId();
    }
    XcUserExt xcUserExt = new XcUserExt();
    BeanUtils.copyProperties(xcUser,xcUserExt);
    xcUserExt.setCompanyId(companyId);
    xcUserExt.setPermissions(xcMenus);
    return xcUserExt;

  }

}
