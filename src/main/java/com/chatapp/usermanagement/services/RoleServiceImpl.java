package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.domain.security.Authority;
import com.chatapp.usermanagement.domain.security.Role;
import com.chatapp.usermanagement.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;


@RequiredArgsConstructor
@Log4j2
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Set<Role> saveAllRoles(String roles, Set<Authority> authorities) {
        Set<Role> roleSet = new HashSet<>();
        List<String> roleList = Arrays.asList(roles.split(" "));
        roleList.forEach(role -> {
            if(roleRepository.existsByRoleName(role)){

                Optional<Role> roleOptional = roleRepository.findByRoleName(role);
                roleOptional.ifPresent(role1 -> role1.setAuthorities(new HashSet<>(authorities)));
                roleSet.add(roleOptional.orElseThrow());
                return;
            }
            Role role1 = roleRepository.saveAndFlush(Role.builder().roleName(role).build());
            role1.setAuthorities(new HashSet<>(authorities));
            roleSet.add(roleRepository.saveAndFlush(role1));
        });
        return roleSet;
    }
}
