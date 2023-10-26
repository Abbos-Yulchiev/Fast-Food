package com.demo.demo.repository.user;

import com.demo.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findUserByUsername(String username);

    @Query(value = "SELECT * FROM Users as u\n" +
            "WHERE u.username = ?1 AND NOT u.id = ?2", nativeQuery = true)
    User findUserByUsernameAndIdNot(String name, Long id);
}
