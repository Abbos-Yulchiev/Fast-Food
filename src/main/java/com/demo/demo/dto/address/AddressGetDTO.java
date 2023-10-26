package com.demo.demo.dto.address;

import com.demo.demo.dto.GenericDTO;
import com.demo.demo.dto.regions.RegionGetDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddressGetDTO extends GenericDTO {

    private String streetName;
    private String blockNumber;
    private String homeNumber;
    private RegionGetDTO region;
}
