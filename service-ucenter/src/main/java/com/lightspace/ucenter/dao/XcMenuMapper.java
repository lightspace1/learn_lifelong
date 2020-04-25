package com.lightspace.ucenter.dao;

import com.lightspace.framework.domain.ucenter.XcMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface XcMenuMapper {
  public List<XcMenu> selectPermissionByUserId(String userid);
}
