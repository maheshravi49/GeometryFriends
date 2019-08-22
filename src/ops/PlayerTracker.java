package ops;

import java.awt.*;

/**
 * 
 * @author user
 *
 */
public abstract class PlayerTracker extends VicinityChecker{
	
	double x;// x-coordinate where the character will be placed
	static double y;// y-coordinate where the character will be placed
		
	
	static int angle;//angle where the character will be facing 
	
	static int velocity = 15;//velocity to which the character will move initially
	static double fallingSpeed = 0;//the game hasn't started
	double gravity = 2;//gravity will help with the jump fucntion
	
	static boolean onGround = false;//the character initally starts as a falling object
	static boolean canRotate = false;//as long as it isn't on the ground, it can't rotate
	
	boolean collided = false;//boolean need it to stop everything when collision is detected
	
	static SoundRendition jumpSound;//variable used to store the jump sound effect
	
	int[][] allXCoordinates = getAllXCoordinates();// contains all Xs of each polygon
	int[][] allYCoordinates = getAllYCoordinates();// contains all Ys of each polygon
	
/*******************************************************************************************************************************************************
 * Constructor will initialize a gPlayer character
 * with the (int x,int y, int degrees) positions and angle where it'll be facing
 */
	@SuppressWarnings("static-access")
	public PlayerTracker(double x, double y, int angle){
		super((int)(x-50),(int)(y-50), 100, 100);
		this.x = x;
		this.y = y;
		this.angle = angle;
		jumpSound = new SoundRendition("music/jumpVoice.wav");//load the sound file
	}

/*******************************************************************************************************************************************************
 *getAllXCoordinates is a 2D array used to store all of the x-coordinates used to design the character
 */
	public abstract int[][] getAllXCoordinates();
	
/*******************************************************************************************************************************************************
 *getAllXCoordinates is a 2D array used to store all of the y-coordinates used to design the character
 */
	public abstract int[][] getAllYCoordinates();

/*******************************************************************************************************************************************************
 * this function will rotate the coordinates of the character
 * 90 degrees clockwise
 */
	public static void rotate(){
		if(canRotate){
		angle += 90;//increase the angle 90 degrees
		if(angle > 359){
			angle -= 360;}//we decrease the degress to be inside a range from 0-359 degress
		}
	}

/*******************************************************************************************************************************************************
 * moveForward will move the character forward 
 * every time this function is called by a fixed amount of ints
 */
	public void moveForward(){
		x += velocity;//adding velocity(values) to the x-coordinate
		this.moveForwardCollisionBox(velocity);
	}

/*******************************************************************************************************************************************************
 * startToJump will initialize the speed for the jump/fall
 */
	public static void startToJump(){
		if(onGround){//if character is on ground,the falling speed changes to negative value
			jumpSound.playAudio();//play the background music
			fallingSpeed = -35;//it is negative so y-coordinate goes down("up")
			canRotate = false;//while jumping, you can't rotate anymore
			onGround = false;//you are not longer on the ground
		}
	}
	
/*******************************************************************************************************************************************************
 * jump will add negative and positive numbers to the y coordinate to make it look
 * like the character is jumping
 */
	@SuppressWarnings("static-access")
	public void fall(){
		fallingSpeed += gravity;//we add gravity(positive) to fallingSpeed(negative)
		y += fallingSpeed;//change y value according to fallingSpeed
		this.setCollisionBoxHeight(this.y-50);
		//falling speed starts negative but it later becomes positive, so y goes up and down
		
		/*if(y > 900){//if the y position is higher than 900
			y = 900;//set it back to initial position
			fallingSpeed = 0;//no more "force" on the y coordinate
			onGround = true;//back on the ground again
			canRotate = true;//once on the ground, it can rotate at the moment of jumping
		}*/
	}
	
/*******************************************************************************************************************************************************
 * Restart character's position and also actualizes its collision box
 */
	@SuppressWarnings("static-access")
	public void restart(double x, double y, int angle){
		super.restartCollisionBox(x, y);
		this.x = x;
		this.y = y;
		this.angle = angle;
		velocity = 15;
	}
/*******************************************************************************************************************************************************
 * Draw method will handle the position of the x and y coordinates 
 * of each single polygon that composes the face of the character
 * during rotation and moving forward functions.
 * It also handles the color for each shape of the face
 */
	public void draw(Graphics g){

		int[] xPoints;//array to place the x-coordinates of each polygon composing the head individually
		int[] yPoints;//array to place the y-coordinates of each polygon composing the head individually
		
		double cosAngle = Lookup.cos[angle];//will help to set the correct x-value for the polygon
		double sinAngle = Lookup.sin[angle];//will help to set the correct y-value for the polygon
		
		Color color = null;//color for each polygon will be different
		//super.draw(g);//this a call to draw the VicinityChecker
		for(int polygon = 0; polygon < allXCoordinates.length; polygon++){//go through all elements inside the array
			xPoints = new int[allXCoordinates[polygon].length];//the number of x-coordinates depends on the number of elements of each polygon
			yPoints = new int[allYCoordinates[polygon].length];//the number of y-coordinates depends on the number of elements of each polygon
			for(int vertex = 0; vertex < allXCoordinates[polygon].length; vertex++){//go through each elements of this current element in the main array
				xPoints[vertex] = (int)(allXCoordinates[polygon][vertex]*cosAngle - allYCoordinates[polygon][vertex]*sinAngle + x-Camera.x);
				yPoints[vertex] = (int)(allXCoordinates[polygon][vertex] * sinAngle + allYCoordinates[polygon][vertex]*cosAngle + y-Camera.y);
			}
			
			//depending on which polygon we are, we choose its color
			if(polygon == 0){ color = new Color(36,36,36);}//for the head
			if(polygon == 1 || polygon == 2){ color = new Color(153,255,255);}//left eye || right eye
			if(polygon == 3){ color = new Color(255,229,204);}//visible face section
			if(polygon == 4){ color = Color.black;}//mouth
			
			g.setColor(color);
			g.fillPolygon(xPoints, yPoints, allXCoordinates[polygon].length);			
		}
	}

}
