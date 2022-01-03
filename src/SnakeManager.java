import java.util.ArrayList;

public class SnakeManager {
    SnakeManager()
    {

    }

    public static void predictGrowth(ArrayList<Snake> snakes,
        Pair normalApple,
        Pair powerApple)
    {
        ArrayList<Pair> normalAppleNeighbours =
            getAppleNeighbours(normalApple);
        ArrayList<Pair> powerAppleNeighbours = 
            getAppleNeighbours(powerApple);
        
            for (Snake s : snakes)
            {
                for (int i = 0; i < 4; i++)
                {
                    if (s.getState() == State.DEAD)
                    {
                        continue;
                    }
                    // Remove this trial block
                    // Fix the issue globally
                    // The issue appears when we dont know where the invisible snake is
                    try {
                        if (s.getCoords().get(0).
                        equals(normalAppleNeighbours.get(i)))
                        {
                            s.startGrowth();
                        }
                        else if (s.getCoords().get(0).
                        equals(powerAppleNeighbours.get(i)))
                        {
                            s.startGrowth();
                        }
                    } catch (IndexOutOfBoundsException e)
                    {
                        continue;
                    }
                    
            
                }
            }
    }

    public static void runAStar(
        int w, int h,
        ArrayList<Snake> snakes,
        int mySnakeNum,
        Pair normalApple,
        Pair powerApple)
    {
        boolean oneIsInvisible = false;
        Snake invisibleSnake = null;
        for (int i = 0; i < snakes.size(); i++)
        {
            if (snakes.get(i).getState() == State.INVISIBLE &&
                i != mySnakeNum)
            {
                oneIsInvisible = true;
                invisibleSnake = snakes.get(i);
                break;
            }
        }

        boolean invisibleMightBeClose = false;
        if (oneIsInvisible)
        {
            try
            {
                int mySnake2Inv = 
                    snakes.get(mySnakeNum).getCoords().get(0).
                    distanceFrom(invisibleSnake.getPosInvisible());
                if (mySnake2Inv < invisibleSnake.getTimeInvisible())
                {
                    invisibleMightBeClose = true;
                }
                
            } catch (IndexOutOfBoundsException e)
            {

            }

            
        }
        int move, moveCost;
        if (!invisibleMightBeClose)
        {
            ShortestAStar a1 = new ShortestAStar(
                new Matrix(w, h),
                snakes,
                mySnakeNum,
                normalApple, false);

            move = a1.getMove();
            moveCost = a1.getMoveCost();
            
            ShortestAStar a2 = new ShortestAStar(
                new Matrix(w, h),
                snakes,
                mySnakeNum,
                powerApple, false);
            
            if (a2.getMoveCost() < a1.getMoveCost())
            {
                move = a2.getMove();
                moveCost = a2.getMoveCost();
            }
        }
        else 
        {
            move = -1; 
            moveCost = Integer.MAX_VALUE;
        }  
        int myCoordsSize = snakes.get(mySnakeNum).getCoords().size();
        Pair myTail = snakes.get(
            mySnakeNum).getCoords().get(
                myCoordsSize - 1);
        
        // Chase tail
        ShortestAStar a3 = new ShortestAStar(
            new Matrix(w, h),
            snakes,
            mySnakeNum,
            myTail, 
            true);
        
        if (moveCost == Integer.MAX_VALUE)
        {
            move = a3.getMove();
            moveCost = a3.getMoveCost();
        }

        // Fill up space
        if (moveCost == Integer.MAX_VALUE)
        {
            LongestAStar a4 = new LongestAStar(
                snakes,
                new Matrix(w, h), 
                mySnakeNum);
            move = a4.getMove();
        }
        

        System.out.println(move);
    }

    

    // WARNING!!! this does not check whether the neighbours
    // are on the board
    // This just returns a list of neighbours for the given
    // node without any checks
    private static ArrayList<Pair> getAppleNeighbours(Pair apple)
    {
        Pair left = new Pair(
            apple.x - 1, 
            apple.y);
        Pair right = new Pair(
            apple.x + 1, 
            apple.y);
        Pair up = new Pair(
            apple.x, 
            apple.y - 1);
        Pair down = new Pair(
            apple.x, 
            apple.y + 1);
        
        ArrayList<Pair> neighbours = new ArrayList<>();
        neighbours.add(left);
        neighbours.add(right);
        neighbours.add(up);
        neighbours.add(down);
        return neighbours;
    }
}
