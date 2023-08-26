package com.chatapp.usermanagement.web.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
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
