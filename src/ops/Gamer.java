package ops;

import java.awt.Graphics;

/**
 * 
 * @author user
 *
 */
public class Gamer extends PlayerTracker{

//---------- constrcutor ------------------------------------------------------------------------------------------------------------------------
	public Gamer(double x, double y, int angle) {
		super(x, y, angle);//constructor from PlayerTracker.java
	}
	
//---------- getAllXCoordinates -----------------------------------------------------------------------------------------------------------------
	@Override
	public int[][] getAllXCoordinates() {
		int [][] allXCoordinates = 
			{ 
			  {-50,-30,30,50,50,-50},//x-coordinates for the head
			  //{-24,-9,-24,-32},//x-coordinates for left eye
			  //{24,9,24,32},//x-coordinates for right eye
			  {-38,0,38,38,33,33,-33,-33,-38},//x-coordinates for the skin section
			  {-28,28,28,-28}
			};
		return allXCoordinates;
	}

//---------- getAllYCoordinates -----------------------------------------------------------------------------------------------------------------
	@Override
	public int[][] getAllYCoordinates() {
		int[][] allYCoordinates = 
			{ 
			 {-50,-20,-20,-50,50,50},//y-coordinates for the head
			 //{-6,0,6,0},//y-coordinates for left eye
			 //{-6,0,6,0},//y-coordinates for right eye
			 {15,28,15,30,28,42,42,28,30},//x-coordinates for the skin section
			 {35,35,36,36}
		};
		return allYCoordinates;
	}
//---------- draw method ------------------------------------------------------------------------------------------------------------------------
	public void draw(Graphics g){
		super.draw(g);
	}
}

