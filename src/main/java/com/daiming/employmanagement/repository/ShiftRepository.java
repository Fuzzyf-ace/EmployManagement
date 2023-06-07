package com.daiming.employmanagement.repository;

import com.daiming.employmanagement.model.Employer;
import com.daiming.employmanagement.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepository extends JpaRepository<Shift, Long> {
    List<Shift> findShiftsByEmployer(Employer employer);

}
