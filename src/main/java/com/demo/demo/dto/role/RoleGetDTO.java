package com.demo.demo.dto.role;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleGetDTO extends GenericDTO {

    private String name;
    private String description;

}