package com.chatapp.usermanagement.annotation;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('service.register')")
public @interface RegisterPerm {
}
