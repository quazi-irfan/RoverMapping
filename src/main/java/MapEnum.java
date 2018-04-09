public enum MapEnum
{
    FREESPACE(0), OBSTACLE(1), ROVER_START_LOC(98), ROVER_DESTINATION(99), NORTH(101), SOUTH(102), EAST(103), WEST(104);

    private int value;

    MapEnum(int value)
    {
        this.value = value;
    }

    byte getValue()
    {
        return (byte)this.value;
    }

}
