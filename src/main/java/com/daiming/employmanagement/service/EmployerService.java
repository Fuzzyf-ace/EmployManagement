package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.AddUserFailedException;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployerService {

    @Autowired
    EmployerRepository employerRepository;
//    @Transactional
    public void addEmployer(Employer user) {
        try {
        employerRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddUserFailedException("add user failed");
        }
    }
}
