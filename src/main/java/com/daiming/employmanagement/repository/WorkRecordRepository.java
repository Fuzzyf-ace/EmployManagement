package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employee;
import com.daiming.employmanagement.model.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {

    WorkRecord findWorkRecordById(Long id);

    List<WorkRecord> findWorkRecordsByEmployee(Employee employee);
}
