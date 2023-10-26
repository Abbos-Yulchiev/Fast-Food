package com.demo.demo.repository.address;

import com.demo.demo.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {

    Optional<Region> findRegionByName(String name);

    Optional<Region> findRegionByNameAndIdNot(String name, Long id);
}
