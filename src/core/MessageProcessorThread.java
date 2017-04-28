package core;

/**
 * Created by hanlin on 4/27/17.
 */
public class MessageProcessorThread implements Runnable {


    @Override
    public void run() {
        while (!Node.getInstance().isShutDown()) {
            Message message = Node.getInstance().getMessageQueue().poll();
            if (message == null)
                continue;
            switch (message.getMessageType()) {
                case INIT_CONNECTION:
                    System.err.println("Found unprocessed INIT message.");
                    break;

            }
        }
    }
}
