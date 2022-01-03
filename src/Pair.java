// A class to store the x,y coordinates of the game objects
public class Pair{
    final public int x, y;

    // Sets the coordinates to be off the board
    Pair()
    {
        this.x = -1;
        this.y = -1;
    }

    // Takes in a string representation of a pair
    // in the format "x,y" or "x y"
    Pair(String line)
    {
        String[] coord;
        if (line.contains(","))
        {
            coord = line.split(",");
        }
        else
        {
            coord = line.split(" ");
        }
        
        this.x = Integer.parseInt(coord[0]);
        this.y = Integer.parseInt(coord[1]);
    }

    Pair (int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    // Returns the Manhattan distance between
    // this pair and that pair
    public int distanceFrom(Pair that)
    {
        return Math.abs(this.x - that.x)
              +Math.abs(this.y - that.y);
    }

    // Returns the string representation of a pair
    // in the format "(x,y)"
    @Override
    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    // Determines whether this pair is
    // equal to that pair
    // on the basis this pair's x and y values
    // are equal to that pair's x and y values
    @Override 
    public boolean equals(Object o)
    {
        if (this == o) 
        {
            return true;
        }
        if (o == null || getClass() != o.getClass()) 
        {
            return false;
        }

        Pair that = (Pair) o;
        return (that.x == this.x && that.y == this.y);
    }
    public static Direction getDirection(Pair from, Pair to)
    {
        if (from.x == to.x)
        {
            if (from.y > to.y)
            {
                return Direction.NORTH;
            }
            else if (from.y < to.y)
            {
                return Direction.SOUTH;
            }
            else 
            {
                // System.out.println("Parent coordinate the same as child");
            }
        }
        else if (from.y == to.y)
        {
            if (from.x > to.x)
            {
                return Direction.WEST;
            }
            else if (from.x < to.x)
            {
                return Direction.EAST;
            }
            else 
            {
                // System.out.println("Parent coordinate the same as child");
            }
        }
        // System.out.println("Parent coordinate not in line with child");
        return null;
    }

    // public static void main(String[] args) {
    //     Pair p1, p2;
    //     p1 = new Pair("100,5");
    //     p1 = new Pair(0, 3);
    //     int x = p1.x;
    //     System.out.println(x); 
    // }

}
