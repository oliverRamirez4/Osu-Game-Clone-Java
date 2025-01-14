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

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.metal.MetalButtonUI;

public class PauseMenu {
	BufferedImage pauseMenuImage;

	
	public PauseMenu(GamePlayPanel gameplay){
		
		/**
		 * Create frame for main menu
		 */
		JFrame PauseMenu = new JFrame();
		PauseMenu.setTitle("Pause Game");
		PauseMenu.setSize(1275, 730);
		PauseMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		PauseMenu.setVisible(true);
		
		/**
		 * read the image for the background
		 */
		try {
			pauseMenuImage = ImageIO.read(new File("PauseMenu.jpg"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		/**
		 * Paint the image for the background
		 */
	    JPanel pauseMenuPanel = new JPanel(){
	        @Override
	        protected void  paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(pauseMenuImage,0,0,null);
	        }
	    };	    
	    pauseMenuPanel.setLayout(new GridLayout(16, 2, 5, 5));
	    PauseMenu.add(pauseMenuPanel);
	    
        
        //Add the title
		JLabel pauseGameLabel = new JLabel("<html><font size='10' color=White> Pause Game</font></html>", SwingConstants.CENTER);
		pauseGameLabel.setFont(pauseGameLabel.getFont().deriveFont(Font.BOLD, 15f));
		pauseGameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		pauseMenuPanel.add(pauseGameLabel);
		
		//add start game
		JButton resumeGameButton = new JButton("<html><font size='6' color=White> Resume Game</font></html>");
		resumeGameButton.setUI(new MetalButtonUI());
		resumeGameButton.setFocusPainted(false);
		resumeGameButton.setBackground(Color.BLACK);
		resumeGameButton.setForeground(Color.WHITE);
		pauseMenuPanel.add(resumeGameButton);
		resumeGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				PauseMenu.dispose();
				gameplay.getMusicPlayer().resumeMusic();
			}
		});	
		
		//add quit game
				JButton quitGame = new JButton("<html><font size='6' color=White>Quit Game</font></html>");
				quitGame.setUI(new MetalButtonUI());
				quitGame.setFocusPainted(false);
				quitGame.setBackground(Color.BLACK);
				quitGame.setForeground(Color.WHITE);
				pauseMenuPanel.add(quitGame);
				quitGame.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						pauseMenuPanel.updateUI();
						try {
							Thread.sleep(4000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						try {
							new MainMenu();
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
	}
}
