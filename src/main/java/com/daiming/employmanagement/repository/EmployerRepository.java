package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    Employer findByEmail(String email);
}
