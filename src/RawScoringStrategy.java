/**
 * Concrete ScoreingStrategy class calculate the raw score
 */
public class RawScoringStrategy implements ScoreingStrategy{
	
	/**
	 * These instance variables represent the time window in which certain amount of points are awarded
	 * For example, if the user clicks within the timeWindow300, they awarded at least 300 points
	 */
	
	private float timeWindow300;
	private float timeWindow100;
	private float timeWindow50;
	
	/**
	 * The constructor takes in a float representing the difficulty of the beatMap
	 * @param difficultyLevel 
	 */
	
	public RawScoringStrategy(float difficultyLevel) {
		this.timeWindow300 = 80 - 6 * difficultyLevel;
		this.timeWindow100 = 140 - 8 * difficultyLevel;
		this.timeWindow50 = 200 - 10 * difficultyLevel;
	}

	@Override
	public int calculateScore(int timeDifference, int currentCombo) {
		if (timeDifference <= timeWindow300) {
			return 300;
		}
		else if (timeDifference <= timeWindow100) {
			return 100;
		}
		else if (timeDifference <= timeWindow50) {
			return 50;
		}
		else {
			return 0;
		}
	}

}
