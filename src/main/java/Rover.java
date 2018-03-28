import java.util.ArrayList;

/**
 *      Map Status
 *
 *      0 freeSpace
 *      1 obstacle
 *      2 rover
 *      3 visted
 *      4 beanbag
 *      5 scanned
 */

public class Rover
{
    Map map;
    int currentRow, currentCol, destinationRow, destinationCol;
    boolean atDestination = false;

    public Rover(Map map, int currentRow, int currentCol, int destinationRow, int destinationCol)
    {
        this.map = map;
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.map.mapArray[currentRow][currentCol] = (byte)2;
        this.destinationRow = destinationRow;
        this.destinationCol = destinationCol;
    }

    void move()
    {
        int nextRow, nextCol;
        nextRow = nextCol = 0;
        double currentLowestDistance = Integer.MAX_VALUE;

        ArrayList<Integer[]> getPeripheralIndices = getAccessiblePeripheralIndices();
        for(int i =0; i<getPeripheralIndices.size(); i++)
        {
            if(getPeripheralIndices.get(i)[0] == destinationRow & getPeripheralIndices.get(i)[1] == destinationCol )
            {
                nextRow = getPeripheralIndices.get(i)[0];
                nextCol = getPeripheralIndices.get(i)[1];
                this.map.mapArray[currentRow][currentCol] = 3;
                currentRow = nextRow;
                currentCol = nextCol;
                this.map.mapArray[currentRow][currentCol] = 2;
                atDestination = true;
                return;
            }
        }

        for (int i = 0; i < getPeripheralIndices.size(); i++) {
            double newDistance = distance(getPeripheralIndices.get(i)[0], getPeripheralIndices.get(i)[1], destinationRow, destinationCol);
            if (newDistance <= currentLowestDistance) {
                nextRow = getPeripheralIndices.get(i)[0];
                nextCol = getPeripheralIndices.get(i)[1];
                currentLowestDistance = newDistance;
            }
        }

        // free up the old rover location
        this.map.mapArray[currentRow][currentCol] = 3;
        // move the rover to new location
        currentRow = nextRow;
        currentCol = nextCol;
        this.map.mapArray[currentRow][currentCol] = 2;

    }

    double distance(int x1, int y1, int x2, int y2)
    {
        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }

    ArrayList<Integer[]> getAccessiblePeripheralIndices()
    {
        ArrayList<Integer[]> peripheralIndices = new ArrayList<>();
        ArrayList<Integer[]> results = new ArrayList<>();

        peripheralIndices.add(new Integer[]{currentRow-1, currentCol-1});
        peripheralIndices.add(new Integer[]{currentRow-1, currentCol});
        peripheralIndices.add(new Integer[]{currentRow-1, currentCol+1});
        
        peripheralIndices.add(new Integer[]{currentRow, currentCol-1});
        peripheralIndices.add(new Integer[]{currentRow, currentCol+1});
        
        peripheralIndices.add(new Integer[]{currentRow+1, currentCol-1});
        peripheralIndices.add(new Integer[]{currentRow+1, currentCol});
        peripheralIndices.add(new Integer[]{currentRow+1, currentCol+1});

        for(int i =0;i<8; i++)
        {
            if(this.map.getCellStatus(peripheralIndices.get(i)[0], peripheralIndices.get(i)[1]) == 0 // if the cell is free to move
                    & this.map.getCellStatus(peripheralIndices.get(i)[0], peripheralIndices.get(i)[1]) != 3) // if the cell hasn't been visited yet
                results.add(peripheralIndices.get(i));
        }

        return results;
    }



}

