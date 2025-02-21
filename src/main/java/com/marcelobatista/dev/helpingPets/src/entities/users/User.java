package com.marcelobatista.dev.helpingPets.src.entities.users;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.marcelobatista.dev.helpingPets.src.entities.users.data.CreateUserRequest;
import com.marcelobatista.dev.helpingPets.src.entities.users.data.UpdateUserRequest;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Client
@Table(name = "users")
public class User implements UserDetails {
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

  public User(CreateUserRequest data) {
    PasswordEncoder passwordEncoder = ApplicationContextProvider.bean(PasswordEncoder.class);
    this.email = data.getEmail();
    this.password = passwordEncoder.encode(data.getPassword());
    this.firstName = data.getFirstName();
    this.lastName = data.getLastName();
    this.role = Role.USER;
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
  }

  public void addConnectedAccount(UserConnectedAccount connectedAccount) {
    connectedAccounts.add(connectedAccount);
  }

  public void update(UpdateUserRequest request) {
    // TODO: EXTEND THIS METHOD TO UPDATE OTHER FIELDS
    this.firstName = request.getFirstName();
    this.lastName = request.getLastName();
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
