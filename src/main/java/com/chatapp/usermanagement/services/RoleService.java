package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.domain.security.Authority;
import com.chatapp.usermanagement.domain.security.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> saveAllRoles(String roles, Set<Authority> authorities);

}
