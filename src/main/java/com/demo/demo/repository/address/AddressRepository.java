package com.demo.demo.repository.address;

import com.demo.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
   
    Optional<Address> findAddressByHomeNumber(String name);

    Optional<Address> findAddressByHomeNumberAndIdNot(String name, Long id);
}
