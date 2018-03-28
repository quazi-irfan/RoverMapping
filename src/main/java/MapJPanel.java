import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MapJPanel extends JPanel implements MouseMotionListener, MouseListener
{
    Map map;
    int startMouseX, startMouseY, currentMouseX, currentMouseY;
    JLabel jLabel;
    boolean drawingRect = false;

    public MapJPanel(Map m, JLabel jLabel) {
        super(true);
        this.map = m;
        this.jLabel = jLabel;
        startMouseX = startMouseY = 0;
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setBackground(Color.WHITE);
        g2D.clearRect(0, 0, getWidth(), getHeight());

        for(int i = 0; i<600; i++)
            for(int j = 0; j<600; j++)
            {
                if(map.mapArray[i][j] == 1)
                {
                    g2D.setColor(Color.GRAY);
                    g2D.drawLine(i, j, i, j);
                }
                else if (map.mapArray[i][j] == 3)
                {
                    g2D.setColor(Color.RED);
                    g2D.drawLine(i, j, i, j);
                }
            }

        if(drawingRect)
        {
            g2D.setColor(Color.BLACK);
            g2D.fillRect(startMouseX, startMouseY, currentMouseX - startMouseX, currentMouseY - startMouseY);
        }

        g2D.dispose();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(drawingRect == false)
        {
            startMouseX = e.getX();
            startMouseY = e.getY();
            drawingRect = true;
        }
        else
        {
            currentMouseX = e.getX();
            currentMouseY = e.getY();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        jLabel.setText(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1)
            repaint();
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            this.map.resetAll();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if(drawingRect == true)
        {
            for(int i = startMouseX; i<currentMouseX; i++)
                for(int j = startMouseY; j<currentMouseY; j++)
                    this.map.setCellStatus(i, j, (byte)1);

            this.drawingRect = false;
        }
        repaint();

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
