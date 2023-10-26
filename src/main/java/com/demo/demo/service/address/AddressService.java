package com.demo.demo.service.address;


import com.demo.demo.core.exception.DataAlreadyExistException;
import com.demo.demo.core.exception.DataNotFoundException;
import com.demo.demo.core.exception.UnknownDataBaseException;
import com.demo.demo.dto.address.AddressCreateDTO;
import com.demo.demo.dto.address.AddressGetDTO;
import com.demo.demo.dto.address.AddressUpdateDTO;
import com.demo.demo.entity.Address;
import com.demo.demo.entity.Region;
import com.demo.demo.repository.address.AddressRepository;
import com.demo.demo.repository.address.RegionRepository;
import com.demo.demo.response.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private final AddressRepository repository;
    private final RegionRepository regionRepository;
    private final ModelMapper mapper;

    public AddressService(AddressRepository repository, RegionRepository regionRepository, ModelMapper mapper) {
        this.regionRepository = regionRepository;
        this.repository = repository;
        this.mapper = mapper;
    }

    public Data<AddressGetDTO> create(AddressCreateDTO dto) {

        Optional<Address> homeNumber = repository.findAddressByHomeNumber(dto.getHomeNumber());
        if (homeNumber.isPresent()) throw new DataAlreadyExistException
                ("Address with home number: [%s] is already exist".formatted(dto.getHomeNumber()));
        Address address = new Address();
        mapper.map(dto, address);
        return new Data<>(convertToAddressGetDTO(address));
    }

    @Transactional
    public Data<AddressGetDTO> update(AddressUpdateDTO dto) {

        Optional<Address> homeNumber = repository.findAddressByHomeNumberAndIdNot(dto.getHomeNumber(), dto.getId());
        if (homeNumber.isPresent()) throw new DataAlreadyExistException
                ("Address with home number: [%s] is already exist".formatted(dto.getHomeNumber()));

        Address address = repository.findById(dto.getId()).orElseThrow(() ->
                new DataNotFoundException("Address with ID: [" + dto.getId() + "] was not found"));
        mapper.map(dto, address);
        if (dto.getRegionId() != null) {
            Region region = regionRepository.findById(Long.valueOf(dto.getRegionId())).orElseThrow(() ->
                    new DataNotFoundException("Region with ID: [" + dto.getId() + "] was not found"));
            address.setRegion(region);
        }
        return new Data<>(convertToAddressGetDTO(address));
    }

    public Data<String> delete(Long id) {
        Optional<Address> byId = repository.findById(id);
        byId.ifPresent(repository::delete);
        return new Data<>("Address was successfully deleted");
    }

    public Data<AddressGetDTO> get(Long id) {
        Address address = repository.findById(id).orElseThrow(() ->
                new UnknownDataBaseException("Address with ID: [%s] was not found".formatted(id)));
        return new Data<>(convertToAddressGetDTO(address));
    }

    public Data<List<AddressGetDTO>> getAll() {
        List<Address> addresses = repository.findAll();
        List<AddressGetDTO> addressList = new ArrayList<>();
        for (Address address : addresses)
            addressList.add(convertToAddressGetDTO(address));

        return new Data<>(addressList, addressList.size());
    }

    public Data<List<AddressGetDTO>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Address> addresses = repository.findAll(pageable);
        List<AddressGetDTO> addressList = new ArrayList<>();
        for (Address address : addresses)
            addressList.add(convertToAddressGetDTO(address));
        return new Data<>(addressList, addressList.size());
    }

    private AddressGetDTO convertToAddressGetDTO(Address address) {
        Address saved = repository.save(address);
        AddressGetDTO addressGetDTO = new AddressGetDTO();
        mapper.map(saved, addressGetDTO);
        return addressGetDTO;
    }
}
