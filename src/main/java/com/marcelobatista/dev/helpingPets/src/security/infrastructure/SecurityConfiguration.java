package com.marcelobatista.dev.helpingPets.src.security.infrastructure;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.marcelobatista.dev.helpingPets.src.config.security.SecurityEndpoints;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserDetailsServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfiguration {

  private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
  private final UserDetailsServiceImpl userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers(SecurityEndpoints.PUBLIC_ENDPOINTS.toArray(String[]::new)).permitAll();
              authorize.requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll();

              applyMethodBasedAuthorization(authorize,
                  SecurityEndpoints.USER_PROTECTED_ENDPOINTS, "USER");
              applyMethodBasedAuthorization(authorize,
                  SecurityEndpoints.ADMIN_PROTECTED_ENDPOINTS, "ADMIN");
              authorize.anyRequest().authenticated();
            });
    httpSecurity.sessionManagement(
        sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
    httpSecurity.oauth2Login(customizer -> {
      customizer.successHandler(oauth2LoginSuccessHandler);
    });
    httpSecurity.addFilterBefore(new UsernamePasswordAuthenticationFilter(),
        LogoutFilter.class);
    // httpSecurity
    // .exceptionHandling(ex -> ex.authenticationEntryPoint(new
    // CustomAuthenticationEntryPoint()));

    return httpSecurity.build();
  }

  private void applyMethodBasedAuthorization(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth,
      Map<HttpMethod, List<String>> endpoints,
      String role) {
    endpoints.forEach((method, urls) -> auth.requestMatchers(method, urls.toArray(String[]::new)).hasRole(role));
  }

  public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
      // Configurar respuesta de error cuando el usuario no está autenticado
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
      response.getWriter().write("{\"error\": \"No autorizado. Debes iniciar sesión.\"}");
    }
  }

  @Bean
  public AuthenticationManager authenticationManager() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    return new ProviderManager(daoAuthenticationProvider);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // @Bean
  // public CorsConfigurationSource configurationSource() {
  // CorsConfiguration configurarion = new CorsConfiguration();
  // configurarion.setAllowedOrigins(
  // List.of("http://localhost:5173", "http://localhost:5173",
  // "http://127.0.0.0:5173", "http://127.0.0.0:5173"));
  // configurarion.setExposedHeaders(List.of("Authorization"));
  // configurarion.setAllowCredentials(true);
  // configurarion.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE",
  // "OPTIONS"));
  // configurarion.setAllowedHeaders(List.of("Authorization", "Content-type",
  // "Acept", "Origin"));
  // UrlBasedCorsConfigurationSource source = new
  // UrlBasedCorsConfigurationSource();
  // source.registerCorsConfiguration("/**", configurarion);
  // return source;
  // }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:5173")); // Asegurar origen correcto
    config.setAllowCredentials(true);
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Incluir OPTIONS
    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
    config.setExposedHeaders(List.of("Authorization"));

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }
}
