package com.demo.demo.entity;


import com.demo.demo.entity.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;


@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Roles")
public class Role extends Auditable implements GrantedAuthority {

    private String name;

    @Column(columnDefinition = "text", length = 500)
    private String description;

    @Override
    public String getAuthority() {
        return name;
    }
}