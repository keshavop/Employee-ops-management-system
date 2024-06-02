package com.example.authbackend.controller;

import com.example.authbackend.components.JwtHelper;
import com.example.authbackend.models.LoginRequestModel;
import com.example.authbackend.models.LoginResponseModel;
import com.example.authbackend.models.Usermodels;
import com.example.authbackend.repository.UserRepository;
import com.example.authbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {




    @Autowired
    private JwtHelper helper;

    @Autowired
    UserService userService;

    private Logger log=Logger.getLogger(String.valueOf(AuthController.class));


    @PostMapping("/createUser")
    public ResponseEntity<LoginResponseModel>  addUser(@RequestBody Usermodels usermodels)
    {
        try {
                    this.userService.addUser(usermodels);
                    String token = this.helper.generateToken(usermodels.getEmail());
                    LoginResponseModel response = new LoginResponseModel(token,userService.getUsername(usermodels.getEmail()));
                    return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e)
        {
            log.info(e.getMessage());
            return new ResponseEntity<>(new LoginResponseModel(e.getMessage(),null), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> login(@RequestBody LoginRequestModel request) {
        try {
            this.doAuthenticate(request.getEmail(), request.getPassword());
            String token = this.helper.generateToken(request.getEmail());
            LoginResponseModel response = new LoginResponseModel(token,userService.getUsername(request.getEmail()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(new LoginResponseModel(e.getMessage(),null), HttpStatus.BAD_REQUEST);
        }
    }

    private void doAuthenticate(String email, String password) {
        try {
            if(!userService.authenticate(email, password))
            {
                throw new BadCredentialsException(" Invalid Username or Password  !!");
            }
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
