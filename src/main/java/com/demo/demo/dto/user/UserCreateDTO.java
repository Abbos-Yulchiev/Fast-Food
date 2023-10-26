package com.demo.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Long> roles;
    private Set<Long> addresses;
}