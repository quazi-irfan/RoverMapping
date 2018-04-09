import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**         [row][column]
 *          R 0 0 0 0
 *          0 1 0 0 0
 *          0 0 1 1 0
 *          0 0 1 0 0
 *          0 0 0 0 D
 *
 *      0 freeSpace
 *      1 obstacle
 *      2 rover
 *      3 destination
 *      4 beanbag
 *      5 scanned
 */
public class Map
{
    public byte[][] mapArray;
    public int row, col;

    Map(int row, int col)
    {
        this.row = row;
        this.col = col;

        mapArray = new byte[this.row][this.col]; // all values in the array is set to zero
    }

    public void setCellStatus(int row, int col, byte cellStatus)
    {
        if(row >= 0 & row < this.row & col >= 0 & col < this.col)
            mapArray[row][col] = cellStatus;
    }

    public byte getCellStatus(int row, int col)
    {
        if(!(row < 0 || row >= this.row || col < 0 || col >= this.col))
            return mapArray[row][col];

        return Byte.MIN_VALUE; //Execution should not reach here
    }

    void populateMapFromFile(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("map"));
        String mapLine;
        while( (mapLine = reader.readLine()) != null)
        {
            String[] mapIndexAndValue = mapLine.split(" ");
            setCellStatus(Integer.valueOf(mapIndexAndValue[0]),Integer.valueOf(mapIndexAndValue[1]),Byte.valueOf(mapIndexAndValue[2]));
        }
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0; i<this.row; i++)
            stringBuilder.append(Arrays.toString(mapArray[i])).append("\n");

        return stringBuilder.toString();
    }

    public void resetAll()
    {
        mapArray = new byte[this.row][this.col];
    }

    public boolean isAccessible(int row, int col)
    {
        // if the cell value is 0(Free space) or 99(Rover end location) the location is accessable
        if(getCellStatus(row, col) == MapEnum.FREESPACE.getValue()
                | getCellStatus(row, col) == MapEnum.ROVER_DESTINATION.getValue())
            return true;

        // Otherwise the location is not accessible
        else
            return false;
    }

    public void clearNSEW()
    {
        for(int i = 0; i<row; i++)
            for(int j = 0; j<col; j++)
                if(getCellStatus(i, j) == MapEnum.NORTH.getValue() ||
                        getCellStatus(i, j) == MapEnum.SOUTH.getValue() ||
                        getCellStatus(i, j) == MapEnum.EAST.getValue() ||
                        getCellStatus(i, j) == MapEnum.WEST.getValue())
                {
                    setCellStatus(i, j, MapEnum.FREESPACE.getValue());
                }
    }
}


