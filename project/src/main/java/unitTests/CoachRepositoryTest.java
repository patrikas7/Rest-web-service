package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import repository.CoachRepository;
import objects.Coach;

class CoachRepositoryTest {
	
	CoachRepository coachRepository = new CoachRepository();
	Coach expectedCoach = new Coach("Martin", "Schiller", "Austras", "Kauno žalgiris", 1);
	
	@Test
	void getCoach_ExistingCoach_True() {	
		Coach coach = coachRepository.getCoach("Martin", "Schiller");
		assertEquals(expectedCoach, coach);
	}
	
	@Test
	void getCoach_ExistingCoach_False() {
		Coach coach = coachRepository.getCoach("Martin", "NotSchiller");
		assertNotEquals(expectedCoach, coach);
	}
	
}
