package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployerRepository extends JpaRepository<Employer, Long> {

}
