package com.chatapp.usermanagement.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDTO {
    UUID userId;
    String firstName;
    String lastName;
    String pictureUrl;
    String role;
    String username;

}
