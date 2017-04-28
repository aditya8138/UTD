package core;

/**
 * Created by hanlin on 4/27/17.
 */
public class MessageProcessorThread implements Runnable {


    @Override
    public void run() {

        System.out.println("MessageProcessorThread initiated.");
        while (!Node.getInstance().isShutDown()) {
            Message message = Node.getInstance().getMessageQueue().poll();
            if (message == null)
                continue;
            switch (message.getMessageType()) {
                case INIT_CONNECTION:
                    System.err.println("Found unprocessed INIT message.");
                    break;
                case INIT_VOTE:
                    System.out.println("Initializing vote data...");
                    Node.getInstance().voteDataInitialize();
                    System.out.println(Node.getInstance().getVoteData());
                    break;
            }
        }
    }
}
