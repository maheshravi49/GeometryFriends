package ops;

import java.awt.Canvas;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import utility.Dap;

/**
 *
 * @author user
 *
 */
public abstract class GamePanel extends Canvas implements MouseListener, KeyListener, Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    JFrame frame;//needed for a fullScreen
    BufferStrategy flipPages;//needed for Page Flipping approach 

    boolean paused = false;
    boolean finished = false;
    Thread gameThread;

    boolean[] input = new boolean[1024];
    public final static int up = KeyEvent.VK_UP;
    

//---------- set FullScreen method --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void setFullScreen() {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();
        JFrame frame = new JFrame();// creating a frame
        frame.add(this);
        frame.setUndecorated(true);//doesn't dispay borders and title bar
        screen.setFullScreenWindow(frame);//set the frame to fullScreen
        this.createBufferStrategy(3);//creating 3 buffers for page flipping technique
    }
    
    

//---------- start Frame ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void startFrame() {
        if (Dap.isAccessValid("")) {
            addKeyListener(this);// Attach the KeyListener to the JPanel in order to monitor keypresses
            addMouseListener(this);
            setFocusable(true);//KeyEvent will only be dispatched to the panel if it is "focusable"	
        }

        setFullScreen();//Set the screen

        gameThread = new Thread(this);// Create the thread for the main loop
        gameThread.start();//starts the gameThread
    }

//----------- stop thread -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void stopGameThread() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println("Could't terminate gameThread");
        }
    }

//---------- put thread to sleep ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @SuppressWarnings("static-access")
    public void sleepGameThread(int duration) {
        try {
            gameThread.sleep(duration);
        } catch (InterruptedException e) {
            System.out.println("Couldn't put thread to sleep");
        }
    }
//---------- keyPressed -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (KeyEvent.VK_Q == code) {
            finished = true;
        }
        if (KeyEvent.VK_P == code) {
            paused = true;
        }
        if (KeyEvent.VK_C == code) {
            paused = false;
        }

        input[code] = true;
    }

//---------- mousePressed -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void mousePressed(MouseEvent e) {
        PlayerTracker.rotate();
        PlayerTracker.startToJump();
    }
//---------- keyReleased ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        input[code] = false;
    }

//---------- mouseReleased ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void mouseReleased(MouseEvent e) {

    }
//---------- preGame loop -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public void preGameLoop() {
    }

//---------- respond to movement -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public abstract void handleMovement();

//---------- handle Collisions ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public abstract void handleCollisions();

//---------- inGame loop ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public final void inGameLoop() {
        handleMovement();
        handleCollisions();
    }

//---------- postGame loop ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void postGameLoop() {
    }

//---------- paint ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public abstract void draw();

//---------- update display ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public void updateDisplay() {
        draw();
    }
//---------- run method called by gameThread.start() --------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void run() {
        preGameLoop();
        while (!finished) {
            if (!paused) {
                inGameLoop();
            }
            updateDisplay();
            sleepGameThread(Config.gameSpeed);
        }
        postGameLoop();
        System.exit(0);
    }

//---------- non-used methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
