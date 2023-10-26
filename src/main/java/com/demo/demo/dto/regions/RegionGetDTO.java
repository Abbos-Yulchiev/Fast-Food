package com.demo.demo.dto.regions;

import com.demo.demo.dto.GenericDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegionGetDTO extends GenericDTO {
    private String name;
}
