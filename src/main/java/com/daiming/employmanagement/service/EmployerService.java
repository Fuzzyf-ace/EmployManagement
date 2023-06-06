package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.AddUserFailedException;
import com.daiming.employmanagement.exception.AuthenticationFailedException;
import com.daiming.employmanagement.exception.UserDoesNotExistException;
import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Token;
import com.daiming.employmanagement.repository.EmployerRepository;
import com.daiming.employmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployerService {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    EmployerRepository employerRepository;
    public void addEmployer(Employer employer) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        employer.setPassword(passwordEncoder.encode(employer.getPassword()));
        try {
        employerRepository.save(employer);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddUserFailedException("add user failed");
        }
    }

    public Token employerLogin(Employer employer) {

        if (!authenticate(employer)) {
            throw new AuthenticationFailedException("Wrong password");
        }

        return new Token(jwtUtil.generateToken(employer.getEmail()));

    }

    private boolean authenticate(Employer employer) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            Employer existedEmployer = employerRepository.findByEmail(employer.getEmail());
            if (passwordEncoder.matches(employer.getPassword(), existedEmployer.getPassword())) {
                return true;
            }
        } catch (NullPointerException e) {
            throw new UserDoesNotExistException("Employer does not exist");
        }
        return false;
    }
}
