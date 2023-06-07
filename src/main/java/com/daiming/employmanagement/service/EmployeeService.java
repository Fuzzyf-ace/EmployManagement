package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.AuthenticationFailedException;
import com.daiming.employmanagement.exception.UserDoesNotExistException;
import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Token;
import com.daiming.employmanagement.repository.EmployeeRepository;
import com.daiming.employmanagement.repository.EmployerRepository;
import com.daiming.employmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Transactional
    public void addEmployee(Employee employee, String employerEmail) {
        employee.setEmployer(employerRepository.findByEmail(employerEmail));
        employeeRepository.save(employee);
    }

    @Transactional
    public List<Employee> getEmployeesByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return employeeRepository.findEmployeesByEmployer(employer);
    }


    public Token employeeLogin(Employee employee) {

        if (!authenticate(employee)) {
            throw new AuthenticationFailedException("Wrong password");
        }

        return new Token(jwtUtil.generateToken(employee.getEmail()));

    }

    private boolean authenticate(Employee employee) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            Employee existedEmployee = employeeRepository.findByEmail(employee.getEmail());
            if (passwordEncoder.matches(employee.getPassword(), existedEmployee.getPassword())) {
                return true;
            }
        } catch (NullPointerException e) {
            throw new UserDoesNotExistException("User does not exist");
        }
        return false;
    }


}
