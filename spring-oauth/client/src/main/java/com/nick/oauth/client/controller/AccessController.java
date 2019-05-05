package com.nick.oauth.client.controller;

import jdk.nashorn.internal.parser.Token;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author haloo
 * @Date 2019/5/3 15:50
 * @Version 1.0
 */
@Controller
public class AccessController {


    private static final String CLIENT_ID = "client";
    private static final Map<String, String> ACCESS_MAP = new ConcurrentHashMap<>();

    @GetMapping("/login")
    public String login() {
        return "redirect:" + getAuthorizationEndpoint();
    }

    @GetMapping("/callback")
    @ResponseBody
    public Object callback(String code, String state){
        AuthToken token = getToken(code);
        String access_token = token.getAccess_token();
        System.out.println(access_token);
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + access_token);
        RequestEntity<Object> request = new RequestEntity<>(
                headers,
                HttpMethod.GET,
                URI.create("http://localhost:1003/api/username")
        );
        ResponseEntity<String> exchange = restTemplate.exchange(request, String.class);
        if(exchange.getStatusCode().is2xxSuccessful()){
            return exchange.getBody();
        }
        return "fail";
    }

    private String getAuthorizationEndpoint() {
        String authorizationEndpoint = "http://localhost:1002/oauth/authorize?";
        Map<String, String> map = new HashMap<>();
        map.put("response_type", "code");
        map.put("scope", "read");
        map.put("client_id", CLIENT_ID);
        map.put("redirect_uri", "http://localhost:1004/callback");
        String s = parseUrl(map);
        return authorizationEndpoint + s;
    }


    private String getEncodeUrl(String targetUrl) {
        try {
            return URLEncoder.encode(targetUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public AuthToken getToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        String auth = encodeCredentials("client", "inner");

        RequestEntity<MultiValueMap<String, String>> request =
                new RequestEntity<>(
                        getBody(authorizationCode),
                        getHeader(auth),
                        HttpMethod.POST,
                        URI.create("http://localhost:1002/oauth/token")
                );
        ResponseEntity<AuthToken> responseEntity = restTemplate.exchange(request, AuthToken.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            AuthToken authToken = responseEntity.getBody();
            return authToken;
        }
        return null;
    }

    private String parseUrl(Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        params.forEach((key, value) -> {
            sb.append(key).append("=").append(value).append("&");
        });
        return sb.substring(0, sb.length()-1);
    }

    private MultiValueMap<String, String> getBody(String authorizationCode) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("scope", "read");
        formData.add("code", authorizationCode);
        formData.add("redirect_uri", "http://localhost:1004/callback");
        return formData;

    }

    private HttpHeaders getHeader(String clientAuthentication) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        headers.add("Authorization", "Basic " + clientAuthentication);
        return headers;
    }

    private String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        String encode = new String(Base64.getEncoder().encode(credentials.getBytes()));
        return encode;
    }


}
