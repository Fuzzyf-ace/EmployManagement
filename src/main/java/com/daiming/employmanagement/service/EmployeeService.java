package com.daiming.employmanagement.service;

import com.daiming.employmanagement.exception.*;
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

    public void addEmployee(Employee employee, String employerEmail) {
        //encrypt password before storing
        Employer employer = employerRepository.findByEmail(employerEmail);
        if (employer == null) {
            throw new EmployerDoesNotExistException("Employer does not exist");
        }
        employee.setEmployer(employer);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //assign employer before storing
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        try {
            employeeRepository.save(employee);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AddUserFailedException("User with the same email already exists");
        }
    }

    public List<Employee> getEmployeesByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return employeeRepository.findEmployeesByEmployer(employer);
    }
    public List<Employee> getAvailableEmployeesByEmployer(String employerEmail) {
        Employer employer = employerRepository.findByEmail(employerEmail);
        return employeeRepository.findEmployeesByEmployerAndActiveIsTrue(employer);
    }

    /**
     * employer can manage employee's and pay rate
     * @param employee
     */
    public void updateEmployeeByEmployer(Employee employee) {
        Employee storedEmployee = null;
        try {
            storedEmployee =  employeeRepository.findByEmail(employee.getEmail());
            if (employee.getPayRate() != null) {
                storedEmployee.setPayRate(employee.getPayRate());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        employeeRepository.save(storedEmployee);
    }

    /**
     * employees can change their own email, password, active status, firstname, lastname
     * @param employee
     */
    public void updateEmployeeByEmployee(Employee employee) {
        Employee storedEmployee = null;
        try {
            storedEmployee = employeeRepository.findByEmail(employee.getEmail());
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
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        employeeRepository.save(storedEmployee);
    }

    @Transactional
    public void deleteEmployee(Employee employee) {
        Employer employer = null;
        try {
            employee = employeeRepository.findByEmail(employee.getEmail());
            employer = employee.getEmployer();
            employer.getEmployees().remove(employee);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EmployeeDoesNotExistException("Employee does not exist");
        }
        employerRepository.save(employer);
        employeeRepository.deleteEmployeeByEmail(employee.getEmail());
    }

}
