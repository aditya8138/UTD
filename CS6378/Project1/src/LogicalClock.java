/**
 * Created by hanlin on 2/17/17.
 */
public class LogicalClock {

    private long logicalTime;


    private static LogicalClock ourInstance = new LogicalClock();

    public static LogicalClock getInstance() {
        return ourInstance;
    }

    private LogicalClock() {
        this.logicalTime = 0;
    }

    public long getLogicalTime() {
        return logicalTime;
    }

    public synchronized void logicalTimeIncrease(long logicalTimeIncrement){
        this.logicalTime += logicalTimeIncrement;
    }

    public synchronized void logicalTimeIncrease(long logicalTimeIncrement, long otherclock){
        this.logicalTime =  this.logicalTime > otherclock ?
                this.logicalTime + logicalTimeIncrement : otherclock + logicalTimeIncrement;
    }
}
