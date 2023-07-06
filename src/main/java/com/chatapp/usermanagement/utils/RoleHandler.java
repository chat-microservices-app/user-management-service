package com.chatapp.usermanagement.utils;

import com.chatapp.usermanagement.enums.Role;

import java.util.Arrays;

public class RoleHandler {

    public static String getRoles(String roles) {
        return Arrays.stream(roles.split(" "))
                .filter(role -> role.startsWith(Role.PREFIX.getLabel()))
                .map(role -> role.replace(Role.PREFIX.getLabel(), ""))
                .reduce("", (a, b) -> a + " " + b)
                .trim();
    }

    public static String getPermissions(String roles) {
        return Arrays.stream(roles.split(" "))
                .filter(permission -> !permission.startsWith(Role.PREFIX.getLabel()))
                .reduce("", (a, b) -> a + " " + b)
                .trim();
    }
}
