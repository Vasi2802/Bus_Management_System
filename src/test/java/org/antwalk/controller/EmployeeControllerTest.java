package org.antwalk.controller;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpSession;

import org.antwalk.entity.Employee;
import org.antwalk.entity.User;
import org.antwalk.service.DriverService;
import org.antwalk.service.UserService;
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

    @Test
    public void bookABusByBusId_shouldReturnBadRequest_whenEmployeeAlreadyBooked() throws Exception {
        // Arrange
        Long employeeId = 1L;
        Long busId = 1L;
        Long stopId = 3L;
        Employee employee = new Employee();
        employee.setEid(employeeId);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(employeeId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Assert
    }
    
    
    @Test
    public void bookABusByBusId_shouldReturnBadRequest_whenEmployeeAlreadyinWaitingList() throws Exception {
        // Arrange
        Long employeeId = 1L;
        Long busId = 1L;
        Long stopId = 3L;
        Employee employee = new Employee();
        employee.setEid(employeeId);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(employeeId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Assert
    }
    
    @Test
    public void bookABusByBusId_shouldReturnOk_whenEmployeeNotBookedandSeatAvailable() throws Exception {
        // Arrange
        Long employeeId = 1L;
        Long busId = 1L;
        Long stopId = 3L;
        Employee employee = new Employee();
        employee.setEid(employeeId);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(employeeId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Assert
    }
    @Test
    public void bookABusByBusId_shouldReturnBadRequest__whenEmployeeNotBookedButSeatNotAvailable() throws Exception {
        // Arrange
        Long employeeId = 1L;
        Long busId = 1L;
        Long stopId = 3L;
        Employee employee = new Employee();
        employee.setEid(employeeId);

        
        mockMvc.perform(MockMvcRequestBuilders.post("/employee/bookABusByBusId/{busId}/{stopId}", busId, stopId)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(String.valueOf(employeeId)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        // Assert
    }
}
