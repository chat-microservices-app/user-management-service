package com.chatapp.usermanagement.repositories;

import com.chatapp.usermanagement.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {


    boolean existsByPermission(String permission);


    Optional<Authority> findByPermission(String permission);
}
