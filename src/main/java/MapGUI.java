import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class MapGUI
{
    Rover r;
    MapJPanel mapJPanel;
    JLabel jLblBFSStatus;


    public MapGUI() throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(1000, 750));
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(new FlowLayout(1, 100, 100));
        jFrame.setLayout(null);

        Map m = new Map(600, 600);
        JTextField jTextField = new JTextField();
        jTextField.setLocation(700, 50);
        jTextField.setSize(150, 25);
//        jTextField.setPreferredSize(new Dimension(250, 100));
//        jTextField.setMinimumSize(new Dimension(250, 100));
//        jTextField.setMaximumSize(new Dimension(250, 100));
        jTextField.setText("StartX StartY DestX DestY");
        jTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1)
                    jLblBFSStatus.setText("");
            }
        });
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String[] input = jTextField.getText().split(" ");
                    r = new Rover(m, Integer.valueOf(input[0]),Integer.valueOf(input[1]),Integer.valueOf(input[2]),Integer.valueOf(input[3]));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Thread.currentThread().setName("PATHFINDING");
                            boolean unreachable = false;
                            while (!r.atDestination)
                            {
                                if(!r.move())
                                {
                                    unreachable = true;
                                    jLblBFSStatus.setText("Unreachable");
                                    break;
                                }
                            }
                            if(!unreachable)
                            {
                                mapJPanel.setpath(new ArrayList<>(r.path));
                                jLblBFSStatus.setText("Path found");
                            }
                            mapJPanel.repaint();
                        }
                    }).start();

                }
            }
        });
        jFrame.add(jTextField);

        jLblBFSStatus = new JLabel();
        jLblBFSStatus.setLocation(850, 50);
        jLblBFSStatus.setSize(150, 25);
        jFrame.add(jLblBFSStatus);

        JLabel jLblMapCoordinate = new JLabel("X Y");
        jLblMapCoordinate.setLocation(700, 75); // 75 = 50 for top margin + 25 for jTextBox
        jLblMapCoordinate.setSize(150, 25);
//        jLblMapCoordinate.setPreferredSize(new Dimension(250, 100));
//        jLblMapCoordinate.setMinimumSize(new Dimension(250, 100));
//        jLblMapCoordinate.setMaximumSize(new Dimension(250, 100));
        jFrame.add(jLblMapCoordinate);

        mapJPanel = new MapJPanel(m, jLblMapCoordinate);
        mapJPanel.setLocation(50, 50);
        mapJPanel.setSize(600, 600);
//        mapJPanel.setPreferredSize(new Dimension(600, 600));
//        mapJPanel.setLocation(100, 100);
        jFrame.add(mapJPanel);

        JTextField jTxtfileName = new JTextField();
        jTxtfileName.setLocation(700, 100);
        jTxtfileName.setSize(150, 25);
        jFrame.add(jTxtfileName);

        JLabel jLblSaveLoadStatus = new JLabel("");
        jLblSaveLoadStatus.setLocation(850, 100);
        jLblSaveLoadStatus.setSize(150, 25);
        jFrame.add(jLblSaveLoadStatus);

        // SAVE
        JButton jBtnSave = new JButton("Save");
        jBtnSave .setLocation(700, 125);
        jBtnSave.setSize(75, 25);
        jBtnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jTxtfileName.getText().equals(""))
                {
                    jLblSaveLoadStatus.setText("Enter Map file Name");
                }
                else
                {
                    PrintStream writer = null;

                    try {
                        writer = new PrintStream(new File(jTxtfileName.getText()));
                    } catch (IOException e1) {
                        jLblSaveLoadStatus.setText("Error opening file " + jTextField.getText());
                    }
                    for(int col = 0; col<600; col++)
                    {
                        for(int row = 0; row<600; row++)
                            writer.print(m.getCellStatus(row, col) + " ");
                        writer.println();
                    }

                    jLblSaveLoadStatus.setText("Map saved:" + jTxtfileName.getText());
                    jTxtfileName.setText("");
                    writer.close();
                }
            }
        });
        jFrame.add(jBtnSave);

        // LOAD
        JButton jBtnLoad = new JButton("Load");
        jBtnLoad.setLocation(775, 125);
        jBtnLoad.setSize(75, 25);
        jBtnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jTxtfileName.getText().equals(""))
                {
                    jLblSaveLoadStatus.setText("Enter Map file Name");
                }
                else
                {
                    BufferedReader reader = null;

                    try {
                        reader = new BufferedReader(new FileReader(jTxtfileName.getText()));
                    } catch (FileNotFoundException e1) {
                        jLblSaveLoadStatus.setText("Error opening file " + jTextField.getText());
                    }
                    try {
                        m.resetAll();

                        String inputString;
                        int row = 0;
                        while ( (inputString = reader.readLine()) != null)
                        {
                            String[] stringArray = inputString.split(" ");

                            for(int i = 0; i< 600; i++)
                                m.setCellStatus(i,row, Byte.valueOf(stringArray[i]));

                            row++;
                        }
                        mapJPanel.repaint();

                    } catch (IOException e1) {
                        jLblSaveLoadStatus.setText("Error opening file " + jTextField.getText());
                    }
                    try {
                        jLblSaveLoadStatus.setText("Map Loaded:" + jTxtfileName.getText());
                        jTxtfileName.setText("");
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        jFrame.add(jBtnLoad);

        JCheckBox jCckBx = new JCheckBox("Grid");
        jCckBx.setSelected(true);
        jCckBx.setLocation(700, 150);
        jCckBx.setSize(75, 25);
        jCckBx.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox tempCheck = (JCheckBox)e.getSource();
                if(tempCheck.isSelected())
                    mapJPanel.setDrawGrid(true);
                else
                    mapJPanel.setDrawGrid(false);
            }
        });
        jFrame.add(jCckBx);

        jFrame.setVisible(true);
    }
}
