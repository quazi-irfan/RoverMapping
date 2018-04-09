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
    ArrayList<Integer[]> path;

    public MapJPanel(Map m, JLabel jLabel)
    {
        super(true);
        this.map = m;
        this.jLabel = jLabel;
        startMouseX = startMouseY = 0;
        path = new ArrayList<>();
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
//        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g.create();

        // draw the white canvas
        g2D.setBackground(Color.WHITE);
        g2D.clearRect(0, 0, getWidth(), getHeight());

        // go over the path list and paint red dots
        if(path != null && path.size() > 0)
            for(Integer[] i : path)
            {
                g2D.setColor(Color.RED);
                g2D.drawLine(i[0],i[1],i[0],i[1]);
            }

        // go over the map array and paint grey dots
        for(int i = 0; i<map.row; i++)
            for(int j = 0; j<map.col; j++)
                if(map.mapArray[i][j] == MapEnum.OBSTACLE.getValue())
                {
                    g2D.setColor(Color.GRAY);
                    g2D.drawLine(i, j, i, j);
                }

        // draw the black rectangle
        if(drawingRect)
        {
            g2D.setColor(Color.BLACK);
            g2D.fillRect(startMouseX, startMouseY, currentMouseX - startMouseX, currentMouseY - startMouseY);
        }

        g2D.dispose();
}

    @Override
    public void mouseDragged(MouseEvent e)
    {
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
    public void mouseMoved(MouseEvent e)
    {
        jLabel.setText(e.getX() + " " + e.getY());
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            repaint();
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            this.map.resetAll();
            this.path = null;
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(this.drawingRect == true)
        {
            for(int i = startMouseX; i<currentMouseX; i++)
                for(int j = startMouseY; j<currentMouseY; j++)
                    this.map.setCellStatus(i, j, MapEnum.OBSTACLE.getValue());

            this.drawingRect = false;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void setpath(ArrayList<Integer[]> path)
    {
        this.path = path;
    }
}
