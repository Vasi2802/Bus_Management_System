package org.antwalk.controller;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.antwalk.entity.User;
import org.antwalk.service.DriverService;
import org.antwalk.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private DriverService driverService;

    @Test	
    public void testUpdate() throws Exception {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("testpassword");
        when(session.getAttribute("driver")).thenReturn(user);
        when(userService.findByUserName("testuser")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/driver/updateprofile")
                .param("eid", "1")
                .param("contactNo", "1234567890")
                .param("password", "newpassword")
                .sessionAttr("driver", user))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    
    @Test	
    public void testGetAllEmployees() throws Exception {
        HttpSession session = mock(HttpSession.class);
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("testpassword");
        when(session.getAttribute("driver")).thenReturn(user);
        when(userService.findByUserName("testuser")).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/driver/get-all-passengers")
                .sessionAttr("driver", user))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
    
    @Test	
    public void flushDelay() throws Exception {
       
        mockMvc.perform(MockMvcRequestBuilders.get("/driver/flushDelays/"+1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}