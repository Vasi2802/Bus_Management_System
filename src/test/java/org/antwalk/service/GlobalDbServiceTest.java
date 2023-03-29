package org.antwalk.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.antwalk.entity.ArrivalTimeTable;
import org.antwalk.entity.GlobalDb;
import org.antwalk.entity.Route;
import org.antwalk.entity.RouteStopId;
import org.antwalk.entity.Stop;
import org.antwalk.repository.GlobalRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
class GlobalDbServiceTest {
	
	@Autowired
	private GlobalDbService globalService;
	
	@MockBean
	private GlobalRepo glovalRepo;
	
	private String email;

	
	@BeforeEach
	void setUp() {
		email = "subhashreem@trainee.nrifintech.com";
	}

	@Test
	void testGetAllAdmin() {
		List<GlobalDb> expected = new ArrayList<>();
		GlobalDb g1 = new GlobalDb(1, email);
		GlobalDb g2 = new GlobalDb(2, "shreyanshs@trainee.nrifintech.com");
		expected.add(g1);
		expected.add(g2);

		when(glovalRepo.findAll()).thenReturn(expected);
		
		List<GlobalDb> actual = globalService.getAllAdmin();
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
	}

}
