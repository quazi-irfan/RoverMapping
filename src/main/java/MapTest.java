import java.io.IOException;
import java.util.Arrays;

public class MapTest
{
    public static void main(String[] args) throws IOException
    {
        Map m = new Map(3, 3);
        m.populateMapFromFile("map");
        System.out.println(m);

        Rover r = new Rover(m, 0, 0, 4, 4);
        System.out.println(m);

        boolean unreachable = false;
        while (!r.atDestination)
        {
            if(!r.move())
            {
                unreachable = true;
                System.out.println("Unreachable");
                break;
            }
            System.out.println(m);
        }
        System.out.println(Arrays.deepToString(r.path.toArray()));

    }
}
