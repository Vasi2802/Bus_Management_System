package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.antwalk.entity.Stop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
class StopRepoTest {

//	@MockBean
	
	@Autowired
	private StopRepo stopRepo;
	
//	@Test
//	void testFindByName() {
//		
//		Stop expected = new Stop(1,"stop name");
//		when(stopRepo.findByName("stop name")).thenReturn(expected);
//		
//		Stop actual = stopRepo.findByName("stop name");
//		
//		assertEquals(expected,actual);
//		
//	}
	
	@Test
	void testFindByName() {
		
		Stop expected = stopRepo.findById((long) 1).get();
		Stop actual = stopRepo.findByName("Belgharia");
		
//		System.out.println(expected.getName() + "          " + actual.getName());
		
		assertEquals(expected.getSid(),actual.getSid());
		assertEquals(expected.getName(),actual.getName());
		
	}

}
