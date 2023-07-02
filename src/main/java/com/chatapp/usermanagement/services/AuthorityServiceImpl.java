package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.domain.security.Authority;
import com.chatapp.usermanagement.repositories.AuthorityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Service
@Transactional
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;
    @Override
    public Set<Authority> saveAllAuthorities(String permissions) {
        Set<Authority> authoritySet = new HashSet<>();
        List<String> permissionList = Arrays.asList(permissions.split(" "));
        permissionList.forEach(permission -> {
            if(authorityRepository.existsByPermission(permission)){
                Optional<Authority> authority = authorityRepository.findByPermission(permission);
                authority.ifPresent(authoritySet::add);
                return;
            }
            Authority authority = authorityRepository.saveAndFlush(Authority.builder().permission(permission).build());
            authoritySet.add(authority);
        });
        return authoritySet;
    }
}
