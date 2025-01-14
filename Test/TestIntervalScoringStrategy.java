import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for the interval scoring strategy
 */
class TestIntervalScoringStrategy {

	private IntervalScoringStrategy intervalScoringStrategy1;
	private IntervalScoringStrategy intervalScoringStrategy2;
	private IntervalScoringStrategy intervalScoringStrategy3;
	
	@BeforeEach
	void setUp() throws Exception {
		intervalScoringStrategy1 = new IntervalScoringStrategy(1);
		intervalScoringStrategy2 = new IntervalScoringStrategy(2);
		intervalScoringStrategy3 = new IntervalScoringStrategy(3);
	}
	
	

	//Difficulty 1
	@Test
	void test300PointScoreDifficulty1High() {
		assertEquals(intervalScoringStrategy1.calculateScore(74, 5), 300 * 5);
	}
	@Test
	void test100PointScoreDifficulty1Low() {
		assertEquals(intervalScoringStrategy1.calculateScore(75, 9), 100 * 9);
	}
	@Test
	void test100PointScoreDifficulty1High() {
		assertEquals(intervalScoringStrategy1.calculateScore(132, 14), 100 * 14);
	}
	@Test
	void test50PointScoreDifficulty1Low() {
		assertEquals(intervalScoringStrategy1.calculateScore(133, 23), 50 * 23);
	}
	@Test
	void test50PointScoreDifficulty1High() {
		assertEquals(intervalScoringStrategy1.calculateScore(190, 22), 50 * 22);
	}
	@Test
	void test0PointScoreDifficulty1Low() {
		assertEquals(intervalScoringStrategy1.calculateScore(191, 11), 0 * 11);
	}
	
	
	//Difficulty2
	@Test
	void test300PointScoreDifficulty2High() {
		assertEquals(intervalScoringStrategy2.calculateScore(68, 18), 300 * 18);
	}
	
	@Test
	void test100PointScoreDifficulty2Low() {
		assertEquals(intervalScoringStrategy2.calculateScore(69, 27), 100 * 27);
	}
	
	@Test
	void test100PointScoreDifficulty2High() {
		assertEquals(intervalScoringStrategy2.calculateScore(124, 40), 100 * 40);
	}
	
	@Test
	void test50PointScoreDifficulty2Low() {
		assertEquals(intervalScoringStrategy2.calculateScore(125, 1), 50 * 1);
	}
	
	@Test
	void test50PointScoreDifficulty2High() {
		assertEquals(intervalScoringStrategy2.calculateScore(180, 9), 50 * 9);
	}
	
	@Test
	void test0PointScoreDifficulty2Low() {
		assertEquals(intervalScoringStrategy2.calculateScore(181, 4), 0 * 4);
	}
	
	//Difficulty3
		@Test
		void test300PointScoreDifficulty3High() {
			assertEquals(intervalScoringStrategy3.calculateScore(62, 0), 300 * 0);
		}
		
		@Test
		void test100PointScoreDifficulty3Low() {
			assertEquals(intervalScoringStrategy3.calculateScore(63, 0), 100 * 0);
		}
		
		@Test
		void test100PointScoreDifficulty3High() {
			assertEquals(intervalScoringStrategy3.calculateScore(116, 3), 100 * 3);
		}
		
		@Test
		void test50PointScoreDifficulty3Low() {
			assertEquals(intervalScoringStrategy3.calculateScore(117, 17), 50 * 17);
		}
		
		@Test
		void test50PointScoreDifficulty3High() {
			assertEquals(intervalScoringStrategy3.calculateScore(170, 24), 50 * 24);
		}
		
		@Test
		void test0PointScoreDifficulty3Low() {
			assertEquals(intervalScoringStrategy3.calculateScore(171, 3), 0 * 3);
		}
	
	
	
	

}
