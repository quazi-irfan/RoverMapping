import java.io.IOException;
import java.util.Arrays;

public class MapTest
{
    public static void main(String[] args) throws IOException
    {
        Map m = new Map(5, 5);
        m.populateMapFromFile("map");
        System.out.println(m);

        Rover r = new Rover(m, 4, 0, 0, 4);
        System.out.println(m);

        while (!r.atDestination)
        {
            r.move();
            System.out.println(m);
        }
        System.out.println(Arrays.deepToString(r.path.toArray()));

    }
}
