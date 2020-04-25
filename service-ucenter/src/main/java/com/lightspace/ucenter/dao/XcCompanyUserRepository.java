package com.lightspace.ucenter.dao;

import com.lightspace.framework.domain.XcCompanyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcCompanyUserRepository extends JpaRepository<XcCompanyUser,String> {
  XcCompanyUser findByUserId(String userId);
}
