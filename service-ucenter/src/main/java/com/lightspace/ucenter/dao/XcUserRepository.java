package com.lightspace.ucenter.dao;

import com.lightspace.framework.domain.ucenter.XcUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface XcUserRepository extends JpaRepository<XcUser,String> {
  XcUser findByUsername(String username);
}
