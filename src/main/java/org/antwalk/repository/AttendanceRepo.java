package org.antwalk.repository;

import java.time.LocalDate;
import java.util.List;

import org.antwalk.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepo extends JpaRepository<Attendance, Long> {

    public List<Attendance> findAllByAttendanceDateGreaterThanEqualAndAttendanceDateLessThanEqualOrderByEmployeeId(LocalDate startDate,
            LocalDate endDate);

    
}
