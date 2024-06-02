package com.example.authbackend.repository;

import com.example.authbackend.models.Usermodels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Usermodels, Long> {
    @Query ("SELECT s FROM Usermodels s WHERE s.email=?1")
    Optional<Usermodels> findByEmail(String email);


    @Query ("SELECT s.password FROM Usermodels s WHERE s.email=?1")
    String findByEmailandPass(String email,String pass);

    @Query ("SELECT s.username FROM Usermodels s WHERE s.email=?1")
    String findByNameByEmail(String email);

    @Query ("SELECT s.id FROM Usermodels s WHERE s.email=?1")
    UUID findByIdByEmail(String email);


}
