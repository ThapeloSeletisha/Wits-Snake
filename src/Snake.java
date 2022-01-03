import java.util.ArrayList;

// This class only stores the details of a snake
public class Snake {

    private ArrayList<Pair> coords;
    private Pair invisibleCoord;
    private int kills, length, timeInvisible,
        timeToGrow;
    private boolean mySnake;
    private State state;

    Snake()
    {
        coords = new ArrayList<>();
        kills = length = 
        timeInvisible = timeToGrow = 0;
        mySnake = false;
        state = null;
        invisibleCoord = null;
    }
    void addDetails(String snakeLine)
    {
        String[] details = snakeLine.split(" ");

        switch(details[0])
        {
        case "alive":
            state = State.ALIVE;
            break;
        case "dead":
            state = State.DEAD;
            break;
        case "invisible":    
            state = State.INVISIBLE;
            break;
        }

        length = Integer.parseInt(details[1]);
        kills = Integer.parseInt(details[2]);
        timeInvisible = -1;

        coords = new ArrayList<>();
        if (state == State.ALIVE)
        {
            for (int i = 3; i < details.length; i++)
            {
                coords.add(new Pair(details[i]));
            }
        }
        else if (state == State.INVISIBLE)
        {
            timeInvisible = Integer.parseInt(details[3]);

            // The remember that the first coordinate
            // is the place where the snake went invisible
            invisibleCoord = new Pair(details[4]);
            for (int i = 5; i < details.length; i++)
            {
                coords.add(new Pair(details[i]));
            }
        }
        mySnake = false;

        if (timeToGrow != 0)
        {
            timeToGrow--;
        }

    }

    public int getKills()
    {
        return kills;
    }

    public int getLength()
    {
        return length;
    }

    public int getTimeInvisible()
    {
        return timeInvisible;
    }

    public boolean isMySnake()
    {
        return mySnake;
    }

    public State getState()
    {
        return state;
    }
    public Pair getPosInvisible()
    {
        return invisibleCoord;
    }

    public Boolean isGrowing()
    {
        return timeToGrow > 0;
    }

    public void setAsMySnake()
    {
        mySnake = true;
    }

    public void startGrowth()
    {
        timeToGrow = 6;
    }

    // Returns the direction of this snake
    // Returns null if the directions is 
    // indeterminate or the snake is dead
    public Direction getDirection()
    {
        Pair head, kink1;
        try
        {
            head = coords.get(0);
            kink1 = coords.get(1);
            if (state == State.ALIVE)
            {
                return getDirection(head, kink1);
            }
            else if (state == State.INVISIBLE)
            {
                try{

                    if (invisibleCoord.equals(head))
                    {
                        return getDirection(head, kink1);
                    }
                    else if (mySnake)
                    {
                        return getDirection(head, kink1);
                    }
                    else 
                    {
                        return null;
                    }

                } catch (Exception e)
                {
                    return null;
                }
            }
        } catch (IndexOutOfBoundsException e)
        {
            return null;
        }
        return null;
    }

    // Given the head coordinate nad the first kink
    // Determines and retruns the direction the snake is going
    // This function is just a helper function for the
    // public getDirection() function which takes no parameters
    private Direction getDirection(Pair head, Pair kink1)
    {
        if (head.x - kink1.x > 0)
        {
            return Direction.EAST;
        }
        else if (head.x - kink1.x < 0)
        {
            return Direction.WEST;
        }
        else if (head.y - kink1.y > 0)
        {
            return Direction.SOUTH;
        }
        else 
        {
            return Direction.NORTH;
        }
    }

    public ArrayList<Pair> getCoords()
    {
        return coords;
    }

    // public static void main(String[] args) {
    //     Snake s1, s2, s3, s4;
    //     s1 = new Snake("alive 26 2 10,12 15,12 15,7 5,7 5,2");
    //     s2 = new Snake("dead 6 6 14,13 19,13");
    //     s3 = new Snake("alive 2 1 12,13 12,14");
    //     s4 = new Snake("invisible 17 1 9 21,14 21,14 15,14 15,13");

    //     ArrayList<Snake> snakes = new ArrayList<>();
    //     snakes.add(s1);
    //     snakes.add(s2);
    //     snakes.add(s3);
    //     snakes.add(s4);
    //     s1.setAsMySnake();

    //     for (Snake s : snakes)
    //     {
    //         System.out.println(s.getDirection());
    //     }
    // }
}


