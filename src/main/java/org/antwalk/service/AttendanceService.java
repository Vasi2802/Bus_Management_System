package org.antwalk.service;

import org.antwalk.entity.Attendance;
import org.antwalk.repository.AttendanceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {


    @Autowired
    private AttendanceRepo attendanceRepo;

    public void save(Attendance attendance) {
        attendanceRepo.save(attendance);
    }
    
}
