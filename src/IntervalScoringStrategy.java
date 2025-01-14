/**
 * Concrete ScoreingStrategy class based on the interval scoring strategy
 * STATE PATTERN
 */
public class IntervalScoringStrategy implements ScoreingStrategy{
	
	private RawScoringStrategy rawScoringStrategy;
	
	public IntervalScoringStrategy(float difficultyLevel) {
		rawScoringStrategy = new RawScoringStrategy(difficultyLevel);
	}
	
	/**
	 * 
	 * @param timeDifference 
	 * @param currentCombo 
	 * @return 
	 */

	@Override
	public int calculateScore(int timeDifference, int currentCombo) {
		return rawScoringStrategy.calculateScore(timeDifference, currentCombo) * currentCombo;
	}
	
}
