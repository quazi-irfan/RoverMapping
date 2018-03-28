import java.io.IOException;

public class MapTest
{
    public static void main(String[] args) throws IOException
    {
        Map m = new Map(5, 5);
        m.populateMapFromFile("map");
        System.out.println(m);

        Rover r = new Rover(m, 0, 0, 4, 4);
        System.out.println(m);

        while (!r.atDestination)
        {
            r.move();
            System.out.println(m);
        }

    }
}
