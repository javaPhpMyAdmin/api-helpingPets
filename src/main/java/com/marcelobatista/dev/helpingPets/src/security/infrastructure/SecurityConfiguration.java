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
import org.springframework.security.web.context.NullSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.marcelobatista.dev.helpingPets.src.config.security.SecurityEndpoints;
import com.marcelobatista.dev.helpingPets.src.security.application.service.UserDetailsServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

  private final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
  private final UserDetailsServiceImpl userDetailsService;
  private final CustomLogoutSuccessHandler customLogoutSuccessHandler;
  private final CustomLogoutHandler customLogoutHandler;
  private final JwtFilter jwtFilter;

  public SecurityConfiguration(CustomLogoutHandler customLogoutHandler, UserDetailsServiceImpl userDetailsService,
      Oauth2LoginSuccessHandler oauth2LoginSuccessHandler, JwtFilter jwtFilter,
      CustomLogoutSuccessHandler customLogoutSuccessHandler) {
    this.customLogoutHandler = customLogoutHandler;
    this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
    this.userDetailsService = userDetailsService;
    this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    this.jwtFilter = jwtFilter;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
      throws Exception {
    httpSecurity.csrf(csrf -> csrf.disable());
    httpSecurity
        .authorizeHttpRequests(
            authorize -> {
              authorize.requestMatchers(SecurityEndpoints.PUBLIC_ENDPOINTS.toArray(new String[0])).permitAll();
              authorize.requestMatchers(org.springframework.http.HttpMethod.OPTIONS,
                  "/**").permitAll();
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
    httpSecurity.securityContext(
        securityContext -> securityContext.securityContextRepository(new NullSecurityContextRepository()));
    httpSecurity.addFilterBefore(jwtFilter,
        UsernamePasswordAuthenticationFilter.class);

    // httpSecurity
    // .exceptionHandling(ex -> ex.authenticationEntryPoint(new
    // CustomAuthenticationEntryPoint()));
    httpSecurity.logout(logout -> logout
        .logoutUrl("/auth/logout")
        .addLogoutHandler(customLogoutHandler)
        .logoutSuccessHandler(customLogoutSuccessHandler));

    return httpSecurity.build();
  }

  @Bean
  public SecurityContextRepository securityContextRepository() {
    return new NullSecurityContextRepository();
  }

  private void applyMethodBasedAuthorization(
      AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth,
      Map<HttpMethod, List<String>> endpoints,
      String role) {
    endpoints.forEach((method, urls) -> auth.requestMatchers(method,
        urls.toArray(String[]::new)).hasRole(role));
  }

  public class CustomAuthenticationEntryPoint implements
      AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
      // Configurar respuesta de error cuando el usuario no está autenticado
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
      response.getWriter().write("{\"error\": \"You are not authorized, please login first.\"}");
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

  @Bean

  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowedOrigins(List.of("http://localhost:5173")); // Asegurar origen correcto
    config.setAllowCredentials(true);
    config.setAllowedMethods(
        List.of(HttpMethod.GET.name(), HttpMethod.PUT.name(), HttpMethod.POST.name(), HttpMethod.DELETE.name())); // Incluir
                                                                                                                  // OPTIONS
    config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin"));
    config.setExposedHeaders(List.of("Authorization"));

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
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

  // @Bean
  // public CorsFilter corsFilter() {
  // UrlBasedCorsConfigurationSource source = new
  // UrlBasedCorsConfigurationSource();
  // CorsConfiguration config = new CorsConfiguration();

  // // config.setAllowedOrigins(List.of("http://localhost:5173",
  // // "http://localhost:8081",
  // // "exp://192.168.79.165:8081",
  // // "exp://192.168.79.165",
  // // "http://192.168.79.165",
  // // "http://192.168.79.165:8081",
  // // "http://192.168.79.165:8081/_expo/loading?platform=ios",
  // // "http://192.168.79.165:8081/_expo/loading"));
  // config.setAllowedOriginPatterns(List.of(
  // "http://localhost:*", // Permitir cualquier puerto en localhost
  // "http://127.0.0.1:*", // Otra forma de localhost
  // "http://192.168.*.*", // Permitir cualquier IP en la red local
  // "exp://*", // Permitir Expo en cualquier IP
  // "http://*.expo.dev"));
  // config.setAllowCredentials(true);
  // config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
  // // Incluir OPTIONS
  // config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept",
  // "Origin"));
  // config.setExposedHeaders(List.of("Authorization"));
  // log.info("CORS Configuration: {}", config.getAllowedOrigins());
  // source.registerCorsConfiguration("/**", config);
  // return new CorsFilter(source);
  // }
}
