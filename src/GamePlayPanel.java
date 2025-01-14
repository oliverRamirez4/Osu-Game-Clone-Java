import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for playing the Game
 * 
 */
public class GamePlayPanel extends JPanel{
	
	public static final int REFRESH_RATE = 8; //miliseconds

	//Scoring variables
	private int score;
	private int combo;
	private double accuracy;
	private int objectAlreadyHit;
	private double rawScoreSum;
	private int maxCombo;
	private HashMap<Integer, Integer> scoreCounts;
	
	//Beat Map that holds the level and HitObjects
	private String songName;
	private BeatMapFactory beatMap;
	private HitObject nextHitObject;
	private MusicPlayer musicPlayer;
	
	private long currentTime;
	private int prepareTime;
	
	//both scoring strategies
	private ScoreingStrategy rawScoringStrategy;
	private ScoreingStrategy scoringStrategy;
	
	private Deque<HitObject> hitObjectToShow;
	private Deque<HitObject> scoreLabels;
	private Deque<Point> mouseTrack;
	
	private JFrame gameFrame;
	
	private JLabel scoreLabel;
	private JLabel comboLabel;
	private JLabel accuracyLabel;
	
	private Color numberColor = Color.WHITE;
	private Font numberFont = new Font(null, Font.BOLD, 30);
	
	private Timer musicStartTimer;
	private Thread refreshThread;
	private boolean isPause;


	public GamePlayPanel(String songName, String difficultyName) {		
		super(true);
		this.setLayout(null);
		this.setBounds(0, 0, 1152, 864);
		this.setBackground(Color.BLACK);
		this.songName = songName;
		this.difficultyName = difficultyName;
		String songPathName = getSongPathname(songName, difficultyName);

		System.out.println(songPathName);
		System.out.println(songName);
		
		this.beatMap = new BeatMapFactory();
		

		beatMap.readBeatMap(songPathName);

		this.musicPlayer = new MusicPlayer(new File("Songs/"+songName+"/audio.mp3"));
		
		this.scoreCounts = new HashMap<Integer, Integer>();
		scoreCounts.put(300, 0);
		scoreCounts.put(100, 0);
		scoreCounts.put(50, 0);
		scoreCounts.put(0, 0);



		rawScoringStrategy = new RawScoringStrategy(beatMap.getDifficultyLevel());
		scoringStrategy = new IntervalScoringStrategy(beatMap.getDifficultyLevel());
		
		hitObjectToShow = new ArrayDeque<HitObject>();
		nextHitObject = beatMap.getNextHitObject();
		prepareTime = 3000 - nextHitObject.getTimeToClick();
		
		scoreLabels = new ArrayDeque<HitObject>();
		
		mouseTrack = new ArrayDeque<Point>();
		

		//Set up the score label
		scoreLabel = new JLabel();
		scoreLabel.setBounds(852, 0, 300, 30);
		scoreLabel.setFont(numberFont);
		scoreLabel.setForeground(numberColor);
		scoreLabel.setHorizontalAlignment(JLabel.RIGHT);
		this.add(scoreLabel);
		scoreLabel.setText("0");
		
		//Set up the combo label
		comboLabel = new JLabel();
		comboLabel.setBounds(0, 834, 100, 30);
		comboLabel.setFont(numberFont);
		comboLabel.setForeground(numberColor);
		comboLabel.setHorizontalTextPosition(JLabel.LEFT);
		this.add(comboLabel);
		comboLabel.setText("0");
		
		//Set up the Accuracy Label
		accuracyLabel = new JLabel();
		accuracyLabel.setBounds(1000, 30, 150, 30);
		accuracyLabel.setFont(numberFont);
		accuracyLabel.setForeground(numberColor);
		this.add(accuracyLabel);
		accuracyLabel.setText("100.00%");
		
		//When the mouse is pressed run the hit method
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
	            	hit();
            }
        	});
		
		//Key Listener so that x and c act as clicks
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_X || e.getKeyCode() == KeyEvent.VK_C) {
					hit();
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					pauseGame();
				}
			}
		});
		
		gameFrame = new JFrame();
	    gameFrame.getContentPane().setLayout(null);
		gameFrame.add(this);
		gameFrame.addKeyListener(getKeyListeners()[0]);
		gameFrame.setSize(1200, 900);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		
		System.out.println(musicPlayer.getDuration());
		startPlay();
    }
	
	
		
	public int getScore() {
		return score;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public int getMaxCombo() {
		return maxCombo;
	}

	public HashMap<Integer, Integer> getScoreCounts() {
		return scoreCounts;
	}

	/**
	 * Hit method is activated every time there is a click on the screen
	 */
	public void hit() {
		Point mousePoint = MouseInfo.getPointerInfo().getLocation();
		System.out.println(mousePoint);
		//check if mouse is clicked within shape
		if (!hitObjectToShow.isEmpty() && hitObjectToShow.peek().contains(mousePoint)){
			System.out.println("Hit");
			int hitTime = this.hitObjectToShow.peek().getTimeToClick();
			int timeDifference = (int)Math.abs(hitTime - getCurrentTime());
			int scoreValue = rawScoringStrategy.calculateScore(timeDifference, combo);
			hitObjectToShow.peek().hit(hitTime, scoreValue);
			
			objectAlreadyHit++;
			rawScoreSum += scoreValue;
			updateAccuracy();
			score += scoringStrategy.calculateScore(timeDifference , combo);
			if (scoreValue > 0) {
				combo++;
			}
			else {
				resetCombo();
			}
			scoreCounts.put(scoreValue, scoreCounts.get(scoreValue) + 1);
			scoreLabels.add(hitObjectToShow.poll());
        }
	}
	
	public MusicPlayer getMusicPlayer() {
		return this.musicPlayer;
	}
	
	/**
	 * If a click is a miss
	 */
	public void miss() {
		resetCombo();
		objectAlreadyHit++;
		rawScoreSum += 0;
		updateAccuracy();
		scoreLabels.add(hitObjectToShow.poll());
	}
	
	public void resetCombo() {
		if (combo > maxCombo) {
			maxCombo = combo;
		}
		combo = 0;
	}
	
	public void updateAccuracy() {
		accuracy = rawScoreSum / (objectAlreadyHit * 300.0) * 100;
	}
	
	//Every 10 miliseconds
	public void refresh() {
		this.currentTime = getCurrentTime();
		if (nextHitObject != null && currentTime >= this.nextHitObject.getTimeToAppear()) {
			hitObjectToShow.add(nextHitObject);
			nextHitObject = beatMap.getNextHitObject();
		}
		try {
			if (!hitObjectToShow.isEmpty() && currentTime >= hitObjectToShow.peek().getLastTimeToClick()) {
				miss();
			}	
		} catch (Exception e) {}
		if (!scoreLabels.isEmpty() && currentTime >= scoreLabels.peek().getTimeToDisappear()) {
			scoreLabels.poll();
		}
		
//		mouseTrack.add(getMousePosition());
//		if (mouseTrack.size() > 20) {
//			mouseTrack.poll();
//		}
		
		scoreLabel.setText(score + "");
		comboLabel.setText(combo + "");
		accuracyLabel.setText(String.format("%.2f", accuracy) + "%");
		updateUI();
	}
	
	public long getCurrentTime() {
		return musicPlayer.getPlayedMillis();
	}
	
	public void pauseGame() {
		musicPlayer.pauseMusic();
		isPause = true;
	}
	
	public void resumeGame() {
		musicPlayer.resumeMusic();
		isPause = false;
	}
	
	public void startPlay() {
		//Two events dependent on Time, refresh and playing Music
		musicStartTimer = new Timer();
		//Schedule the Music Playing
		musicStartTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				musicPlayer.resumeMusic();
			}
		}, prepareTime > 0 ? prepareTime : 0);
		
		refreshThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (!musicPlayer.isComplete()) {
					if (isPause) {
						continue;
					}
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							refresh();
						}
					});
					try {
						Thread.sleep(REFRESH_RATE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				endPlay();
			}
		});
		refreshThread.start();		
	}
	
	public void endPlay() {
		System.out.println("Game Over");
		System.out.println(getScore());
		new PlayAgain();
		gameFrame.dispose();		
	}
	
	/**
	 * Need to move into the BeatMap Factory
	 * returns pathname of song, including directory, song name, and song version file
	 * @param songName
	 * @return – pathname of song with correct version
	 */
	public String getSongPathname(String songName, String songDifficulty) {
		String pathname = " ";
		switch(songName) {
		case("470977 Mili - worldexecute(me);"):
			pathname = "Songs/470977 Mili - worldexecute(me);/Mili - world.execute(me); (Exile-) [mapset.extra(Exile-);].osu";
			break;
		case("476691 DJ OKAWARI - Flower Dance"):
			pathname = "Songs/476691 DJ OKAWARI - Flower Dance/DJ OKAWARI - Flower Dance (Narcissu) [Little's Insane].osu";
			break;
		case("891345 HyuN - Infinity Heaven"):
			pathname = "Songs/891345 HyuN - Infinity Heaven/HyuN - Infinity Heaven (Niva) [Mirash's Insane].osu";
			break;
		case("546384 NOMA - Brain Power"):
			pathname = "Songs/546384 NOMA - Brain Power/NOMA - Brain Power (Monstrata) [Doormat's Insane].osu";
			break;	
		}
		return pathname;
	}
	

	
	
	@Override
    public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics);

        Graphics2D g2d = (Graphics2D) graphics;
        
        
        //Paint all of the HitObjects that need to be shown
        Iterator<HitObject> hitObjectIterator = hitObjectToShow.descendingIterator();
        while (hitObjectIterator.hasNext()) {
        		try {
        			hitObjectIterator.next().draw(g2d, currentTime);
			} catch (Exception e) {
			}
        }
        
        Iterator<HitObject> scoreLabelIterator = scoreLabels.descendingIterator();
        while (scoreLabelIterator.hasNext()) {
	        	try {
	        		scoreLabelIterator.next().draw(g2d, currentTime);
	        	} catch (Exception e) {
	        	}
        }
        

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
//        g2d.setColor(Color.PINK);
//        Iterator<Point> mousePoints = mouseTrack.descendingIterator();
//        while (mousePoints.hasNext()) {
//	        	Point p = mousePoints.next();
//	        	if (p != null) {
//	        		g2d.fillOval((int)p.getX(), (int)p.getY(), 16, 16);	
//	        	}
//        }
    }


	
		

	
}
