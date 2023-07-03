package com.chatapp.usermanagement.domain.security;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue(generator = "UUID", strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false, name = "authority_id")
    private UUID authorityId;


    @Column(unique = true, name = "permission", length = 120, columnDefinition = "varchar")
    private String permission;

    //referencing the property authorities where we have declared the dependencies for joining tables
    @ManyToMany(mappedBy = "authorities")
    @ToString.Exclude
    private Set<Role> roles;


}
