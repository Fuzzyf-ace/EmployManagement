package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findEmployeesByEmployer(Pageable pageable, Long employerId);

    Employee findByEmail(String email);
}
