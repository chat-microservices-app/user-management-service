package com.chatapp.usermanagement.web.dto;

import lombok.*;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Getter
@Builder
public class UserDTO {
    UUID userId;
    String firstName;
    String lastName;
    String pictureUrl;
    String username;
}
