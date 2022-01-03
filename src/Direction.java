public enum Direction {
    NORTH, EAST, WEST, SOUTH;

    public static int getMoveFromDirection(Direction d)
    {
        if (d == null)
        {
            return 5;
        }
        switch (d)
        {
        case NORTH:
            return 0;
        case SOUTH:
            return 1;
        case WEST:
            return 2;
        case EAST:
            return 3;
        }

        return 5;
    }
}


