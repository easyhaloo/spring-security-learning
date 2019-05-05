package com.nick.oauth.client.controller;

/**
 * @Author haloo
 * @Date 2019/5/3 17:58
 * @Version 1.0
 */
public class AuthToken {

    private String access_token;
    private String token_type;
    private Long expires_in;
    private String scope;

    public AuthToken() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
