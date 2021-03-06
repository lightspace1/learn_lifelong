package com.lightspace.auth.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
  @Autowired
  private DataSource dataSource;
  @Autowired
  private JwtAccessTokenConverter jwtAccessTokenConverter;
  @Autowired
  UserDetailsService userDetailsService;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  TokenStore tokenStore;
  @Autowired
  private CustomUserAuthenticationConverter customUserAuthenticationConverter;

  @Bean("keyProp")
  public KeyProperties keyProperties(){
    return new KeyProperties();
  }

  @Resource(name = "keyProp")
  private KeyProperties keyProperties;


  @Bean
  public ClientDetailsService clientDetails() {
    return new JdbcClientDetailsService(dataSource);
  }
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.jdbc(this.dataSource).clients(this.clientDetails());

  }


  @Bean
  @Autowired
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
    return new JwtTokenStore(jwtAccessTokenConverter);
  }
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter(CustomUserAuthenticationConverter customUserAuthenticationConverter) {
    JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
    KeyPair keyPair = new KeyStoreKeyFactory
      (keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
      .getKeyPair(keyProperties.getKeyStore().getAlias(),keyProperties.getKeyStore().getPassword().toCharArray());
    converter.setKeyPair(keyPair);

    DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
    accessTokenConverter.setUserTokenConverter(customUserAuthenticationConverter);
    return converter;
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

    endpoints.accessTokenConverter(jwtAccessTokenConverter)
      .authenticationManager(authenticationManager)
      .tokenStore(tokenStore)
      .userDetailsService(userDetailsService);
  }


  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer.allowFormAuthenticationForClients()
      .passwordEncoder(new BCryptPasswordEncoder())
      .tokenKeyAccess("permitAll()")
      .checkTokenAccess("isAuthenticated()");
  }



}


