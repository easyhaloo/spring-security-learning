package com.nick.oauth.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * auth server
 *
 * @Author haloo
 * @Date 2019/5/2 23:54
 * @Version 1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthConfig extends AuthorizationServerConfigurerAdapter {


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess("isAuthenticated() || isAnonymous() ||hasAuthority('ROLE_TRUSTED_CLIENT')")
                .tokenKeyAccess("isAnonymous() ||hasAuthority('ROLE_TRUSTED_CLIENT')").passwordEncoder(
                new MyPasswordEncoder()
        );
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // client name. such as : appId
                .withClient("client")
                // client secret
                .secret("inner")
                //  redirect url,callback url.
                .redirectUris("http://localhost:1004/callback")
                // authorization type :
                //  total have four.
                // authorization_code, password ,
                .authorizedGrantTypes("authorization_code")
                // authorization scope
                .scopes("read", "write")
                .and()
                .withClient("haloo")
                .scopes("123456");
    }
}
