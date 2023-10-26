package com.demo.demo.service.user;

import com.demo.demo.core.exception.DataAlreadyExistException;
import com.demo.demo.core.exception.DataNotFoundException;
import com.demo.demo.core.exception.UnknownDataBaseException;
import com.demo.demo.dto.user.UserCreateDTO;
import com.demo.demo.dto.user.UserGetDTO;
import com.demo.demo.dto.user.UserUpdateDTO;
import com.demo.demo.entity.Address;
import com.demo.demo.entity.Role;
import com.demo.demo.entity.User;
import com.demo.demo.repository.address.AddressRepository;
import com.demo.demo.repository.role.RoleRepository;
import com.demo.demo.repository.user.UserRepository;
import com.demo.demo.response.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private static final String WAS_NOT_FOUND = "] was not found";

    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;


    public UserService(UserRepository repository, AddressRepository addressRepository,
                       RoleRepository roleRepository, ModelMapper mapper,
                       PasswordEncoder passwordEncoder) {
        this.addressRepository = addressRepository;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
    }

    public Data<UserGetDTO> create(UserCreateDTO dto) {
        Optional<User> existingUser = repository.findUserByUsername(dto.getUsername());
        if (existingUser.isPresent()) throw new DataAlreadyExistException
                ("User with username: [%s] already exist".formatted(dto.getUsername()));
        User user = new User();
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        mapper.map(dto, user);
        user.setRoles(getRoles(dto.getRoles()));
        user.setAddresses(getAddresses(dto.getRoles()));
        User saved = repository.save(user);
        UserGetDTO userGetDTO = new UserGetDTO();
        mapper.map(saved, userGetDTO);
        return new Data<>(userGetDTO);
    }

    public Data<UserGetDTO> update(UserUpdateDTO dto) {
        User existingUser = repository.findById(dto.getId()).orElseThrow(()
                -> new DataNotFoundException("User with ID: [" + dto.getId() + WAS_NOT_FOUND));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        mapper.map(dto, existingUser);
        User saved = repository.save(existingUser);
        UserGetDTO userGetDTO = new UserGetDTO();
        mapper.map(saved, userGetDTO);
        return new Data<>(userGetDTO);
    }

    public Data<String> delete(Long id) {
        Optional<User> user = repository.findById(id);
        user.ifPresent(repository::delete);
        return new Data<>("User was successfully deleted");
    }

    public Data<UserGetDTO> get(Long id) {
        User user = repository.findById(id).orElseThrow(() ->
                new UnknownDataBaseException("Address with ID: [%s] was not found".formatted(id)));
        UserGetDTO userGetDTO = new UserGetDTO();
        mapper.map(user, userGetDTO);
        return new Data<>(userGetDTO);

    }

    public Data<List<UserGetDTO>> getAll() {
        List<User> userList = repository.findAll();
        List<UserGetDTO> userGetDTOList = new ArrayList<>();
        for (User user : userList) {
            UserGetDTO userGetDTO = new UserGetDTO();
            mapper.map(user, userGetDTO);
            userGetDTOList.add(userGetDTO);
        }
        return new Data<>(userGetDTOList, userGetDTOList.size());
    }

    public Data<List<UserGetDTO>> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = repository.findAll(pageable);

        List<UserGetDTO> userGetDTOList = new ArrayList<>();
        for (User user : users) {
            userGetDTOList.add(convertToUserGetDTO(user));
        }
        return new Data<>(userGetDTOList, userGetDTOList.size());
    }

    public Data<UserGetDTO> activateUser(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new DataNotFoundException("Invalid User Id"));
        user.setEnabled(false);
        User saved = repository.save(user);
        return new Data<>(convertToUserGetDTO(saved));
    }

    private Set<Role> getRoles(Set<Long> roleIds) {
        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleRepository.findById(roleId).orElseThrow(()
                    -> new DataNotFoundException("Role with ID: [" + roleId + WAS_NOT_FOUND));
            roles.add(role);
        }
        return roles;
    }

    private Set<Address> getAddresses(Set<Long> addressIds) {
        Set<Address> addresses = new HashSet<>();
        for (Long addressId : addressIds) {
            Address role = addressRepository.findById(addressId).orElseThrow(()
                    -> new DataNotFoundException("Address with ID: [" + addressId + WAS_NOT_FOUND));
            addresses.add(role);
        }
        return addresses;
    }

    private UserGetDTO convertToUserGetDTO(User user) {
        User saved = repository.save(user);
        UserGetDTO userGetDTO = new UserGetDTO();
        mapper.map(saved, userGetDTO);
        return userGetDTO;
    }
}
