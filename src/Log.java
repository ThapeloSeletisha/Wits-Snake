import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.io.File;

public class Log {
    private static FileWriter writer = null;
    private static BufferedWriter bufferedWriter = null;

    public static void init()
    {
        if (Objects.isNull(writer) ||
            Objects.isNull(bufferedWriter))
        {
            try 
            {
                writer = new FileWriter("log.txt");
                bufferedWriter = new BufferedWriter(writer);
            } catch (IOException e)
            {
                int x = 0;
            }
        }
    }
    
    public static void write(Object output)
    {
         
        String fileText = "";
        if (Objects.isNull(writer) &&
            Objects.isNull(bufferedWriter))
        {
            try 
            {
                File logFile = 
                new File("log.txt"); 
                Scanner in = new Scanner(logFile);
                while (in.hasNextLine())
                { 
                    fileText += in.nextLine() + "\n";
                }
                fileText = fileText.substring(0, fileText.length()- 1);
                in.close();
            } catch (IOException e)
            {
                int x = 0;
            }
        }
        try
        {
            init();
            bufferedWriter.write(fileText);
            bufferedWriter.newLine();
            bufferedWriter.write(output.toString());
            bufferedWriter.newLine();
            close();
        } catch (IOException e)
        {
            int y = 0;
        }
    }
    private static void close()
    {
        try
        {
            bufferedWriter.close();
            writer.close();
            writer = null;
            bufferedWriter = null;
        } catch (IOException e)
        {
            int x = 0;
        }
        
    }
    


}
