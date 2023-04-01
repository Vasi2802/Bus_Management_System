package org.antwalk.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId; 

    private Long busId;

    private Long employeeId;

    private String employeeName;

    private String stopName;
    
    private String shift;

    private LocalDate attendanceDate;

    public Attendance() {
    }

    public Attendance(Long attendanceId, Long busId, Long employeeId, LocalDate attendanceDate) {
        this.attendanceId = attendanceId;
        this.busId = busId;
        this.employeeId = employeeId;
        this.attendanceDate = attendanceDate;
    }

    

    public Attendance(Long attendanceId, Long busId, Long employeeId, String employeeName, String stopName, String shift,
            LocalDate attendanceDate) {
        this.attendanceId = attendanceId;
        this.busId = busId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.stopName = stopName;
        this.shift = shift;
        this.attendanceDate = attendanceDate;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    

}