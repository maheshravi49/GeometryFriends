package ops;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author user
 *
 */
public class Floor {
	int x1;//x-coordinate for the beginning of the floor
	int y1;//y-coordinate for the beginning of the floor
	
	int x2;//x-coordinate for the ending of the floor
	int y2;//y-coordinate for the beginning of the floor
	
	double xVector;//used to get distance between player and floor
	double yVector;//used to get distance between player and floor
	
//---------- constructor --------------------------------------------------------------------------------------------------------------------
	public Floor(int x1, int y1, int x2, int y2){
		this.x1 = x1;//x-coordinate for starting point of the floor
		this.y1 = y1;//y-coordinate for starting point of the floor
		this.x2 = x2;//x-coordinate for ending point of the floor
		this.y2 = y2;//y-coordinate for ending point of the floor
		
		double magnitud = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
		xVector = (x2-x1)/magnitud;//we need an unit vector, thats why we divide it by the magnitud 
		yVector = (y2-y1)/magnitud;
	}
	
/********************************************************************************************************************************************
 *This method keeps track of the character's height. If the distance between the floor
 *and the character is decreasing, it means the character is falling and therefore,
 *we make sure it does not go beyond the floor.
 * @param gPlayer
 */
	@SuppressWarnings("static-access")
	public void keepPlayerOnTheGround(Gamer gPlayer){
		double distance = (((gPlayer.x - x1)*yVector)-((gPlayer.y - y1)*xVector));
		
		if(distance <= 50){//if the edge of the character is touching the floor
			gPlayer.y = this.y1-50;//change its height to be lower than the floor
			gPlayer.setCollisionBoxHeight(this.y1 -100);
			gPlayer.fallingSpeed = 0;//since the character is touching the floor, he doesnt fall anymore
			gPlayer.onGround = true;//character is on a solid ground
			gPlayer.canRotate = true;//can rotate again
		}
	}
	
/********************************************************************************************************************************************
 * Restart floor's position by setting the coordinates back to their default position
 */
	public void restartFloor(int x1, int y1, int x2, int y2){
		this.x1 = x1;//x-coordinate for starting point of the floor
		this.y1 = y1;//y-coordinate for starting point of the floor
		this.x2 = x2;//x-coordinate for ending point of the floor
		this.y2 = y2;//y-coordinate for ending point of the floor
	}
	
/********************************************************************************************************************************************
 * Draw method to illustrate the floor for the game(it is currently used as a reference, 
 * it will be delated later on). 
 * @param g
 */
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.drawLine(x1, y1, x2, y2);
	}
}
