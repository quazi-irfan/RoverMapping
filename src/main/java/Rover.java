import java.util.*;

public class Rover
{
    Map map;
    int startRow, startCol, destinationRow, destinationCol;
    boolean atDestination = false;
    Queue<Integer[]> frontier;
    ArrayList<Integer[]> path;

    public Rover(Map map, int startRow, int startCol, int destinationRow, int destinationCol)
    {
        this.map = map;

        this.startRow = startRow;
        this.startCol = startCol;
        this.destinationRow = destinationRow;
        this.destinationCol = destinationCol;
        this.atDestination = false;

        // this line can be removed since I never used MapEnum.ROVER_START_LOC; instead I am using local variables to keep track of the rover
        this.map.mapArray[startRow][startCol] = MapEnum.ROVER_START_LOC.getValue();
        this.map.mapArray[destinationRow][destinationCol] = MapEnum.ROVER_DESTINATION.getValue();

        this.frontier = new LinkedList<>();
        this.frontier.add(new Integer[]{startRow, startCol});
    }

    void move()
    {
        Integer[] current = frontier.poll();
        if(current != null) // if the queue is not empty
        {
            if(!(current[0] == destinationRow & current[1] == destinationCol))
            {
                ArrayList<Integer[]> neighbours = getNeighbours(current);
                for(Integer[] next : neighbours)
                {
                    if(map.isAccessible(next[0], next[1]))
                    {
                        frontier.add(next);
                        map.setCellStatus(next[0], next[1], findIncomingDirection(current, next));
                    }
                }
            }
            else
            {
                path = new ArrayList<>();
                while (!(current[0] == startRow & current[1] == startCol))
                {
                    path.add(current.clone());
                    if(map.getCellStatus(current[0], current[1]) == MapEnum.NORTH.getValue())
                        current[0] += 1;
                    else if(map.getCellStatus(current[0], current[1]) == MapEnum.SOUTH.getValue())
                        current[0] -= 1;
                    else if(map.getCellStatus(current[0], current[1]) ==  MapEnum.EAST.getValue())
                        current[1] -= 1;
                    else if(map.getCellStatus(current[0], current[1]) == MapEnum.WEST.getValue())
                        current[1] += 1;
                }
                atDestination = true;
                path.add(new Integer[]{startRow, startCol}); // add the start location
                Collections.reverse(path); // because we get path from last to first
                map.clearNSEW(); // clear the NSEW numbers from the array
            }
        }
    }

    private byte findIncomingDirection(Integer[] previous, Integer[] current)
    {
        if(current[0] +1 == previous[0] && current[1] == previous[1])
            return MapEnum.NORTH.getValue();
        else if(current[0] -1 == previous[0]&& current[1]  == previous[1])
            return MapEnum.SOUTH.getValue();
        else if(current[0] == previous[0] && current[1] == previous[1] - 1)
            return MapEnum.WEST.getValue();
        else if(current[0] == previous[0] && current[1] == previous[1] + 1)
            return MapEnum.EAST.getValue();

        return Byte.MIN_VALUE;
    }

    ArrayList<Integer[]> getNeighbours(Integer[] current)
    {
        ArrayList<Integer[]> allNeighbourList = new ArrayList<>();

        allNeighbourList.add(new Integer[]{current[0]-1, current[1]});

        allNeighbourList.add(new Integer[]{current[0], current[1]-1});
        allNeighbourList.add(new Integer[]{current[0], current[1]+1});
        
        allNeighbourList.add(new Integer[]{current[0]+1, current[1]});

        return allNeighbourList;
    }
}

