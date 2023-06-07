package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.Employer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findEmployeesByEmployer(Employer employer);

    List<Employee> findEmployeesByEmployerAndActiveIsTrue(Employer employer);

    Employee findByEmail(String email);

    void deleteEmployeeByEmail(String email);
}
