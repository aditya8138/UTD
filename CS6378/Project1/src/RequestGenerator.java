import java.util.Random;

/**
 * Created by hanlin on 2/22/17.
 */
public class RequestGenerator implements Runnable {
    private Node node;
    private long timeInterval;

    public RequestGenerator(Node node) {
        this.node = node;
    }

    @Override
    public void run() {
        synchronized (this) {
            while (this.node.getPhase() < 3 && this.node.getCriticalSectionCounter() < 40) {
                if ((this.node.getPhase() == 1) || !this.node.isOdd_even()) {
                    Random rn = new Random();
                    timeInterval = 50 + rn.nextInt(50);
                } else {
                    Random rn = new Random();
                    timeInterval = 450 + rn.nextInt(50);
                }
                try {
                    while (this.node.isExecutingCriticalZone() || this.node.getCurrentRequestTimestamp() != -1) {
                        wait(1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    wait(timeInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                this.node.getLogicalClock().logicalTimeIncrease(node.getId());
                this.node.setCurrentRequestTimestamp();
                this.node.setCurrentRequestRealTimestamp();
                this.node.resetPendingAckNum();
                this.node.setMessageList();
            }
        }
    }
}
