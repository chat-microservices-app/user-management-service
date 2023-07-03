package com.chatapp.usermanagement.domain.security;


import com.chatapp.usermanagement.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false, name = "role_id")
    private UUID roleId;

    @Column(name = "role_name", unique = true, length = 60, columnDefinition = "varchar")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<User> users;

    //hibernate creates just a single sql statement rather than a separate statement for each entity
    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "role_authority",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", referencedColumnName = "authority_id")})
    private Set<Authority> authorities;
}
