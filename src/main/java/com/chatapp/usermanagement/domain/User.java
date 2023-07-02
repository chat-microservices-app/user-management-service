package com.chatapp.usermanagement.domain;


import com.chatapp.usermanagement.domain.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "user_account")
public class User implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false, name = "user_id")
    private UUID userId;

    @Column(length = 120, columnDefinition = "varchar", unique = true, nullable = false, name = "usernameOrEmail")
    private String username;


    @Column(columnDefinition = "varchar(max)", nullable = false, name = "password")
    private String password;

    @Column(name = "first_name", length = 120, columnDefinition = "varchar")
    private String firstName;

    @Column(name = "last_name", length = 120, columnDefinition = "varchar")
    private String lastName;

    @Email
    @Column(unique = true, name = "email", length = 250, columnDefinition = "varchar")
    private String email;


    @Column(name = "date_of_birth", columnDefinition = "date")
    private Date dateOfBirth;


    // singular allows adding one role at a time
    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.JOIN)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "user_id"))},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id", foreignKey = @ForeignKey(name = "role_id"))})
    private Set<Role> roles;


    //Transient annotation is used to exclude this field from the object
    //property is calculated and not stored in the database or persisted
    @Transient
    public Set<GrantedAuthority> getAuthorities() {
        if (this.roles == null || this.roles.isEmpty()) {
            return Set.of();
        }
        Set<GrantedAuthority> grantedAuthorities = this.roles.stream()
                .map(Role::getAuthorities)
                .flatMap(Set::stream)
                .map(authority -> (GrantedAuthority) new SimpleGrantedAuthority(authority.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.addAll(
                this.roles.stream().map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getRoleName()))
                        .collect(Collectors.toSet())
        );
        return grantedAuthorities;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }


    @Column(name = "account_non_expired")
    @Builder.Default
    private Boolean accountNonExpired = true;

    @Column(name = "account_non_locked")
    @Builder.Default
    private Boolean accountNonLocked = true;


    @Column(name = "credentials_non_expired")
    @Builder.Default
    private Boolean credentialsNonExpired = true;


    @Builder.Default
    private Boolean enabled = true;

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return getUserId() != null && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}
