import javax.swing.*;
import java.io.IOException;

public class MapGUITest
{
    // todo find another path when you are stuck
    // todo save the 2d array
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MapGUI();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
