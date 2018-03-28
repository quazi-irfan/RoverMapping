import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
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

        mapArray = new byte[this.row][this.col];
    }

    public void setCellStatus(int row, int col, byte cellStatus)
    {
        if(row >= 0 & row < this.row & col >= 0 & col < this.col)
            mapArray[row][col] = cellStatus;
    }

    public byte getCellStatus(int row, int col)
    {
        // return obstacle cell status if the index is out of bound
        if(row < 0 || row >= this.row || col < 0 || col >= this.col)
        {
//            System.out.println("Get OUT OF BOUND: row " + row + " column " + col );
//            System.exit(1);
            return Byte.MIN_VALUE; // unreachable statement
        }
        // or return cell status
        else
            return mapArray[row][col];
    }

    void populateMapFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("map"));
        String mapLine;
        while( (mapLine = reader.readLine()) != null)
        {
            String[] mapIndexAndValue = mapLine.split(" ");
            setCellStatus(
                    Integer.valueOf(mapIndexAndValue[0]),
                    Integer.valueOf(mapIndexAndValue[1]),
                    Byte.valueOf(mapIndexAndValue[2]));
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0; i<this.row; i++)
            stringBuilder.append(Arrays.toString(mapArray[i])).append("\n");

        return stringBuilder.toString();
    }

    public void resetAll()
    {
        mapArray = new byte[this.row][this.col];
    }

    public void clearPlayerAndPath()
    {
        for(int i = 0; i<this.row; i++)
            for(int j = 0; j<this.col; j++)
                if(getCellStatus(i, j) == (byte)3 || getCellStatus(i, j) == (byte)2)
                    setCellStatus(i, j, (byte)0);
    }
}


