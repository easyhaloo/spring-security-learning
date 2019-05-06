package com.nick.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * Create by haloo on 2019-05-06
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration
    extends AuthorizationServerConfigurerAdapter {


  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.checkTokenAccess("isAuthenticated() || isAnonymous()")
        .tokenKeyAccess("isAuthenticated() || isAnonymous()")
        .passwordEncoder(new MyPasswordEncoder());
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.inMemory()
        .withClient("client")
        .secret("inner")
        .redirectUris("http://localhost:1001/callback")
        .authorizedGrantTypes("implicit")
        .accessTokenValiditySeconds(120)
        .scopes("read", "write")
        .and()
        .withClient("haloo")
        .scopes("123456");
  }
}
