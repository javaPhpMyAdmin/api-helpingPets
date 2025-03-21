package com.marcelobatista.dev.helpingPets.src.modules.users.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.marcelobatista.dev.helpingPets.src.modules.users.dto.CreateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.modules.users.dto.UpdateUserRequestDto;
import com.marcelobatista.dev.helpingPets.src.shared.utils.ApplicationContextProvider;
import com.marcelobatista.dev.helpingPets.src.shared.utils.Client;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Client
@Table(name = "users")
public class User implements UserDetails {
  private static final String USER_DEFAULT_PROFILE_URL = "https://static.vecteezy.com/system/resources/thumbnails/024/983/914/small_2x/simple-user-default-icon-free-png.png";
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;

  private String password;
  private String firstName;
  private String lastName;
  @Setter
  private boolean verified = false;
  @Setter
  private String profileImageUrl;
  @Enumerated(EnumType.STRING)
  @Setter
  @Getter
  private Role role;

  @Setter
  @OneToOne(mappedBy = "user")
  private VerificationCode verificationCode;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<UserConnectedAccount> connectedAccounts = new ArrayList<>();

  public User(CreateUserRequestDto data) {
    PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
    this.email = data.getEmail();
    this.password = passwordEncoder.encode(data.getPassword());
    this.firstName = data.getFirstName();
    this.lastName = data.getLastName();
    this.role = Role.USER;
    this.profileImageUrl = USER_DEFAULT_PROFILE_URL;
  }

  public User(OAuth2User oAuth2User) {
    // User user = new User();
    this.email = oAuth2User.getAttribute("email");
    String name = oAuth2User.getAttribute("name");
    if (name != null) {
      List<String> names = List.of(name.split(" "));
      if (names.size() > 1) {
        this.firstName = names.get(0);
        this.lastName = names.get(1);
      } else {
        this.firstName = names.get(0);
      }
    }
    this.verified = true;
    this.role = Role.USER;
    this.profileImageUrl = oAuth2User.getAttribute("picture");
  }

  public void addConnectedAccount(UserConnectedAccount connectedAccount) {
    connectedAccounts.add(connectedAccount);
  }

  public void updateFromDto(UpdateUserRequestDto userRequestDto) {
    Optional.ofNullable(userRequestDto.getFirstName()).ifPresent(firstName -> this.firstName = firstName);
    Optional.ofNullable(userRequestDto.getLastName()).ifPresent(lastName -> this.lastName = lastName);
    Optional.ofNullable(userRequestDto.getProfileImageUrl()).ifPresent(imageUrl -> this.profileImageUrl = imageUrl);
  }

  public void updatePassword(String newPassword) {
    PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
    this.password = passwordEncoder.encode(newPassword);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // If you want to not allow the user to login before verifying their email, you
  // can change this to
  // return verified;
  @Override
  public boolean isEnabled() {
    return true;
  }

}
