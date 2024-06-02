package com.example.authbackend.controller;

import com.example.authbackend.components.JwtHelper;
import com.example.authbackend.dto.DeleteEmployeeRequest;
import com.example.authbackend.models.EmployeeModel;
import com.example.authbackend.service.EmployeeService;
import com.example.authbackend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    public final UserService userService;
    public final JwtHelper jwtHelper;
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(UserService userService, JwtHelper jwtHelper, EmployeeService employeeService) {
        this.userService = userService;
        this.jwtHelper=jwtHelper;
        this.employeeService = employeeService;
    }


    @PostMapping("/createEmployee")
    public ResponseEntity<String> addEmployee(@RequestBody EmployeeModel employeeModel, HttpServletRequest request) {

        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String token = requestHeader.substring(7);
            String email=jwtHelper.getUsernameFromToken(token);
            UUID userId=userService.getIdByEmail(email);
            if(userId==null) {
              return  new ResponseEntity<>("User Not Found",HttpStatusCode.valueOf(404));
            }
            employeeModel.setEmplId(userId.toString());
            employeeService.saveEmployee(employeeModel);
            return new ResponseEntity<>("Saved",HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Token Not Found",HttpStatusCode.valueOf(404));
    }

    @GetMapping("/getEmployeeData")
    public ResponseEntity<List<EmployeeModel>> getEmployees( HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            String token = requestHeader.substring(7);
            String email=jwtHelper.getUsernameFromToken(token);
            UUID userId=userService.getIdByEmail(email);
            List<EmployeeModel> res=employeeService.getEmployeeById(userId.toString());
            return new ResponseEntity<>(res,HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>(HttpStatusCode.valueOf(404));
    }

    @DeleteMapping("/deleteEmployee")
    public ResponseEntity<String> deleteEmployee(@RequestBody DeleteEmployeeRequest deleteEmployeeRequest, HttpServletRequest request) {
        try {
            employeeService.deleteEmployee(deleteEmployeeRequest.getEmplId());
            return new ResponseEntity<>("Deleted",HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(404));
        }
    }

    @PutMapping("/updateEmployee")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeModel employeeModel, HttpServletRequest request) {
        try {
            employeeService.updateEmployee(employeeModel);
            return new ResponseEntity<>("updated",HttpStatusCode.valueOf(200));
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatusCode.valueOf(404));
        }
    }






}
