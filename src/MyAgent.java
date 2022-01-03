import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
// import java.util.Random;
import za.ac.wits.snake.DevelopmentAgent;
// import java.lang.Long;

public class MyAgent extends DevelopmentAgent {

    public static void main(String args[]) {

        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    // private static long secondsToMilli(long seconds)
    // {
    //     return seconds * ((long)Math.pow(10, 3));
    // }

    // private static long minutesToMilli(long minutes)
    // {
    //     return 60 * secondsToMilli(minutes);
    // }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            int w, h;
            String[] gameDetails = initString.split(" ");
            w = Integer.parseInt(gameDetails[1]);
            h = Integer.parseInt(gameDetails[2]);
            ArrayList<Snake> snakes = new ArrayList<>();
            for (int i = 0; i < nSnakes; i++)
            {
                snakes.add(new Snake());
            }
            // Log.init();
            // gvuhvh
            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                Pair apple1 = new Pair(line);
                Pair apple2 = new Pair(br.readLine());
                //do stuff with apples

                
                int mySnakeNum = Integer.parseInt(br.readLine());
                // Log.write(mySnakeNum);

                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    snakes.get(i).addDetails(snakeLine);
                    //do stuff with other snakes
                }
                //finished reading, calculate move
                SnakeManager.predictGrowth(snakes, apple2, apple1);
                SnakeManager.runAStar(
                    w, h, 
                    snakes, mySnakeNum, 
                    apple2, apple1);

                // int move = new Random().nextInt(4);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}