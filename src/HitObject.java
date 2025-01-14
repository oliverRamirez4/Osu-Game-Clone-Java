
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

/**
 * The clickable circle in the Osu game
 */
public class HitObject{
	public static final float PIXEL_SIZE = 1.8f;
	public static final double APPROACHING_CIRCLE_RADIUS_SCALE = 4;
	public static final int DISAPPEAR_DELAY = 250;
	
	private static final BasicStroke FRAME_STROKE = new BasicStroke(PIXEL_SIZE * 4);
	private static final BasicStroke APPROACHING_STROKE = new BasicStroke(PIXEL_SIZE * 4  );
	
	
	private double x;
	private double y;
	private double circleRadius;
	private String circleNumber;
	
	private int timeToClick;
	private int timeToAppear;
	private int lastTimeToClick;
	private int timeToDisappear;
	private int preempt;
	private int fadeInTime;
	private int timeToFullyAppear;
	
	private boolean hit;
	
	
	
	
	private Shape circle;
	private double approachingCircleRadiusScaleChangeUnit;
	private Shape scoreLabel;
	
	private Color circleColor;
	private Color frameColor = Color.WHITE;
	private Color scoreColor = Color.RED;
	
	private Font numberFont;
	
	/**
	 * 
	 * @param x - x coordinate of hit object in osu grid
	 * @param y - y coordinate of hit object in osu grid
	 * @param timeToClick - time to click this hit object (ms)
	 * @param circleSize - circle size of the beat map
	 * @param circleNumber - number to display on the circle
	 */
	public HitObject(int x, int y, int timeToClick, double circleSize, int circleNumber, Color circleColor, float approachingRate, float difficultyLevel) {
		this.x = (64 + x) * PIXEL_SIZE;
		this.y = (48 + y) * PIXEL_SIZE;
		this.circleRadius = (54.4 - (circleSize * 4.48)) * PIXEL_SIZE;
		this.timeToClick = timeToClick;
		this.preempt = this.getPreempt(approachingRate);
		this.timeToAppear = timeToClick - preempt;
		this.lastTimeToClick = timeToClick + Math.round(200 - 10 * difficultyLevel);
		this.timeToDisappear = lastTimeToClick + DISAPPEAR_DELAY;
		
		this.circleNumber = circleNumber + "";
	
		this.circle = new Ellipse2D.Double(
				this.x - circleRadius, 
				this.y - circleRadius, 
				circleRadius * 2, 
				circleRadius * 2);
		this.approachingCircleRadiusScaleChangeUnit = (1.0 - APPROACHING_CIRCLE_RADIUS_SCALE) / preempt;
		
		this.scoreLabel = new Ellipse2D.Double(
				this.x - PIXEL_SIZE * 6, 
				this.y - PIXEL_SIZE * 6, 
				PIXEL_SIZE * 12, 
				PIXEL_SIZE * 12);
		
		this.fadeInTime = Math.round(preempt * 2 / 3.0f);
		this.timeToFullyAppear = timeToAppear + fadeInTime;
		
		this.numberFont = new Font(null, Font.BOLD, (int)(30 * PIXEL_SIZE));
		this.circleColor = circleColor;
		
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getCircleRadius() {
		return circleRadius;
	}

	/**
	 * Draw this hit object
	 * @param g2d
	 */
	public void draw(Graphics2D g2d, long currentTime) {
		
		if (this.hit || currentTime > lastTimeToClick) {
			g2d.setColor(scoreColor);
			g2d.fill(scoreLabel);
			return;
		}
		
		if (currentTime <= timeToFullyAppear) {
			float alpha = (currentTime - timeToAppear) / (float) fadeInTime;
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		}
		else {
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		}
		
		g2d.setColor(circleColor);
		
		if (currentTime <= timeToClick) {
			double scale = (currentTime - timeToAppear) * approachingCircleRadiusScaleChangeUnit + APPROACHING_CIRCLE_RADIUS_SCALE ;
			g2d.translate(x * (1 - scale), y * (1 - scale));
			g2d.scale(scale, scale);
			
			g2d.setStroke(APPROACHING_STROKE);
			g2d.draw(circle);
			g2d.scale( 1 / scale, 1 / scale);
			g2d.translate(x * (scale - 1), y * (scale - 1));
		}
		
		g2d.fill(circle);
		
		g2d.setColor(frameColor);
		g2d.setStroke(FRAME_STROKE);
		g2d.draw(circle);
		
		g2d.setFont(numberFont);
		
		float xOff = g2d.getFontMetrics().stringWidth(circleNumber) / 2f;
		int ascent = g2d.getFontMetrics().getAscent();
		int descent = g2d.getFontMetrics().getDescent();
		float yOff = (ascent - descent) / 2f;
		g2d.drawString(circleNumber, (float)x - xOff, (float)y + yOff);
	}
	
	public void hit(int hitTime, int hitRawScore) {
		System.out.println(hitRawScore);
		this.hit = true;
		this.timeToDisappear = hitTime + DISAPPEAR_DELAY;
		switch (hitRawScore) {
		case 300:
			this.scoreColor = Color.BLUE;
			break;
		case 100:
			this.scoreColor = Color.GREEN;
			break;
		case 50:
			this.scoreColor = Color.YELLOW;
		default:
			break;
		}
	}
	
	public int getPreempt(float approachingRate) {
		if (approachingRate < 5) {
			return Math.round(1200 + 600 * (5 - approachingRate) / 5);
		}
		else if (approachingRate == 5) {
			return 1200;
		}
		else {
			return Math.round(1200 - 750 * (approachingRate - 5) / 5);
		}
	}
	
	public int getTimeToAppear() {
		return this.timeToAppear;
	}
	
	public int getTimeToClick() {
		return this.timeToClick;
	}
	
	public int getLastTimeToClick() {
		return this.lastTimeToClick;
	}
	
	public int getTimeToDisappear() {
		return timeToDisappear;
	}
	
	public boolean contains(Point p) {
		if (p == null) {
			return false;
		}
		return (Math.pow((p.getX() - x), 2) + Math.pow((p.getY() - y), 2)) <= Math.pow(circleRadius, 2);
//		return circle.contains(p);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return circleNumber + "";
	}

}
