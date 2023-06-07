package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.AddUserFailedException;
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

    public Token employeeLogin(Employee employee) {

        if (!authenticate(employee)) {
            throw new AuthenticationFailedException("Wrong password");
        }
        return new Token(jwtUtil.generateToken("EMPLOYEE" + employee.getEmail()));
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

    @Transactional
    public void addEmployee(Employee employee, String employerEmail) {
        //encrypt password before storing
        employee.setEmployer(employerRepository.findByEmail(employerEmail));
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //assign employer before storing
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddUserFailedException("add user failed");
        }
    }

    @Transactional
    public List<Employee> getEmployeesByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return employeeRepository.findEmployeesByEmployer(employer);
    }
    @Transactional
    public List<Employee> getAvailableEmployeesByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return employeeRepository.findEmployeesByEmployerAndActiveIsTrue(employer);
    }

    /**
     * employer can manage employee's and pay rate
     * @param employee
     */
    public void updateEmployeeByEmployer(Employee employee) {
        Employee storedEmployee =  employeeRepository.findByEmail(employee.getEmail());
        if (employee.getPayRate() != null) {
            storedEmployee.setPayRate(employee.getPayRate());
        }
        employeeRepository.save(storedEmployee);
    }

    /**
     * employees can change their own email, password, active status, firstname, lastname
     * @param employee
     */
    public void updateEmployeeByEmployee(Employee employee) {
        Employee storedEmployee =  employeeRepository.findByEmail(employee.getEmail());
        if (employee.getEmail() != null) {
            storedEmployee.setEmail(employee.getEmail());
        }
        if (employee.getPassword() != null) {
            storedEmployee.setPassword(employee.getPassword());
        }
        if (employee.getActive() != null) {
            storedEmployee.setActive(employee.getActive());
        }
        if (employee.getFirstName() != null) {
            storedEmployee.setFirstName(employee.getFirstName());
        }
        if (employee.getLastName() != null) {
            storedEmployee.setLastName(employee.getLastName());
        }
        employeeRepository.save(storedEmployee);
    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        employee = employeeRepository.findByEmail(employee.getEmail());
        Employer employer = employee.getEmployer();
        employer.getEmployees().remove(employee);
        employerRepository.save(employer);
        employeeRepository.deleteEmployeeByEmail(employee.getEmail());
    }

}
