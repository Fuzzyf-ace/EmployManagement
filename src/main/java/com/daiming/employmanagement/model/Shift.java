package com.daiming.employmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employer")
    private Employer employer;

    @OneToMany(mappedBy = "shift", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<WorkRecord> workRecords;
    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start_time", nullable = false)
    @JsonProperty("start_time")
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    @JsonProperty("end_time")
    private Instant endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public List<WorkRecord> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(List<WorkRecord> workRecords) {
        this.workRecords = workRecords;
    }
}