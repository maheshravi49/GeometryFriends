package ops;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author user
 *
 */
public class Target extends VicinityChecker {//Obstacle is going to be a rectangle

    int x;//x-coordinate of left upper corner where the origen for the obstacle will be
    int y;//y-coordinate of left upper corner where the origen for the obstacle will be

    int width;//width for the obstacle
    int height;//height for the obstacle

    Graphics pri_g;

//---------- constructor to initialize the origin, width, height, and sides vectors ------------------------------------------------------------------------------------------------------------
    public Target(int x, int y, int w, int h) {
        super(x, y, w, h);
        this.x = x;
        this.y = y;
        width = w;
        height = h;
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
        double distance = Math.sqrt((x - gPlayer.x) * (x - gPlayer.x) + (y - gPlayer.y) * (y - gPlayer.y));
        //System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>...."+distance);            
        if (distance < 50) {//since this class extends VicinityChecker, we have access to hasCollidedWith()            
            //this.drawNew(pri_g);
            //System.out.println("FLAG>>...." + distance);
            Config.points++;
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
        g.setColor(Color.GREEN);
        g.fillRect((int) (x - Camera.x), y, width, height);
        pri_g = g;
    }

    // not working
    public void drawNew(Graphics pri_g) {
        //super.draw(g);
        pri_g.setColor(Color.GRAY);
        pri_g.fillRect((int) (x - Camera.x), y, width, height);
    }
}
