package ops;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class UI extends JPanel {

    static JFrame frame;

    static String existing = "Existing";
    static String proposed = "Proposed";
    static String enhancement = "Enhancement";

    JLabel picture;

    public UI() {
        // Create the radio buttons.
        JRadioButton eButton = new JRadioButton(existing);
        eButton.setMnemonic(KeyEvent.VK_B);
        eButton.setActionCommand(existing);
        //eButton.setSelected(true);

        JRadioButton pButton = new JRadioButton(proposed);
        pButton.setMnemonic(KeyEvent.VK_C);
        pButton.setActionCommand(proposed);

        JRadioButton xButton = new JRadioButton(enhancement);
        xButton.setMnemonic(KeyEvent.VK_D);
        xButton.setActionCommand(enhancement);

        // Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(eButton);
        group.add(pButton);
        group.add(xButton);

        // Register a listener for the radio buttons.
        RadioListener myListener = new RadioListener();
        eButton.addActionListener(myListener);
        pButton.addActionListener(myListener);
        xButton.addActionListener(myListener);

        // Set up the picture label
        picture = new JLabel(new ImageIcon("images/"
                + existing
                + ".png"));

        picture.setVisible(false);

        // The preferred size is hard-coded to be the width of the
        // widest image and the height of the tallest image.
        // A real program would compute this.
        picture.setPreferredSize(new Dimension(677, 122));

        // Put the radio buttons in a column in a panel
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new GridLayout(0, 1));
        radioPanel.add(eButton);
        radioPanel.add(pButton);
        radioPanel.add(xButton);

        setLayout(new BorderLayout());
        add(radioPanel, BorderLayout.WEST);
        add(picture, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    /**
     * Listens to the radio buttons.
     */
    class RadioListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            picture.setVisible(true);
            picture.setIcon(new ImageIcon("images/"
                    + e.getActionCommand()
                    + ".png"));

            if (e.getActionCommand().equals("Existing")) {
                Config.system = 1;
            }
            if (e.getActionCommand().equals("Proposed")) {
                Config.system = 2;
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like Start New(Y) or Go Next Level(N)", "Note", dialogButton);
                String line = "1";
                if (dialogResult == 0) {
                    Config.complexityLevel = 1;
                } else {
                    try {
                        // FileReader reads text files in the default encoding.
                        FileReader fileReader
                                = new FileReader(Config.dat);

                        // Always wrap FileReader in BufferedReader.
                        BufferedReader bufferedReader
                                = new BufferedReader(fileReader);

                        while ((line = bufferedReader.readLine()) != null) {                            
                            Config.complexityLevel = Integer.parseInt(line.trim());
                        }

                        // Always close files.
                        bufferedReader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (e.getActionCommand().equals("Enhancement")) {
                Config.system = 3;
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like Start New(Y) or Go Next Level(N)", "Note", dialogButton);
                String line = "1";
                if (dialogResult == 0) {
                    Config.complexityLevel = 1;
                } else {
                    try {
                        // FileReader reads text files in the default encoding.
                        FileReader fileReader
                                = new FileReader(Config.dat);

                        // Always wrap FileReader in BufferedReader.
                        BufferedReader bufferedReader
                                = new BufferedReader(fileReader);

                        while ((line = bufferedReader.readLine()) != null) {
                            Config.complexityLevel = Integer.parseInt(line.trim());
                        }

                        // Always close files.
                        bufferedReader.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            Game game = new Game();
            game.startTheGame();
        }
    }

    public static void main(String s[]) {
        frame = new JFrame(Config.title);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.setSize(800, 200);
        frame.getContentPane().add(new UI(), BorderLayout.CENTER);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
    }
}
