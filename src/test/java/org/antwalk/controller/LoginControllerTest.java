package org.antwalk.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // to display login page
    @Test
    public void testLoginForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showMyLoginPage"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login.html"));
    }

    // Testing with correct Data-Admin
    @Test
    public void testLoginSuccessAdmin() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticateTheUser")
                .param("username", "admin@nrifintech.com")
                .param("password", "fun123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin"));
    }

    // Testing with correct Data-Employee
    @Test
    public void testLoginSuccessEmployee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticateTheUser")
                .param("username", "subhashreem@trainee.nrifintech.com")
                .param("password", "fun123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/employee"));
    }
    
    // Testing with correct Data-Driver
    @Test
    public void testLoginSuccessDriver() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/authenticateTheUser")
                .param("username", "driver2@driver.nrifintech.com")
                .param("password", "fun123"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/driver"));
    }
    
 // Testing with incorrect Data
    @Test
    public void testLoginFailure() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/authenticateTheUser")
                .param("username", "wronguser")
                .param("password", "wrongpass"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/showMyLoginPage?error"));
    }
}