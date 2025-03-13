package com.marcelobatista.dev.helpingPets.src.security.application.service.impl;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.Base64.Decoder;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.crypto.SecretKey;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Service;

import com.marcelobatista.dev.helpingPets.src.config.security.JwtConfig;
import com.marcelobatista.dev.helpingPets.src.modules.users.application.service.UserService;
import com.marcelobatista.dev.helpingPets.src.modules.users.domain.User;
import com.marcelobatista.dev.helpingPets.src.security.application.service.JwtService;
import com.marcelobatista.dev.helpingPets.src.security.domain.Token;
import com.marcelobatista.dev.helpingPets.src.security.domain.TokenData;
import com.marcelobatista.dev.helpingPets.src.shared.enums.TokenType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl extends JwtConfig implements JwtService {

  private static final String HELPING_PETS = "HELPING_PETS";

  private static final String JWT = "JWT";

  private static final String TYP = "typ";

  private final UserService userService;

  private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(getSecretKey()));

  private final Function<String, Claims> claimsFunction = token -> Jwts.parser().verifyWith(key.get()).build()
      .parseSignedClaims(token).getPayload();

  private final Function<String, String> subject = token -> getClaimsValue(token, Claims::getSubject);

  private <T> T getClaimsValue(String token, Function<Claims, T> claims) {
    return claimsFunction.andThen(claims).apply(token);
  }

  private final Supplier<JwtBuilder> builder = () -> Jwts.builder()
      .header().add(Map.of(TYP, JWT))
      .and()
      .audience().add(HELPING_PETS)
      .and()
      .id(UUID.randomUUID().toString())
      .issuedAt(Date.from(Instant.now()))
      .notBefore(new Date())
      .signWith(key.get(), SIG.HS512);

  private final BiFunction<User, TokenType, String> buildToken = (user, type) -> Objects.equals(type, TokenType.ACCESS)
      ? builder.get()
          .subject(user.getEmail())
          .claim("AUTHORITIES", user.getAuthorities())
          .claim("ROLE", user.getRole())
          .expiration(Date.from(Instant.now().plusSeconds(getExpirationToken())))
          .compact()
      : builder.get()
          .subject(user.getEmail())
          .expiration(Date.from(Instant.now().plusSeconds(getExpirationToken())))
          .compact();

  @Override
  public String createToken(User user, Function<Token, String> tokenFunction) {
    throw new UnsupportedOperationException("Unimplemented method 'createToken'");
  }

  @Override
  public Optional<String> extractToken(HttpServletRequest request, String tokenType) {
    throw new UnsupportedOperationException("Unimplemented method 'extractToken'");
  }

  @Override
  public <T> T getTokenData(String token, Function<TokenData, T> tokenFunction) {
    throw new UnsupportedOperationException("Unimplemented method 'getTokenData'");
  }

}
