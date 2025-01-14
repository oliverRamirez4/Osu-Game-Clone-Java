import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Class that starts the program
 */
public class RunGame {
	
	private String songName;
	
	public RunGame(String songToPlay) {
		this.songName = songToPlay;
		JPanel panel = new GamePlayPanel(songName);
	//Run the game	
		
		
	}
	
}