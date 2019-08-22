/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ops;

/**
 *
 * @author India
 */
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class SAE {

    private JPanel gui = null;
    private BorderLayout mainLayout;
    private FlowLayout buttonLayout;
    private EmptyBorder border;

    public Container getGui() {
        if (gui == null) {
            mainLayout = new BorderLayout(0, 0);
            gui = new JPanel(mainLayout);
            gui.setBackground(Color.RED);
            border = new EmptyBorder(0, 0, 0, 0);

            //data
            //create the root node
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("SAE");

            File f = new File(Config.qLearnCache);
            File pDir[] = f.listFiles();

            ArrayList<DefaultMutableTreeNode> primeDirs = new ArrayList<DefaultMutableTreeNode>();
            for (int i = 0; i < pDir.length; i++) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(pDir[i].getName());

                //sub dir
                File secList[] = pDir[i].listFiles();
                for (int j = 0; j < secList.length; j++) {
                    DefaultMutableTreeNode sNode = new DefaultMutableTreeNode(secList[j].getName());
                    node.add(sNode);
                }
                primeDirs.add(node);
            }

            //add the child nodes to the root node
            for (int i = 0; i < primeDirs.size(); i++) {
                root.add(primeDirs.get(i));
            }

            //create the tree by passing in the root node
            JTree tree = new JTree(root);

            ImageIcon imageIcon = new ImageIcon(SAE.class.getResource("leaf.jpg"));
            DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
            renderer.setLeafIcon(imageIcon);

            tree.setCellRenderer(renderer);
            tree.setShowsRootHandles(true);
            tree.setRootVisible(false);

            JTextArea ta = new JTextArea(10, 30);

            tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    String parent = "";
                    try {
                        parent = selectedNode.getParent().toString();
                    } catch (Exception ex) {
                        return;
                    }
                    //System.out.println(parent + "----" + selectedNode.getUserObject().toString());
                    File selectedDir = new File(Config.qLearnCache);
                    File selectedFile = new File(selectedDir, selectedNode.getUserObject().toString());
                    ta.setText("");
                    try {
                        // Open the file
                        FileInputStream fstream = new FileInputStream(selectedFile);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                        String strLine;

                        //Read File Line By Line
                        while ((strLine = br.readLine()) != null) {
                            // Print the content on the console
                            if (selectedFile.getName().equals("content.txt")) {
                                strLine = addLinebreaks(strLine);
                                ta.append(strLine + "\n");
                            } else {
                                ta.append(strLine + "\n");
                            }
                        }

                        //Close the input stream
                        br.close();

                    } catch (Exception ex) {
                        System.out.println(ex.toString());
                    }
                }

                private String addLinebreaks(String input) {
                    String[] tok = input.split("\\.");
                    String output = "";
                    for (int i = 0; i < tok.length; i++) {
                        String sentence = tok[i];
                        output += sentence + ".\n";
                    }
                    return output.toString();
                }
            });

            ///////
            gui.add(new JScrollPane(
                    tree,
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                    BorderLayout.LINE_START);
            gui.add(new JScrollPane(ta));

            buttonLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

            ChangeListener changeListener = new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                }
            };
        }

        return gui;
    }
    
    public static void getSAE_Aggregators(int R)
    {   final int count = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < count; i++) {
            new Thread(new Runnable() {
                int j = 0;
                public void run() {
                    while (j<count*R){
                        j++;
                    }
                }
            }).start();
        }
    }

    public static void init(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                SAE ws = new SAE();
                // the GUI as seen by the user (without frame)
                Container gui = ws.getGui();

                JFrame f = new JFrame("SAE");
                f.add(gui);
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.setSize(1024,768);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                f.setLocation(dim.width / 2 - f.getSize().width / 2, dim.height / 2 - f.getSize().height / 2);
                f.pack();
                // should be done last, to avoid flickering, moving,
                // resizing artifacts.
                //f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
}
