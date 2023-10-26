package com.demo.demo.entity;

import com.demo.demo.entity.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Regions")
public class Region extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    private String name;

    @OneToMany(mappedBy = "region", fetch = FetchType.LAZY)
    private List<Address> addresses;
}