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
import java.io.FileFilter;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.metal.MetalButtonUI;

public class MainMenu {
	
	
	BufferedImage mainMenuImage;
	private String songToPlay;
	private String difficultyToPlay;
	private String[] songVersionsToDisplay;
	private String[] songFileNames;
	
	public MainMenu() throws ClassNotFoundException, SQLException{
		
		FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".osu");
			}
		};
		
		//initializes the songFileNames
		File songs = new File("Songs");
		songFileNames = new String[songs.listFiles().length];
		for(int i = 0; i < songs.listFiles().length; i++) {
			songFileNames[i] = songs.listFiles()[i].getName();
		}
		
		
		/**
		 * Create frame for main menu
		 */
		JFrame mainMenuFrame = new JFrame();
		mainMenuFrame.setTitle("Main Menu");
		mainMenuFrame.setSize(1275, 730);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setVisible(true);
		
		/**
		 * read the image for the background
		 */
		try {
			mainMenuImage = ImageIO.read(new File("HomeMenuBackground.jpg"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		/**
		 * Paint the image for the background
		 */
	    JPanel mainMenuPanel = new JPanel(){
	        @Override
	        protected void  paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(mainMenuImage,0,0,null);
	        }
	    };	    
	    mainMenuPanel.setLayout(new GridLayout(16, 2, 5, 5));
	    mainMenuFrame.add(mainMenuPanel);
	    
        
        //Add the title
		JLabel homeMenuLabel = new JLabel("<html><font size='10' color=White> Main Menu</font></html>", SwingConstants.CENTER);
		homeMenuLabel.setFont(homeMenuLabel.getFont().deriveFont(Font.BOLD, 15f));
		homeMenuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainMenuPanel.add(homeMenuLabel);
		
		//add start game
		JButton pressStartButton = new JButton("<html><font size='6' color=White> Start Game</font></html>");
		pressStartButton.setUI(new MetalButtonUI());
		pressStartButton.setFocusPainted(false);
		pressStartButton.setBackground(Color.BLACK);
		pressStartButton.setForeground(Color.WHITE);
		mainMenuPanel.add(pressStartButton);
		
	
		pressStartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				mainMenuFrame.dispose();
				new RunGame(songToPlay, difficultyToPlay);
			}
		});
        
		//Difficulty Jlist
		JLabel difficultyLabel = new JLabel();
		JList difficultyList = new JList(songVersionsToDisplay);
		JScrollPane difficultyDropDown = new JScrollPane(difficultyList);
		difficultyList.setBackground(Color.BLACK);
		difficultyList.setForeground(Color.WHITE);
		difficultyList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					difficultyToPlay = (String) difficultyList.getSelectedValue();					
				}	
			}
		});
				
		
		
		//select song
		//add choose song drop down
		//Container songPane = mainMenuFrame.getContentPane();
		//songPane.setLayout(new FlowLayout());
						
		JLabel songsLabel = new JLabel();
						
		JList songList = new JList(songFileNames);
		songList.setVisibleRowCount(songFileNames.length);
		JScrollPane songsDropDown = new JScrollPane(songList);
		songList.setBackground(Color.BLACK);
		songList.setForeground(Color.WHITE);
						
		mainMenuPanel.add(songsDropDown);
		mainMenuPanel.add(songsLabel);
		
		songList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					songToPlay = (String) songList.getSelectedValue();
					System.out.println(songToPlay);
					File song = new File("Songs/"+ songToPlay);
					songVersionsToDisplay = new String[song.listFiles(filter).length];
					for(int i = 0; i < songVersionsToDisplay.length;i++) {
						songVersionsToDisplay[i] = song.listFiles(filter)[i].getName();
					}
					
					mainMenuPanel.add(difficultyDropDown);
					//songVersionsToDisplay = getDifficultyVersions(songToPlay);

					mainMenuPanel.add(difficultyLabel);
					mainMenuPanel.add(difficultyDropDown);
					difficultyList.setVisibleRowCount(songVersionsToDisplay.length);
					difficultyList.setListData(songVersionsToDisplay);
					mainMenuPanel.updateUI();
					
				}
				
			}
			
		});
		
	
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
		
		mainMenuFrame.getContentPane().add(leaderboardPanel, BorderLayout.PAGE_END);		

		
	}
	
//	public String[] getDifficultyVersions(String songName) {
//		System.out.println(songName);
//		/*if(songName == null) {
//			return null;
//		}*/
//		switch(songName){
//		case("470977 Mili - worldexecute(me);"):
//			return ExecuteMeDifficulties ;
//		case("476691 DJ OKAWARI - Flower Dance"):
//			return FlowerDanceDifficulties ;
//		case("546384 NOMA - Brain Power"):
//			return BrainPowerDifficulties;
//		case("891345 HyuN - Infinity Heaven"):
//			return InfinityHeavenDifficulties;
//		}
//		return null;
//
//	} 
	
}
