package ops;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author user
 *
 */

public class VicinityChecker {
	double x;//x-coordinte starting corner
	double y;//y-coordinte starting corner
	int w;//width for the rectangle
	int h;//height for the rectangle
	static int boxAngle;//angle used for the rotation of the rectangle
	SoundRendition collidedSound;//variable for the collision sound
	
//---------- constructor -------------------------------------------------------------------------------------------------------------------------------------------------
	public VicinityChecker(int x,int y, int w, int h){//constructor must protect the code, what if it was negative??
		this.x = x;
		this.y = y;
	
		this.w = w;
		this.h = h;
		collidedSound = new SoundRendition(Config.crashSound);//loading the sound
	}
	
/*************************************************************************************************************************************************************************
 * This method checks if another rectangle has collides with this rectangle
 * @param collisionBox
 * @return true;
 */
	public boolean hasCollidedWith(VicinityChecker otherBox){
		//if the right upper corner of character's collision box is colliding, then return true
		if(otherBox.x <= this.x && otherBox.x + otherBox.w >= x && otherBox.y >= y){	
			//collidedSound.playAudio();//play the sound
			otherBox.x = this.x - 100;//move the collision box backwards so their edges are touching each other
			return true;
		}
		
		/*if the charcter's collision box's y coordinate are lower than the obstacle's y, 
		and character's collision box's x coordinate is less than the upper corner of this obstacle
		character is on top of the obstacle*/
		else if(otherBox.x + otherBox.w >= this.x + this.w && otherBox.y < this.y){
		//	System.out.println("on top");
			return false;
		}
		
		/* if the coordinates of character's collision box has passed 
		 * the right upper corner of this obstacle
		 * then the character has moved passed this obstacle*/
		else if(otherBox.x > this.x + this.w && otherBox.y == this.y){
		//	System.out.println("past the obstacle");
			return false;
		}
		
		//character hasn't arrived to this obstacle
		else{
			return false;
		}
	}
	
/*************************************************************************************************************************************************************************
 *This function is used to move the character forward according to the angle and distance desired
 * @param d
 */
	public void moveForwardCollisionBox(int d){
		x += d;
		y += d;
	}
	
/*************************************************************************************************************************************************************************
 * Function used for other classes to adjust the box y-coordinate
 * @param y
 */
	public void setCollisionBoxHeight(double y){
		this.y = y;//method required to be used in the Floor class
	}
	
/*************************************************************************************************************************************************************************
 * RestartCollisionBox
 */
	public void restartCollisionBox(double x, double y){
		this.x = x;
		this.y = y;
	}
/*************************************************************************************************************************************************************************
 * Function used to draw the collision box around the objects
 * @param g
 */
	public void draw(Graphics g){
		g.setColor(Color.yellow);
		g.drawRect((int)(x-Camera.x),(int)y,w,h);
	}
		
}
