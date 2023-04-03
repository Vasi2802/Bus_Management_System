package org.antwalk.controller;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;

import javax.servlet.http.HttpSession;

import org.antwalk.entity.Employee;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.entity.User;
import org.antwalk.service.DriverService;
import org.antwalk.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    private Long employeeId;
    private Long busId;
    private Long stopId;
    private Employee employee;
    
    @BeforeEach
	void setUp() {
    	employeeId = 1L;
    	busId = 1L;
    	stopId = 3L;
    	employee = new Employee();
    	employee.setEid(employeeId);
    }

    @Test
    public void bookABusByBusId_shouldReturnBadRequest_whenEmployeeAlreadyBooked() throws Exception {
        
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(employeeId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    
    
    @Test
    public void bookABusByBusId_shouldReturnBadRequest_whenEmployeeAlreadyinWaitingList() throws Exception {
    	employee.setEid(9L); 
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(9L)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
    
    @Test
    public void bookABusByBusId_shouldReturnOk_whenEmployeeNotBookedandSeatAvailable() throws Exception {
    	employee.setEid(12L); 
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(12L)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void bookABusByBusId_shouldReturnBadRequest__whenEmployeeNotBookedButSeatNotAvailable() throws Exception {
    	employee.setEid(13L); 
    	busId = 4L;
    	stopId = 17L;
    	
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(13L)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }
}
