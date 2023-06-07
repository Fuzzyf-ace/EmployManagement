package com.daiming.employmanagement.controller;

import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Token;
import com.daiming.employmanagement.service.EmployeeService;
import com.daiming.employmanagement.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class EmployerController {
    @Autowired
    EmployerService employerService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/employer/signup")
    public String signup (@RequestBody Employer employer) {
        employerService.addEmployer(employer);
        return employer.getFirstName();
    }
    @PostMapping("/employer/login")
    public Token login(@RequestBody Employer employer) {
        return employerService.employerLogin(employer);
    }

    @GetMapping("/employer/getEmployees")
    public List<Employee> getEmployees(Principal principal) {
        List<Employee> employees = employeeService.getEmployeesByEmployer(principal.getName());
        return employees;
    }

    @GetMapping("/employer/getAvailableEmployees")
    public List<Employee> getAvailableEmployees(Principal principal) {
        List<Employee> employees = employeeService.getAvailableEmployeesByEmployer(principal.getName());
        return employees;
    }

    @PutMapping("/employer/addEmployee")
    public void addEmployee(@RequestBody Employee employee, Principal principal) {
        System.out.println("principal: " + principal.getName());
        System.out.println("employee: " + employee.toString());
        employeeService.addEmployee(employee, principal.getName());
    }

    @DeleteMapping("/employer/deleteEmployee")
    public void deleteEmployee(@RequestBody Employee employee) {
        employeeService.deleteEmployee(employee);
    }

    @PostMapping("/employer/updateEmployee")
    public void updateEmployeeByEmployer(@RequestBody Employee employee) {
        employeeService.updateEmployeeByEmployer(employee);
    }

}
