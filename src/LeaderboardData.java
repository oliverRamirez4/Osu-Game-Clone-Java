import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * Class for handling Leaderboard data
 * Server is hosted by Colorado College math and computer science server
 */
public class LeaderboardData {
	// Set up URL parameters
    private static final String DB_NAME = "OsuProject"; 
    private static final String DB_USER = "k_chang";
    private static final String DB_PASSWORD = "Changeme_00";
    private static final String CONNECTION_NAME = "jdbc:mysql://localhost:1521/" + DB_NAME + "?user=" + DB_USER + "&password=" + DB_PASSWORD;
 
    
    /**
     * Submit a username and a score to the OsuProject database - on the leaderboard table
     * @param playerUsername - the Osu player's username
     * @param score - the score that the player received
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void submitToLeaderboard(String playerUsername, int score) throws SQLException, ClassNotFoundException {
    	System.out.println("Connection name: " + CONNECTION_NAME);
		
		try (
			//Step 1: Allocate a DB "Connection" object
			Connection conn = DriverManager.getConnection(CONNECTION_NAME);
			Statement stmt = conn.createStatement();
			) {
				//Insert values into Student
				String addPlayerScore = "INSERT INTO leaderboard values('" + playerUsername +"', "+ score + ");";
				System.out.println("Inserted three students " + addPlayerScore);
				int countInserted = stmt.executeUpdate(addPlayerScore);
			}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Can not connect to leaderboard");
		}
    }
    
    /**
     * Gets the top 10 scores in OSU history OR top scores if there are less than 10
     * @return an ArrayList of String containing the username and score. Format: "Username: [username] Score: [score]"
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ArrayList<String> getLeaderboardTop10() throws SQLException, ClassNotFoundException{
    	 System.out.println("Connection name: " + CONNECTION_NAME);
    	 
    	 ArrayList<String> topScores = new ArrayList<String>();
    		
    		try (
				//Step 1: Allocate a DB "Connection" object
				Connection conn = DriverManager.getConnection(CONNECTION_NAME);
				//Step 2: 
				Statement stmt = conn.createStatement();
    			) {
					//Step 3: Execute a SQL query, the query result is returned
					//in a "ResultSet" object
					String strSelect = "select * from leaderboard order by score desc";
					System.out.println("The SQL query is: " + strSelect);
					System.out.println();
		
					ResultSet rset = stmt.executeQuery(strSelect);
		
					//Step 4: Process the result set by stepping through using next().
					//For each row, retrieve the contents  using the column names
					System.out.println("The top values of the leaderboard are:");;
					int rowCount = 0;
					while (rset.next() && rowCount < 10) {
						String playerUsername = rset.getString("username");
						int score = rset.getInt("score");
						System.out.println(playerUsername + ", " + score);
						topScores.add("Username: " +playerUsername + "Score: "+ score);
						++rowCount;
					}
					System.out.println("Total number of records = " + rowCount);
					return topScores;

	    		} catch (Exception e) {
	    			topScores.add("No Connection to LeaderBoard");
	    		}
    		//Step 5: close the resources - done automatically by try-with-resources
    		//return empty list
    		return topScores;

    }
    public static String getUsername() throws SQLException, ClassNotFoundException{
    	System.out.println("Connection name: " + CONNECTION_NAME);
   	 
   	 	String username = null;
   		
   		try (
				Connection conn = DriverManager.getConnection(CONNECTION_NAME);
				//Step 2: 
				Statement stmt = conn.createStatement();
   			) {
					String strSelect = "select * from leaderboard order by score desc";
					System.out.println("The SQL query is: " + strSelect);
					System.out.println();
		
					ResultSet rset = stmt.executeQuery(strSelect);
		
					System.out.println("The top values of the leaderboard are:");;
					int rowCount = 0;
					while (rset.next() && rowCount < 10) {
						String playerUsername = rset.getString("username");
						System.out.println(playerUsername);
						username = playerUsername;
					}
	    		} catch (Exception e) {
	    			System.out.println("No Connection to LeaderBoard");
	    		}
   		return username;

    	
    }
  
}
    
    

