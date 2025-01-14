import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalButtonUI;

public class PlayAgain {
	BufferedImage playAgainImage;

	
	public PlayAgain() throws ClassNotFoundException, SQLException {
		/**
		 * Create frame for main menu
		 */
		JFrame playAgainFrame = new JFrame();
		playAgainFrame.setTitle("Game Over");
		playAgainFrame.setSize(1425, 730);
		playAgainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playAgainFrame.setVisible(true);
		
		/**
		 * read the image for the background
		 */
		try {
			playAgainImage = ImageIO.read(new File("PlayAgain.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		/**
		 * Paint the image for the background
		 */
	    JPanel gameOverPanel = new JPanel(){
	        @Override
	        protected void  paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(playAgainImage,0,0,null);
	        }
	    };	    
	    gameOverPanel.setLayout(new GridLayout(16, 2, 5, 5));
	    playAgainFrame.add(gameOverPanel);
	    
        
        //Add the title
		JLabel playAgainPanel = new JLabel("<html><font size='10' color=White>Game Over</font></html>", SwingConstants.CENTER);
		playAgainPanel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		playAgainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		gameOverPanel.add(playAgainPanel);
		
		//add start game
		JButton playAgain = new JButton("<html><font size='6' color=White>Play Again</font></html>");
		playAgain.setUI(new MetalButtonUI());
		playAgain.setFocusPainted(false);
		playAgain.setBackground(Color.BLACK);
		playAgain.setForeground(Color.WHITE);
		gameOverPanel.add(playAgain);
		playAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {				
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				playAgainFrame.dispose();
				try {
					new MainMenu();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		//add quit game
		JButton quitGame = new JButton("<html><font size='6' color=White>Quit Game</font></html>");
		quitGame.setUI(new MetalButtonUI());
		quitGame.setFocusPainted(false);
		quitGame.setBackground(Color.BLACK);
		quitGame.setForeground(Color.WHITE);
		gameOverPanel.add(quitGame);
		quitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				playAgainPanel.updateUI();
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				playAgainFrame.dispose();
				System.exit(0);
			}
		});
		
		JPanel displayPanel = new JPanel();
		displayPanel = new JPanel();
		//displayPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//displayPanel.setLayout(new BorderLayout(0, 0));
		
		displayPanel.setLayout(new GridLayout(16, 2, 5, 5));
		displayPanel.setBackground(Color.BLACK);
		playAgainFrame.getContentPane().add(displayPanel, BorderLayout.WEST);

		//TITLE FOR GAME STATS
		JLabel gameStatLabel = new JLabel("<html><font size='6' color=White>Game Stats</font></html>");
		//gameStatLabel.setText("Game Stats");
		gameStatLabel.setForeground(Color.WHITE);
		gameStatLabel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		displayPanel.add(gameStatLabel);

		
		//add player score
		int testScore = 100;
		JLabel playerScoreLabel = new JLabel();
		playerScoreLabel.setText("Player Score: " + testScore);
		playerScoreLabel.setForeground(Color.WHITE);
		playerScoreLabel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		displayPanel.add(playerScoreLabel);

		//display accuracy
		JLabel maxAccuracyLabel = new JLabel();//"<html><font size='8' color=White>Accuracy: </font></html>" + "HERE IT IS!", SwingConstants.CENTER);
		maxAccuracyLabel.setText("Accuracy Level: " + testScore);
		maxAccuracyLabel.setForeground(Color.WHITE);
		maxAccuracyLabel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		displayPanel.add(maxAccuracyLabel);
		
		//display max combo
		JLabel maxComboLabel = new JLabel();//"<html><font size='8' color=White>Max Combo: </font></html>" + "HERE IT IS!", SwingConstants.CENTER);
		maxComboLabel.setText("Max Combo: " + testScore);
		maxComboLabel.setForeground(Color.WHITE);
		maxComboLabel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		displayPanel.add(maxComboLabel);
		
		//display number of scores (like how many 300 scores you got)
		JLabel scoreCountlabel = new JLabel();//"<html><font size='8' color=White>Max Combo: </font></html>" + "HERE IT IS!", SwingConstants.CENTER);
		scoreCountlabel.setText("Score Breakdown: " + testScore);
		scoreCountlabel.setForeground(Color.WHITE);
		scoreCountlabel.setFont(playAgainPanel.getFont().deriveFont(Font.BOLD, 15f));
		displayPanel.add(scoreCountlabel);
		
		//LEADERBOARD
		JPanel leaderboardPanel = new JPanel();
		ArrayList<String> leaderboardData = LeaderboardData.getLeaderboardTop10();
		JList displayLeaderboard = new JList();
		displayLeaderboard = new JList<>(leaderboardData.toArray(new String[0]));
		displayLeaderboard.setBackground(Color.BLACK);
		displayLeaderboard.setForeground(Color.WHITE);

				
		JScrollPane scrollPane = new JScrollPane(displayLeaderboard);
		leaderboardPanel.add(displayLeaderboard);
		leaderboardPanel.add(scrollPane);
		leaderboardPanel.setBackground(Color.BLACK);
		leaderboardPanel.setForeground(Color.WHITE);
		leaderboardPanel.updateUI();				
		
		playAgainFrame.getContentPane().add(leaderboardPanel, BorderLayout.PAGE_END);
		
	}
}