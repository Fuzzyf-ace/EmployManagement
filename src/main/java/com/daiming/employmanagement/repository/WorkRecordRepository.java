package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRecordRepository extends JpaRepository<WorkRecord, Long> {

}
