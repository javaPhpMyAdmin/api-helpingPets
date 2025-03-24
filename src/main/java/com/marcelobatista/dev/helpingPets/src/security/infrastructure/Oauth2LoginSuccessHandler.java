package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.UserConnectedAccount;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.ConnectedAccountRepository;
import com.marcelobatista.dev.helpingPets.src.modules.users.infrastructure.UserRepository;
import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserTokenService;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;
import com.marcelobatista.dev.helpingPets.src.security.domain.TokenData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

  private final ConnectedAccountRepository connectedAccountRepository;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final UserTokenService userTokenService;

  // public Oauth2LoginSuccessHandler(ApplicationProperties applicationProperties,
  // UserRepository userRepository,
  // ConnectedAccountRepository connectedAccountRepository) {
  // this.applicationProperties = applicationProperties;
  // this.userRepository = userRepository;
  // this.connectedAccountRepository = connectedAccountRepository;
  // }

  @Override
  @Transactional
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {

    OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
    String provider = authenticationToken.getAuthorizedClientRegistrationId();
    String providerId = authentication.getName();
    String email = authenticationToken.getPrincipal().getAttribute("email");

    // First check if we have the user based on a connected account (provider /
    // providerId)
    Optional<UserConnectedAccount> connectedAccount = connectedAccountRepository.findByProviderAndProviderId(provider,
        providerId);
    if (connectedAccount.isPresent()) {
      authenticateUser(connectedAccount.get().getUser(), response);
      return;
    }

    // At this point we don't have a connected account, so we either find a user by
    // email and add the connected account
    // or we create a new user
    User existingUser = userRepository.findByEmail(email).orElse(null);
    if (existingUser != null) {
      UserConnectedAccount newConnectedAccount = new UserConnectedAccount(provider,
          providerId, existingUser);
      // existingUser.addConnectedAccount(newConnectedAccount);
      // existingUser = userRepository.save(existingUser);
      connectedAccountRepository.save(newConnectedAccount);
      authenticateUser(existingUser, response);
    } else {
      User newUser = createUserFromOauth2User(authenticationToken);
      System.out.println("USER IN SUCCESS HANDLER" + newUser);
      authenticateUser(newUser, response);
    }
  }

  private void authenticateUser(User user, HttpServletResponse response) throws IOException {
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null,
        user.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(token);

    String accessToken = jwtService.createToken(user, Token::getAccess);
    Instant expiresAt = jwtService.getTokenData(accessToken, TokenData::getExpiration);

    userTokenService.saveToken(user, accessToken, expiresAt);

    String redirectUrl = "exp://jbqyxvc-chelobat16411-8081.exp.direct/--/authCallback"
        + "?accessToken=" + URLEncoder.encode(accessToken, StandardCharsets.UTF_8);
    response.sendRedirect(redirectUrl);
  }

  private User createUserFromOauth2User(OAuth2AuthenticationToken authentication) {
    User user = new User(authentication.getPrincipal());
    String provider = authentication.getAuthorizedClientRegistrationId();
    String providerId = authentication.getName();
    UserConnectedAccount connectedAccount = new UserConnectedAccount(provider,
        providerId, user);
    // user.addConnectedAccount(connectedAccount);
    user = userRepository.save(user);
    connectedAccountRepository.save(connectedAccount);
    return user;
  }
}
