package ak.minigunquest;

import ak.minigunquest.util.References;

import java.io.DataOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Aleksander on 22/03/2015.
 */
public class Logger {

    public enum LoggingLevel {
        ERROR,
        DEBUG,
        SEVERE
    }
    PrintStream stream;

    public Logger(PrintStream stream){
        this.stream = stream;
    }

    public void log(LoggingLevel level, String message){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date resultDate = new Date(System.currentTimeMillis());

        stream.println(resultDate + " [" + level.name() + "] [" + References.gameName + "] : " + message);
    }
}
