package com.demo.demo.service.address;

import com.demo.demo.core.exception.DataAlreadyExistException;
import com.demo.demo.core.exception.DataNotFoundException;
import com.demo.demo.dto.regions.RegionCreateDTO;
import com.demo.demo.dto.regions.RegionGetDTO;
import com.demo.demo.dto.regions.RegionUpdateDTO;
import com.demo.demo.entity.Region;
import com.demo.demo.repository.address.RegionRepository;
import com.demo.demo.response.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    private final ModelMapper mapper;
    private final RegionRepository repository;


    public RegionService(RegionRepository repository, ModelMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public Data<RegionGetDTO> create(RegionCreateDTO dto) {
        Optional<Region> byName = repository.findRegionByName(dto.getName().toUpperCase());
        if (byName.isPresent()) throw new DataAlreadyExistException
                ("Region with name: [%s] already exist".formatted(dto.getName()));
        Region region = new Region();
        dto.setName(dto.getName().toUpperCase());
        mapper.map(dto, region);
        region = repository.save(region);
        RegionGetDTO regionGetDTO = new RegionGetDTO();
        mapper.map(region, regionGetDTO);
        return new Data<>(regionGetDTO);
    }

    public Data<RegionGetDTO> update(RegionUpdateDTO dto) {
        Region region = repository.findRegionByNameAndIdNot(dto.getName(), dto.getId()).orElseThrow(() ->
                new DataNotFoundException("Region with ID: [%s] was not found".formatted(dto.getId())));
        RegionGetDTO regionGetDTO = new RegionGetDTO();
        mapper.map(region, regionGetDTO);
        repository.save(region);
        return new Data<>(regionGetDTO);
    }

    public Data<String> delete(Long id) {
        Optional<Region> byId = repository.findById(id);
        byId.ifPresent(repository::delete);
        return new Data<>("Region was successfully deleted");
    }

    public Data<RegionGetDTO> get(Long id) {
        Region region = repository.findById(id).orElseThrow(() ->
                new DataNotFoundException("Region with ID: [%s] was not found".formatted(id)));
        RegionGetDTO regionGetDTO = new RegionGetDTO();
        mapper.map(region, regionGetDTO);
        return new Data<>(regionGetDTO);
    }

    public Data<List<RegionGetDTO>> getAll() {
        List<Region> regions = repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        List<RegionGetDTO> regionGetDTOs = new ArrayList<>();
        for (Region region : regions) {
            RegionGetDTO regionGetDTO = new RegionGetDTO();
            mapper.map(region, regionGetDTO);
            regionGetDTOs.add(regionGetDTO);
        }
        return new Data<>(regionGetDTOs, regionGetDTOs.size());
    }

    public Data<List<RegionGetDTO>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<Region> regions = repository.findAll(pageable);
        List<RegionGetDTO> regionGetDTOs = new ArrayList<>();
        for (Region region : regions) {
            RegionGetDTO regionGetDTO = new RegionGetDTO();
            mapper.map(region, regionGetDTO);
            regionGetDTOs.add(regionGetDTO);
        }
        return new Data<>(regionGetDTOs, regionGetDTOs.size());
    }

    private RegionGetDTO convertToRegionGetDTO(Region region) {
        Region saved = repository.save(region);
        RegionGetDTO addressGetDTO = new RegionGetDTO();
        mapper.map(saved, addressGetDTO);
        return addressGetDTO;
    }
}
