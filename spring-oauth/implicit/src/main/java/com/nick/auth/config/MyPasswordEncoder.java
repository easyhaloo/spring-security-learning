package com.nick.auth.config;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Create by haloo on 2019-05-06
 */
public class MyPasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(CharSequence charSequence) {
    return String.valueOf(charSequence);
  }

  @Override
  public boolean matches(CharSequence charSequence, String s) {
    return charSequence.equals(s);
  }
}
