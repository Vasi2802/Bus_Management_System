package org.antwalk.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class DemoSecurityConfigTest {

//	@Test
//	void testConfigureAuthenticationManagerBuilder() {
//		fail("Not yet implemented");
//	}
	@Autowired
	private MockMvc mvc;

	@Test
	@WithUserDetails("")
	void testConfigureHttpSecurity() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/"))
		.andExpect(MockMvcResultMatchers.);
	}

//	@Test
//	void testPasswordEncoder() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAuthenticationProvider() {
//		fail("Not yet implemented");
//	}

}
