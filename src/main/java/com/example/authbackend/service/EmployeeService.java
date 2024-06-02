package com.example.authbackend.service;


import com.example.authbackend.models.EmployeeModel;
import com.example.authbackend.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class EmployeeService {

    public final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository=employeeRepository;
    }

    public List<EmployeeModel> getEmployeeById(String id)
    {
        try {
                return employeeRepository.findByemplId(id);
        }
        catch(Exception e)
        {
            log.error("Error while fetching employee by id",e);
            return new ArrayList<>();
        }
    }

    public void saveEmployee(EmployeeModel employeeModel)
    {
        employeeRepository.save(employeeModel);
    }

    public void deleteEmployee(UUID id)
    {
        employeeRepository.deleteEmployeeByID(id);
    }

    public void updateEmployee(EmployeeModel employeeModel)
    {
        employeeRepository.updateEmployee(employeeModel);
    }


}
