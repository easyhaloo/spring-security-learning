package com.nick.oauth.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**  资源配服务器
 * @Author haloo
 * @Date 2019/5/2 23:58
 * @Version 1.0
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends ResourceServerConfigurerAdapter {



    @Override
    public void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .anyRequest()
               .authenticated()
               .and()
               .requestMatchers()
               .antMatchers("/api/**");
    }
}
