package org.antwalk.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendanceId; 

    @OneToOne
    @JoinColumn(name= "bus_id", referencedColumnName = "bid")
    private Bus bus;

    @OneToOne
    @JoinColumn(name= "employee_id", referencedColumnName = "eid")
    private Employee employee;

    private LocalDate attendanceDate;

    public Attendance() {
    }

    public Attendance(Long attendanceId, Bus bus, Employee employee, LocalDate attendanceDate) {
        this.attendanceId = attendanceId;
        this.bus = bus;
        this.employee = employee;
        this.attendanceDate = attendanceDate;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Bus getbus() {
        return bus;
    }

    public void setbus(Bus bus) {
        this.bus = bus;
    }

    public Employee getemployee() {
        return employee;
    }

    public void setemployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

}