import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * test cases for the raw scoring strategy
 */
class TestRawScoringStrategy {

	private RawScoringStrategy rawScoringStrategy1;
	private RawScoringStrategy rawScoringStrategy2;
	private RawScoringStrategy rawScoringStrategy3;
	
	@BeforeEach
	void setUp() throws Exception {
		rawScoringStrategy1 = new RawScoringStrategy(1);
		rawScoringStrategy2 = new RawScoringStrategy(2);
		rawScoringStrategy3 = new RawScoringStrategy(3);
	}
	
	

	//Difficulty 1
	@Test
	void test300PointScoreDifficulty1High() {
		assertEquals(rawScoringStrategy1.calculateScore(74, 5), 300);
	}
	@Test
	void test100PointScoreDifficulty1Low() {
		assertEquals(rawScoringStrategy1.calculateScore(75, 9), 100);
	}
	@Test
	void test100PointScoreDifficulty1High() {
		assertEquals(rawScoringStrategy1.calculateScore(132, 14), 100);
	}
	@Test
	void test50PointScoreDifficulty1Low() {
		assertEquals(rawScoringStrategy1.calculateScore(133, 23), 50);
	}
	@Test
	void test50PointScoreDifficulty1High() {
		assertEquals(rawScoringStrategy1.calculateScore(190, 22), 50);
	}
	@Test
	void test0PointScoreDifficulty1Low() {
		assertEquals(rawScoringStrategy1.calculateScore(191, 11), 0);
	}
	
	
	//Difficulty2
	@Test
	void test300PointScoreDifficulty2High() {
		assertEquals(rawScoringStrategy2.calculateScore(68, 18), 300);
	}
	
	@Test
	void test100PointScoreDifficulty2Low() {
		assertEquals(rawScoringStrategy2.calculateScore(69, 27), 100);
	}
	
	@Test
	void test100PointScoreDifficulty2High() {
		assertEquals(rawScoringStrategy2.calculateScore(124, 40), 100);
	}
	
	@Test
	void test50PointScoreDifficulty2Low() {
		assertEquals(rawScoringStrategy2.calculateScore(125, 1), 50);
	}
	
	@Test
	void test50PointScoreDifficulty2High() {
		assertEquals(rawScoringStrategy2.calculateScore(180, 9), 50);
	}
	
	@Test
	void test0PointScoreDifficulty2Low() {
		assertEquals(rawScoringStrategy2.calculateScore(181, 4), 0);
	}
	
	//Difficulty3
		@Test
		void test300PointScoreDifficulty3High() {
			assertEquals(rawScoringStrategy3.calculateScore(62, 0), 300);
		}
		
		@Test
		void test100PointScoreDifficulty3Low() {
			assertEquals(rawScoringStrategy3.calculateScore(63, 0), 100);
		}
		
		@Test
		void test100PointScoreDifficulty3High() {
			assertEquals(rawScoringStrategy3.calculateScore(116, 3), 100);
		}
		
		@Test
		void test50PointScoreDifficulty3Low() {
			assertEquals(rawScoringStrategy3.calculateScore(117, 17), 50);
		}
		
		@Test
		void test50PointScoreDifficulty3High() {
			assertEquals(rawScoringStrategy3.calculateScore(170, 24), 50);
		}
		
		@Test
		void test0PointScoreDifficulty3Low() {
			assertEquals(rawScoringStrategy3.calculateScore(171, 3), 0);
		}
	
	
	
	

}
