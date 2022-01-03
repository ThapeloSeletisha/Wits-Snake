import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    int[][] m;

    Matrix(int width, int height)
    {
        m = new int[height][width];
        for (int i = 0; i < height; i++)
        {
            Arrays.fill(m[i], 0);
        }
    }

    public int get(int x, int y)
    {
        return m[y][x];
    }

    public int get(Pair position)
    {
        return get(position.x, position.y);
    }

    void markClosed(int x, int y)
    {
        m[y][x] = -1;
    }

    @Override
    public String toString()
    {
        String output = "";
        for (int i = 0; i < 50; i++)
        {
            for (int j = 0; j < 50; j++)
            {
                output += (String.format("%03d",m[i][j]) + " ");
            }
            output += "\n";
        }
        return output;
    }

    boolean setBarriers(ArrayList<Snake> snakes,
                        int snakeNum)
    {
        for (int i = 0; i < snakes.size(); i++)
        {
            try 
            {
                ArrayList<Pair> coords = snakes.get(i).getCoords();
                if (i != snakeNum &&
                    snakes.get(i).getState() !=
                    State.INVISIBLE)
                {
                    setHead(coords.get(0));
                }
                for (int j = 1; j < coords.size(); j++)
                {
                    setBarrierLine(coords.get(j - 1), coords.get(j));
                }
                Pair tail = coords.get(coords.size() - 1);
                if (!snakes.get(i).isGrowing())
                {
                    m[tail.y][tail.x] = 0;
                }

            } catch (IndexOutOfBoundsException e)
            {
                continue;
            }
        }

        return false;
    }



    private void setBarrierLine(Pair p1, Pair p2)
    {
        if (p1.x == p2.x)
        {
            for (int i = Math.min(p1.y, p2.y); 
                 i <= Math.max(p1.y, p2.y); i++)
            {
                m[i][p1.x] = 100;
            }
        }
        else if (p1.y == p2.y)
        {
            for (int i = Math.min(p1.x, p2.x);
                 i <= Math.max(p1.x, p2.x); i++)
            {
                m[p1.y][i] = 100;
            }
        }
    }

    private void setHead(Pair head)
    {
        try {
            m[head.y][head.x + 1] = 33;
        } catch (ArrayIndexOutOfBoundsException e){
            // e.printStackTrace();
        }
        try {
            m[head.y][head.x - 1] = 33;
        } catch (ArrayIndexOutOfBoundsException e){
            // e.printStackTrace();
        }  
        try {
            m[head.y + 1][head.x] = 33;
        } catch (ArrayIndexOutOfBoundsException e){
            // e.printStackTrace();
        } 
        try {
            m[head.y - 1][head.x] = 33;
        } catch (ArrayIndexOutOfBoundsException e){
            // e.printStackTrace();
        } 
    }

    public static void main(String[] args) {
        // Matrix m = new Matrix(50, 50);

        // Snake s1, s2, s3, s4;
        // s1 = new Snake("alive 26 2 10,12 15,12 15,7 5,7 5,2");
        // s2 = new Snake("dead 6 6 14,17 19,17");
        // s3 = new Snake("alive 2 1 31,13 31,14");
        // s4 = new Snake("invisible 17 1 9 21,14 21,14 15,14 15,13");

        // ArrayList<Snake> snakes = new ArrayList<>();
        // snakes.add(s1);
        // snakes.add(s2);
        // snakes.add(s3);
        // snakes.add(s4);
        // m.setBarriers(snakes, 0);

        // Log.init();
        // Log.write(m);
        // Log.close();
    }


}
