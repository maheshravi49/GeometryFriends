package ops;

/**
 * 
 * @author user
 *
 */
public class Camera {

	static double x;//coordinate used to substract this x from others' x-coordinate
	static double y;////coordinate used to substract this y from others' y-coordinate
	
/***************************************************************************************************************************************************************************
 * This method will move everything to the left while camera "moves to the right"
 * @param deltaX
 */
	public static void movetoTheRight(int deltaX){
		x += deltaX;//camera goes to the right as its x-value is increasing
	}
	
/***************************************************************************************************************************************************************************
 * This method will move everything down while camera "moves up"
 * @param deltaY
 */
	public void moveUp(int deltaY){
		y -= deltaY;//the camara goes up as its y-value is decreasing
	}
	
/***************************************************************************************************************************************************************************
 * This method will move everything up up while camera "moves down"
 * @param deltaX
 */
	public void moveDown(int deltaY){
		y+= deltaY;//the camera will go down as its y-coordinate is increasing
	}
	
/***************************************************************************************************************************************************************************
 * Restart the position of the camera
 */
	public static void restartCamera(){
		x = 0;//set the camera back to its default position
		y = 0;//set the camera back to its default position
	}
}
