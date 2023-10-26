package com.demo.demo.dto;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoleDTO extends GenericDTO {
    private String name;
    private String description;
}
