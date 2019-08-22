package ops;

import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 *
 */
public class Obstacle extends VicinityChecker {//Obstacle is going to be a rectangle

    int x;//x-coordinate of left upper corner where the origen for the obstacle will be
    int y;//y-coordinate of left upper corner where the origen for the obstacle will be

    int width;//width for the obstacle
    int height;//height for the obstacle

    double leftSideVectorX;//starting and endig x-coordinates of the left side
    double leftSideVectorY;//starting and endig y-coordinates of the left side

    double upperSideVectorX;//starting and endig x-coordinates of the upper side
    double upperSideVectorY;//starting and endig y-coordinates of the upper side

//---------- constructor to initialize the origin, width, height, and sides vectors ------------------------------------------------------------------------------------------------------------
    public Obstacle(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        width = w;
        height = h;

        /*applying the Pythagorean Theorem for two order pair of points(http://orion.math.iastate.edu/butler/2012/spring/265/week2_review.pdf)
         double sideMagnitud = Math.sqrt((x -x)*(x-x)+(y-(y+height))*(y-(y+height)));
         leftSideVectorX = ((x-x))/sideMagnitud;
         leftSideVectorY = (y-(y+height))/sideMagnitud;*/
        //applying the Pythagorean Theorem for two order pair of points(http://orion.math.iastate.edu/butler/2012/spring/265/week2_review.pdf)
        double upperSideMagnitud = Math.sqrt((x - (x + width)) * ((x - (x + width)) + (y - y) * (y - y)));
        upperSideVectorX = ((x + width) - x) / upperSideMagnitud;
        upperSideVectorY = (y - y) / upperSideMagnitud;
    }

    /**
     * ********************************************************************************************************************************************************************************************
     * CollisionDetection checks the distance between the character and the
     * obstacle. If character collides with the obstacle, it will stop moving
     * forward(for now). It handles other possible events besides colliding with
     * the wall
     *
     * @param gPlayer
     */
    @SuppressWarnings("static-access")
    public void collisionDetection(Gamer gPlayer) {
		//System.out.println(gPlayer.collided);

        //measuring the distance between gPlayer and the side of the obstacle
        //double distanceToTheSide = (((gPlayer.x - x)*leftSideVectorY)-((gPlayer.y - y)*leftSideVectorX));
        double distanceToTheUpperSide = (((gPlayer.x - x) * upperSideVectorY) - ((gPlayer.y - y) * upperSideVectorX));

        if (this.hasCollidedWith(gPlayer)) {//since this class extends VicinityChecker, we have access to hasCollidedWith()
            //	System.out.println("collision");
            gPlayer.velocity = 0;//then he can no longer move forward(it will instead explode later on)
            gPlayer.collided = true;//this will stop all movements
            gPlayer.x = this.x - 50;//pushing gPlayer backwards so he can be right on the edge, and not inside the obstacle
            //Camera.movetoTheRight(0);//camara can no longer move
            Camera.restartCamera();
            int score = Config.points;

            if (Config.points <= 10) {
                Config.complexityLevel = 1;                
            }
            if (Config.points > 20) {
                Config.complexityLevel = 2;                
            }
            if (Config.points >= 35) {
                Config.complexityLevel = 3;                
            }
            String r = "";
            if (Config.system > 1) {
                if (Config.complexityLevel == 1) {
                    r = ("Game Engine Complexity Level Will be Rendered to Low");
                }
                if (Config.complexityLevel == 2) {
                    r = ("Game Engine Complexity Level Will be Rendered to Medium");
                }
                if (Config.complexityLevel == 3) {
                    r = ("Game Engine Complexity Level Will be Rendered to High");
                }
            }

            File f = new File(Config.dat);
            if (f.exists()) {
                f.delete();
            }
            try {
                // Assume default encoding.
                FileWriter fileWriter
                        = new FileWriter(f);

                // Always wrap FileWriter in BufferedWriter.
                BufferedWriter bufferedWriter
                        = new BufferedWriter(fileWriter);

                bufferedWriter.write(""+Config.complexityLevel);                

                // Always close files.
                bufferedWriter.close();
            } catch (IOException ex) {
                System.out.println(ex);
            // Or we could just do this:
                // ex.printStackTrace();
            }

            JOptionPane.showMessageDialog(null, "Your Score:" + Config.points + "\n" + r);
            Config.points = 0;
            Config.pathPoints = 0;
            System.exit(0);
        } else {

        }

        /*if the character is colliding with the left side wall, and is to the left of the wall, and at the same height of the obstacle
         if(gPlayer.x+100 <= 100 && distanceToTheSide >=0 && (gPlayer.y-100) >= this.y){
         gPlayer.velocity = 0;//then he can no longer move forward(it will instead explode later on)
         Camera.movetoTheRight(0);
         }*/
        //System.out.println(distanceToTheUpperSide);
        //if character's passed the obstacle origen, and hasn't gone beyond the end point of obstacle
        //and it remains at certain distance from the top of the obstacle
        if (gPlayer.x + 50 > x && gPlayer.x - 50 < x + width
                && distanceToTheUpperSide <= 50 && distanceToTheUpperSide >= 0) {
            gPlayer.y = this.y - 50;//change the character's height to be on top of the obstacle
            gPlayer.setCollisionBoxHeight(this.y - 100);
            gPlayer.fallingSpeed = 0;//he's no longer falling
            gPlayer.onGround = true;//he's on solid ground again, which means he can jump again
            gPlayer.canRotate = true;//he can rotate again too
        }

        //if the character has passed the obstacle, then make him fall
        if (gPlayer.x - 50 == x + width) {
            gPlayer.onGround = false;//he's no longer on solid ground 
            gPlayer.canRotate = false;//he can't rotate anymore
            gPlayer.y += gPlayer.gravity;//make gravity do the falling effect
        }
    }

    /**
     * *********************************************************************************************************************************************************************************************
     * Restart obstacle
     */
    public void restartObstacle(int x, int y) {
        super.restartCollisionBox(x, y);//actualize its collision box too
        this.x = x;
        this.y = y;
    }

    /**
     * *********************************************************************************************************************************************************************************************
     * Draw method just to show the a simple obstacle. Better graphics will be
     * implemented later on
     *
     * @param g
     */
    public void draw(Graphics g) {
        //super.draw(g);
        g.setColor(Color.black);
        g.fillRect((int) (x - Camera.x), y, width, height);
    }
}
