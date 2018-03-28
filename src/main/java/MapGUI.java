import javax.swing.*;
import javax.swing.plaf.basic.DefaultMenuLayout;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MapGUI
{
    Rover r;
    MapJPanel mapJPanel;


    public MapGUI() throws IOException {
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(new Dimension(1000, 1000));
        jFrame.setResizable(false);
        jFrame.getContentPane().setLayout(new FlowLayout(1, 100, 100));

        Map m = new Map(600, 600);
        JTextField jTextField = new JTextField();
        jTextField.setPreferredSize(new Dimension(250, 100));
        jTextField.setMinimumSize(new Dimension(250, 100));
        jTextField.setMaximumSize(new Dimension(250, 100));
        jTextField.setText("StartX StartY DestX DestY");
        jTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    String[] input = jTextField.getText().split(" ");
                    r = new Rover(m, Integer.valueOf(input[0]),Integer.valueOf(input[1]),Integer.valueOf(input[2]),Integer.valueOf(input[3]));
                    m.clearPlayerAndPath();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!r.atDestination)
                            {
                                r.move();
                            }
                        }
                    }).start();
                }
            }
        });
        jFrame.add(jTextField);

        JLabel jLabel = new JLabel("X Y");
        jLabel.setPreferredSize(new Dimension(250, 100));
        jLabel.setMinimumSize(new Dimension(250, 100));
        jLabel.setMaximumSize(new Dimension(250, 100));
        jFrame.add(jLabel);

        mapJPanel = new MapJPanel(m, jLabel);
        mapJPanel.setPreferredSize(new Dimension(600, 600));
        mapJPanel.setLocation(100, 100);

        jFrame.add(mapJPanel);


        jFrame.setVisible(true);
    }
}
