package com.example.videocliprating.repositories;

import com.example.videocliprating.models.User;
import com.example.videocliprating.models.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query("SELECT new com.example.videocliprating.models.dto.UserDTO(u.username, u.role) " +
            "FROM User u " +
            "WHERE u.username = :username")
    Optional<UserDTO> findUserDTOByUsername(@Param("username") String username);


}
