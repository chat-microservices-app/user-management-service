package com.chatapp.usermanagement.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Set;



@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserDetailsTransfer {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("authorities")
    private Set<String> authorities;

    @JsonProperty("accountNonExpired")
    private boolean accountNonExpired;


    @JsonProperty("accountNonLocked")
    private boolean accountNonLocked;
    @JsonProperty("credentialsNonExpired")
    private boolean credentialsNonExpired;

    @JsonProperty("enabled")
    private boolean enabled;


}
