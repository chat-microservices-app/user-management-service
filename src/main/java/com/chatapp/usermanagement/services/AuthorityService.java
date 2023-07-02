package com.chatapp.usermanagement.services;

import com.chatapp.usermanagement.domain.security.Authority;

import java.util.Set;

public interface AuthorityService {

    Set<Authority> saveAllAuthorities(String permissions);
}
