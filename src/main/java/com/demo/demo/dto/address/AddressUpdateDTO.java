package com.demo.demo.dto.address;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressUpdateDTO extends GenericDTO {

    private String streetName;
    private String blockNumber;
    private String homeNumber;
    private String regionId;
}
