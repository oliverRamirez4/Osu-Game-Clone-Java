import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test cases for clicking on the circle
 */
class TestClickOnObject {	
	private int x;
	private int y;
	private int timeToClick;
	private double circleSize;
	private int circleNumber; 
	private float approachingRate;
	private float difficultyLevel;
	private double circleRadius;

	private HitObject hitObject;
	
	@BeforeEach
	void setUp() throws Exception {
		this.x = 100;
		this.y = 100;
		this.timeToClick = 10; //not important 
		this.circleNumber = 3; //not important
		this.circleSize = 5; 
		this.approachingRate = 10; //not important
		this.difficultyLevel = 1; //not important
		
		this.hitObject = new HitObject(x, y, timeToClick, circleSize, circleNumber, Color.PINK, approachingRate, difficultyLevel);
		
	}
	
	@Test
	void testCenterOfCircle() {
		Point testPoint =new Point ((int)hitObject.getX(), (int)hitObject.getY());
		assertTrue(hitObject.contains(testPoint));
	}
	
	@Test
	void testLeftEdgeofCircle() {
		int xCoordinate = (int)hitObject.getX() - (int)hitObject.getCircleRadius();
		int yCoordinate = (int)hitObject.getY();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertTrue(hitObject.contains(testPoint));
	}
	@Test
	void testLeftOfCircleJustOutside() {
		int xCoordinate = (int)hitObject.getX() - 1  - (int)hitObject.getCircleRadius();
		int yCoordinate = (int)hitObject.getY();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertFalse(hitObject.contains(testPoint));
	}
	
	@Test
	void testRightEdgeOfCircle() {
		int xCoordinate = (int)hitObject.getX() + (int)hitObject.getCircleRadius();
		int yCoordinate = (int)hitObject.getY();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertTrue(hitObject.contains(testPoint));
	}
	@Test
	void testRightOfCircleJustOutside() {
		int xCoordinate = (int)hitObject.getX() + 1  + (int)hitObject.getCircleRadius();
		int yCoordinate = (int)hitObject.getY();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertFalse(hitObject.contains(testPoint));
	}
	
	@Test
	void testTopEdgeofCircle() {
		int xCoordinate = (int)hitObject.getX();
		int yCoordinate = (int)hitObject.getY() + (int)hitObject.getCircleRadius();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertTrue(hitObject.contains(testPoint));
	}
	@Test
	void testTopofCircleJustOutside() {
		int xCoordinate = (int)hitObject.getX();
		int yCoordinate = (int)hitObject.getY() + 1 + (int)hitObject.getCircleRadius();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertFalse(hitObject.contains(testPoint));
	}
	
	@Test
	void testBottomOfCircle() {
		int xCoordinate = (int)hitObject.getX();
		int yCoordinate = (int)hitObject.getY() - (int)hitObject.getCircleRadius();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertTrue(hitObject.contains(testPoint));
	}
	@Test
	void testBottomOfCircleJustOutside() {
		int xCoordinate = (int)hitObject.getX();
		int yCoordinate = (int)hitObject.getY() - 1 - (int)hitObject.getCircleRadius();
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertFalse(hitObject.contains(testPoint));
	}
	
	@Test
	void testCornerOfSquareWithEdge2Radius() {
		int xCoordinate = (int)hitObject.getX() + (int)hitObject.getCircleRadius() - 1;
		int yCoordinate = (int)hitObject.getY() + (int)hitObject.getCircleRadius() - 1;
		Point testPoint =new Point (xCoordinate, yCoordinate);
		assertFalse(hitObject.contains(testPoint));
	}
}
