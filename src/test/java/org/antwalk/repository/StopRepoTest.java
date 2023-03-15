package org.antwalk.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.antwalk.entity.Stop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StopRepoTest {

	@Autowired
	private StopRepo stopRepo;
	
	@Test
	void testFindByName() {
		
		String stopName = "test stop for repo";
		
		Stop expected = new Stop(stopName);
		stopRepo.save(expected);
		
		Stop actual = stopRepo.findByName(stopName);
		assertNotNull(actual);
		assertEquals(expected, actual);
		
	}

}
