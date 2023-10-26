package com.demo.demo.dto.regions;

import com.demo.demo.dto.GenericDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegionUpdateDTO extends GenericDTO {
    private String name;
}
