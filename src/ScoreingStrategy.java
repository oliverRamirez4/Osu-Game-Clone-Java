/**
 * Interface for scoring clicks. Different strategies can be used for scoring. 
 * STRATEGY PATTERN
 */
public interface ScoreingStrategy {
	
	/**
	 * 
	 * @param timeDifference – difference in between user click time and correct Click time
	 * @param currentCombo – used as a multiplier based on consecutive hitObjects clicked
	 * @return – returns the score
	 */
	
	public int calculateScore(int timeDifference, int currentCombo);
}
