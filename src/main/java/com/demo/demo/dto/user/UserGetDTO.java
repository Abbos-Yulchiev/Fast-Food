package com.demo.demo.dto.user;


import com.demo.demo.dto.GenericDTO;
import com.demo.demo.dto.RoleDTO;
import com.demo.demo.dto.address.AddressGetDTO;
import lombok.*;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserGetDTO extends GenericDTO {

    private String username;
    private String firstName;
    private String lastName;
    private Set<RoleDTO> roles;
    private Set<AddressGetDTO> addresses;
}
