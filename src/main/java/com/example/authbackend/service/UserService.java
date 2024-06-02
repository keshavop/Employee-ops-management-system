package com.example.authbackend.service;

import com.example.authbackend.models.Usermodels;
import com.example.authbackend.repository.UserRepository;
import com.example.authbackend.utils.PassWordManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PassWordManager passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PassWordManager passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }





    public List<Usermodels> getAllUsers() {
        return this.userRepository.findAll();
    }

    public String addUser(Usermodels usermodels) {
        Optional<Usermodels> optionalUsers = userRepository.findByEmail(usermodels.getEmail());
        passwordEncoder.registerUser(usermodels);
        if (optionalUsers.isPresent()) {
            throw new IllegalStateException("User already exists");
        }
        userRepository.save(usermodels);
        return "Saved Data";
    }

    public void deleteUser(Long id) {
        boolean isIdPresent = userRepository.existsById(id);
        if (!isIdPresent) {
            throw new IllegalStateException("User does not exist");
        }
        userRepository.deleteById(id);
    }


    public boolean authenticate(String email, String password) {
        String optionalUser = userRepository.findByEmailandPass(email, password);
        return passwordEncoder.checkPassword(password, optionalUser);
    }

    public String getUsername(String email) {
        return userRepository.findByNameByEmail(email);
    }

    public UUID getIdByEmail(String email) {
        try {
            return userRepository.findByIdByEmail(email);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public boolean checkUserEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
