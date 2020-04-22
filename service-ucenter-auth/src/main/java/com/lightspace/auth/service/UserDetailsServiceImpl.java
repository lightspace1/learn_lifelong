package com.lightspace.auth.service;

import com.lightspace.auth.client.UserClient;
import com.lightspace.framework.domain.ucenter.XcMenu;
import com.lightspace.framework.domain.ucenter.ext.XcUserExt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  UserClient userClient;

  @Autowired
  ClientDetailsService clientDetailsService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if(authentication==null){
      ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
      if(clientDetails!=null){
        String clientSecret = clientDetails.getClientSecret();
        return new User(username,clientSecret,AuthorityUtils.commaSeparatedStringToAuthorityList(""));
      }
    }
    if (StringUtils.isEmpty(username)) {
      return null;
    }
    XcUserExt userext = userClient.getUserext(username);
    if(userext == null){
      return null;
    }

    userext.setPermissions(new ArrayList<XcMenu>());

    String password = userext.getPassword();
    List<XcMenu> permissions = userext.getPermissions();
    List<String> user_permission = new ArrayList<>();
    permissions.forEach(item-> user_permission.add(item.getCode()));
    String user_permission_string  = StringUtils.join(user_permission.toArray(), ",");
    UserJwt userDetails = new UserJwt(username,
      password,
      AuthorityUtils.commaSeparatedStringToAuthorityList(user_permission_string));
    userDetails.setId(userext.getId());
    userDetails.setUtype(userext.getUtype());
    userDetails.setCompanyId(userext.getCompanyId());
    userDetails.setName(userext.getName());
    userDetails.setUserpic(userext.getUserpic());
    return userDetails;
  }
}

