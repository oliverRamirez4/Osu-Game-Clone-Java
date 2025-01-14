import java.awt.Point;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
	
	/**
	 * Main takes you to the login page (and the buttons on the login page take you through the game
	 * To efficiently work with the different windows, we can call an instance of them in the game,
	 * as seen through the commented out instances below new LoginPage()
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String args[]) throws IOException, ClassNotFoundException, SQLException {

		new LoginPage();
		//new MainMenu();
		//new PlayAgain();
		//new PauseMenu(null);
	}
}
