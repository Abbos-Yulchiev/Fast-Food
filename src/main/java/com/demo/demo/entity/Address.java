package com.demo.demo.entity;


import com.demo.demo.entity.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "Addresses")
@Entity
public class Address extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 2405172041950251807L;

    private String streetName;

    private String blockNumber;

    private String homeNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;
}


