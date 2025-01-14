import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalButtonUI;

public class LoginPage {	
	BufferedImage loginPageImage;

	public LoginPage(){
		/**
		 * Sets up the frame
		 */
		JFrame loginFrame = new JFrame();
		loginFrame.setTitle("OSU Log In");
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setSize(700, 500);
		loginFrame.setVisible(true);
	
		/**
		 * loads image into background
		 */
		try {
			loginPageImage = ImageIO.read(new File("LoginBackground.jpeg"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
		/**
		 * Grid layout for the JPanel
		 */
	    JPanel loginPanel = new JPanel(new GridLayout(3,2,1,1)){
	        @Override
	        protected void  paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(loginPageImage ,0,0,null);
	        }
	    };
	    
	    loginFrame.add(loginPanel);
		
	
		/**
		 * Create button panel to put on bottom
		 */
		JPanel buttons_panel = new JPanel(new FlowLayout());
		JLabel username_label = new JLabel("<html><font size='6' color=white> Welcome to OSU! Please enter your username: </font></html>", SwingConstants.CENTER);
		username_label.setFont(username_label.getFont().deriveFont(Font.BOLD, 15f));
			
		/**
		 * Allows user to enter a username
		 */
		JTextField typeUsername = new JTextField(10);
		typeUsername.setLocation(5, 5);
		typeUsername.setSize(100,20);	
		Font usernameTextFieldFont = new Font("SansSerif", Font.BOLD, 20);
		typeUsername.setFont(usernameTextFieldFont);
		typeUsername.setHorizontalAlignment(JTextField.CENTER);
		typeUsername.setBackground(Color.BLACK);
		typeUsername.setForeground(Color.WHITE);		
		
		/**
		 * Login button takes user from the login page to main menu page
		 */
	    JButton loginButton = new JButton("Log In");
	    loginButton.setUI(new MetalButtonUI());
	    loginButton.setFocusPainted(false);
	    loginButton.setBackground(Color.BLACK);
	    loginButton.setForeground(Color.WHITE);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String username = typeUsername.getText();
				System.out.println(username);
				loginFrame.dispose();
				try {
					new MainMenu();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		loginPanel.add(username_label);
		loginPanel.add(typeUsername);
		   
		buttons_panel.add(loginButton);
		buttons_panel.setBackground(Color.BLACK);

		loginFrame.getContentPane().add(loginPanel, BorderLayout.CENTER);
		loginFrame.getContentPane().add(buttons_panel, BorderLayout.PAGE_END);		
	}
		
}


