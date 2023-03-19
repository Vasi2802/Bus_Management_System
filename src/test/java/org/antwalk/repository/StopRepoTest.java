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

	@MockBean
	private StopRepo stopRepo;
	
	@Test
	void testFindByName() {
		
		Stop expected = new Stop(1,"stop name");
		when(stopRepo.findByName("stop name")).thenReturn(expected);
		
		Stop actual = stopRepo.findByName("stop name");
		
		assertEquals(expected,actual);
		
	}

}
