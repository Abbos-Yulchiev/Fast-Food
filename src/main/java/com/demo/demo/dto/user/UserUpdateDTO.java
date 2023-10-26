package com.demo.demo.dto.user;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserUpdateDTO extends GenericDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private Set<Long> roles;
    private List<Long> addresses;
    private boolean isDeleted = false;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;
}