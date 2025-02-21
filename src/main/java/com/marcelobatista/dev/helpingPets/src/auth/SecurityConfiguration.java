package com.marcelobatista.dev.helpingPets.src.auth;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import com.marcelobatista.dev.helpingPets.src.auth.service.UserDetailsServiceImpl;

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
            authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/auth/login", "/user/create-user")
                .permitAll()
                .requestMatchers(HttpMethod.PUT, "/user/update-user/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/verify/account").permitAll()
                .requestMatchers(HttpMethod.GET, "/user/verify/password").permitAll()
                .anyRequest().authenticated())
        .sessionManagement(
            sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
    httpSecurity.oauth2Login(customizer -> {
      customizer.successHandler(oauth2LoginSuccessHandler);
    });
    httpSecurity.addFilterBefore(new UsernamePasswordAuthenticationFilter(),
        LogoutFilter.class);

    httpSecurity
        .exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

    return httpSecurity.build();
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
}
