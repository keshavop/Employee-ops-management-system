package com.example.authbackend.repository;

import com.example.authbackend.models.EmployeeModel;
import com.example.authbackend.models.Usermodels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    @Query ("SELECT s FROM EmployeeModel s WHERE s.emplId=?1")
    List<EmployeeModel> findByemplId(String emplId);


    @Modifying
    @Transactional
    @Query("UPDATE EmployeeModel e SET e.firstName = :#{#employee.firstName}, e.lastName = :#{#employee.lastName}, e.email = :#{#employee.email}, e.emplId = :#{#employee.emplId} WHERE e.id = :#{#employee.id}")
    void updateEmployee(@Param("employee") EmployeeModel employee);

    @Modifying
    @Transactional
    @Query("DELETE FROM EmployeeModel WHERE id=?1")
    void deleteEmployeeByID(UUID id);

}
