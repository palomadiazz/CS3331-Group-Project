import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class Log {
    protected File log;

    public Log(File file){
        this.log = file;
    }

    public String getDate(){
        LocalDateTime date = LocalDateTime.now();  
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
        
        String formattedDate = date.format(format);  
        return "["+formattedDate+"] ";  
    }

    public void writeToLog(String in){
        try {
        FileWriter myWriter = new FileWriter(log, true);
        myWriter.write(getDate()+in+"\n");
        myWriter.close();
        } catch (IOException e) {
        System.out.println("Failed to write to log.");
        }

    }

}
