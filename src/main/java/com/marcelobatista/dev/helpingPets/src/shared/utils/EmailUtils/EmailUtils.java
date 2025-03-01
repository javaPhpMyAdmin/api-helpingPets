package com.marcelobatista.dev.helpingPets.src.shared.utils.EmailUtils;

import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
  public String getEmailMessage(String name, String host, String token) {
    return "Hello " + name + ",\n\n"
        + "Welcome to Helping Pets! Please verify your account by clicking the link below:\n\n"
        + getVerificationAccountUrl(host, token) + "\n\n" + "The Helping Pets Team Support!";
  }

  public String getResetPasswordMessage(String name, String host, String token) {
    return "Hello " + name + ",\n\n"
        + "You have requested to reset your password. Please click the link below to reset your password:\n\n"
        + getResetPasswordUrl(host, token) + "\n\n" + "The Helping Pets Team Support!";
  }

  public String getVerificationAccountUrl(String host, String token) {
    return host + "/user/verify/account?token=" + token;
  }

  public String getResetPasswordUrl(String host, String token) {
    return host + "/user/verify/password?token=" + token;
  }

}
