package ops;

import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author user
 *
 */
public class Game extends GamePanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static int obs = 400;
//---------- instances- -----------------------------------------------------------------------------------------------------------------
    SoundRendition backgroundMusic;
    ImageLayer background = new ImageLayer("background.jpg", 0, 0, 8, 800, 100);
    ImageLayer realFloor = new ImageLayer("floor.png", 0, 700, 1, 2000, 30);

    Gamer gPlayer = new Gamer(-200, 0, 0);
    Floor floor = new Floor(0, 700, 7000, 700);

    //int x;//x-coordinate of left upper corner where the origen for the obstacle will be
    //int y;//y-coordinate of left upper corner where the origen for the obstacle will be	
    //int width;//width for the obstacle
    //int height;//height for the obstacle
//    Obstacle obstacle1 = new Obstacle(1000, 550, 800, 150);
//    Obstacle obstacle2 = new Obstacle(3700, 500, 800, 200);
//    Obstacle obstacle3 = new Obstacle(4300, 350, 200, 150);
//    Obstacle obstacle4 = new Obstacle(6000, 500, 500, 200);
    Obstacle mObs[] = new Obstacle[obs];

    Target points[] = new Target[obs * 2];

    int sX = 1000;
    int sY = 500;

    int sW = 0;
    int sH = 0;

    int phase = 0;

    public Game() {
        if (Config.system == 1) {
            lowEndRender();
            Config.gameSpeed = 30;
        }
        if (Config.system == 2) {
            Config.gameSpeed = 30;
            QLearning ql = new QLearning();
            ql.init();
            ql.calculateQ();
            if (Config.complexityLevel == 1) {
                lowEndRender();
            }
            if (Config.complexityLevel == 2) {
                mediumEndRender();
            }
            if (Config.complexityLevel == 3) {
                challengingRender();
            }
        }
        if (Config.system == 3) {
            if (Config.complexityLevel == 1) {
                Config.gameSpeed = 30;
            }
            if (Config.complexityLevel == 2) {
                Config.gameSpeed = 5;
            }
            if (Config.complexityLevel == 3) {
                Config.gameSpeed = 5;
            }
            QLearning ql = new QLearning();
            ql.init();
            ql.calculateQ();
            SAE.getSAE_Aggregators(Config.system);
            if (Config.complexityLevel == 1) {
                lowEndRender();
            }
            if (Config.complexityLevel == 2) {
                mediumEndRender();
            }
            if (Config.complexityLevel == 3) {
                challengingRender();
            }
        }
    }

    /**
     * *************************************************************************************************************************************
     * startTheGame calls a method in GamePanel that sets up the screen
     */
    public void startTheGame() {
        backgroundMusic = new SoundRendition(Config.bgScore);//this loads the music background
        backgroundMusic.playAudio();//play the background music
        startFrame();
    }

    /**
     * **************************************************************************************************************************************
     * respondToInput is used to determine what to do when the user presses keys
     * or mouse
     */
    @Override
    public void handleMovement() {
        //if gPlayer is at a certain point and hasn't collided yet. move everything slowly 
        if (gPlayer.x - 560 > 0 && gPlayer.x - 560 < 400 && !gPlayer.collided) {
            Camera.movetoTheRight(25);
            PlayerTracker.velocity = 25;
        }

        //if gPlayer is at a certain point and hasn't collided yet. move everything faster
        if (gPlayer.x - 560 >= 400 && !gPlayer.collided) {
            Camera.movetoTheRight(28);
            PlayerTracker.velocity = 28;
        }
        if (gPlayer.x <= 0) {
            PlayerTracker.velocity = 10;
        }
        gPlayer.moveForward();//will move if velocity is not zero(when collides, it changes to zero)
        gPlayer.fall();//will jump if gPlayer is on ground 
    }

    /**
     * **************************************************************************************************************************************
     * handleCollisions is used to check collisions between character and
     * obstacles
     */
    @Override
    public void handleCollisions() {
        floor.keepPlayerOnTheGround(gPlayer);
//        obstacle1.collisionDetection(gPlayer);
//        obstacle2.collisionDetection(gPlayer);
//        obstacle3.collisionDetection(gPlayer);
//        obstacle4.collisionDetection(gPlayer);

        for (int i = 0; i < mObs.length; i++) {
            mObs[i].collisionDetection(gPlayer);
        }
        for (int i = 0; i < points.length; i++) {
            try {
                points[i].collisionDetection(gPlayer);
            } catch (Exception e) {

            }
        }
    }

    /**
     * ***************************************************************************************************************************************
     * Restart the whole game by seting everything to its default location
     */
    public void restartGame() {//restart everything by setting them back to their default positions
        backgroundMusic.restartAudio();
        background.restart(0, 0);
        realFloor.restart(0, 700);
        gPlayer.restart(10, 650, 0);
        floor.restartFloor(0, 700, 1000, 700);
//        obstacle1.restartObstacle(1000, 550);
//        obstacle2.restartObstacle(3700, 500);
//        obstacle3.restartObstacle(4300, 350);
//        obstacle4.restartObstacle(6000, 500);
        Camera.restartCamera();
        gPlayer.collided = false;
    }

    /**
     * ***************************************************************************************************************************************
     * draw calls the update method inherited from GamePanel
     */
    @Override
    public void draw() {
        flipPages = getBufferStrategy();//get a reference of the bufferStrategy created in GamePanel
        if (flipPages == null) {//if bufferStrategy is empty
            createBufferStrategy(3);//create a new one
            return;
        }

        Graphics g = flipPages.getDrawGraphics();//get the graphics object
        super.update(g);//The canvas is first cleared by filling it with the background color, and then completely redrawn by calling this canvas's paint method
        background.draw(g);
        realFloor.draw(g);
        if (!(gPlayer.collided)) {
            gPlayer.draw(g);
            //floor.draw(g);
//            obstacle1.draw(g);
//            obstacle2.draw(g);
//            obstacle3.draw(g);
//            obstacle4.draw(g);

            for (int i = 0; i < mObs.length; i++) {
                mObs[i].draw(g);
            }
            for (int i = 0; i < points.length; i++) {
                try {
                    points[i].draw(g);
                } catch (Exception e) {

                }

            }
        }

        if (gPlayer.collided) {
            restartGame();
        }

        g.dispose();
        flipPages.show();
    }

    private void lowEndRender() {
        mObs = new Obstacle[obs];
        points = new Target[obs * 2];

        for (int i = 0; i < mObs.length; i++) {
            if (i % 60 == 0) {
                phase++;
            }

            if (i <= obs / 4) {
                sW = 50;
                sH = 50;
            } else if (i <= obs / 2) {
                sW = 50;
                sH = 50;
            } else if (i <= ((3 * obs) / 4)) {
                sW = 100;
                sH = 50;
            } else {
                sW = 200;
                sH = 200;
            }

            if (i == 0) {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i] = new Target(tx, ty, sW, sH);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 1] = new Target(R, ty, sW, sH);
                }

            } else {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i + 1] = new Target(tx, ty, sW / 2, sH / 2);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 2] = new Target(R, ty, sW, sH);
                }
            }

            if (phase == 1) {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            } else if (phase == 2) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = 200;
                High = 500;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else if (phase == 3) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 150;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
                if (R % 2 == 0) {
                    Low = 500;
                    High = 800;
                    R = r.nextInt(High - Low) + Low;
                } else {
                    Low = 200;
                    High = 500;
                    R = r.nextInt(High - Low) + Low;
                }
                sY = R;

            } else if (phase == 4) {
                Random r = new Random();
                int Low = sX - 50;
                int High = sX + 50;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = sY - 20;
                High = sY + 20;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            }
        }
    }

    private void mediumEndRender() {
        System.out.println("MMMMMMMMMMMM");
        mObs = new Obstacle[obs];
        points = new Target[obs * 2];

        for (int i = 0; i < mObs.length; i++) {
            if (i % 20 == 0) {
                phase++;
            }

            if (i <= obs / 4) {
                sW = 50;
                sH = 50;
            } else if (i <= obs / 2) {
                sW = 50;
                sH = 50;
            } else if (i <= ((3 * obs) / 4)) {
                sW = 100;
                sH = 50;
            } else {
                sW = 200;
                sH = 200;
            }

            if (i == 0) {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i] = new Target(tx, ty, sW, sH);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 1] = new Target(R, ty, sW, sH);
                }

            } else {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i + 1] = new Target(tx, ty, sW / 2, sH / 2);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 2] = new Target(R, ty, sW, sH);
                }
            }

            if (phase == 1) {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            } else if (phase == 2) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = 200;
                High = 500;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else if (phase == 3) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 150;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
                if (R % 2 == 0) {
                    Low = 500;
                    High = 800;
                    R = r.nextInt(High - Low) + Low;
                } else {
                    Low = 200;
                    High = 500;
                    R = r.nextInt(High - Low) + Low;
                }
                sY = R;

            } else if (phase == 4) {
                Random r = new Random();
                int Low = sX - 50;
                int High = sX + 50;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = sY - 20;
                High = sY + 20;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            }
        }
    }

    private void challengingRender() {
        mObs = new Obstacle[obs];
        points = new Target[obs * 2];

        for (int i = 0; i < mObs.length; i++) {
            if (i % 10 == 0) {
                phase++;
            }

            if (i <= obs / 4) {
                sW = 50;
                sH = 50;
            } else if (i <= obs / 2) {
                sW = 50;
                sH = 50;
            } else if (i <= ((3 * obs) / 4)) {
                sW = 100;
                sH = 50;
            } else {
                sW = 200;
                sH = 200;
            }

            if (i == 0) {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i] = new Target(tx, ty, sW, sH);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 1] = new Target(R, ty, sW / 2, sH / 2);
                }

            } else {
                mObs[i] = new Obstacle(sX, sY, sW, sH);

                int tx = sX;
                int ty = sY;
                if (sY % 2 == 0) {
                    ty -= 200;
                }
                points[i + 1] = new Target(tx, ty, sW / 2, sH / 2);
                if (sX % 2 == 0) {
                    Random r = new Random();
                    int Low = sX;
                    int High = sX + 500;
                    int R = r.nextInt(High - Low) + Low;
                    points[i + 2] = new Target(R, ty, sW / 2, sH / 2);
                }
            }

            if (phase == 1) {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            } else if (phase == 2) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = 200;
                High = 500;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else if (phase == 3) {
                Random r = new Random();
                int Low = sX;
                int High = sX + 150;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
                if (R % 2 == 0) {
                    Low = 500;
                    High = 800;
                    R = r.nextInt(High - Low) + Low;
                } else {
                    Low = 200;
                    High = 500;
                    R = r.nextInt(High - Low) + Low;
                }
                sY = R;

            } else if (phase == 4) {
                Random r = new Random();
                int Low = sX - 50;
                int High = sX + 50;
                int R = r.nextInt(High - Low) + Low;
                sX = R;

                Low = sY - 20;
                High = sY + 20;
                R = r.nextInt(High - Low) + Low;
                sY = R;
            } else {
                Random r = new Random();
                int Low = sX + 20;
                int High = sX + 200;
                int R = r.nextInt(High - Low) + Low;
                sX = R;
            }
        }
    }

}
