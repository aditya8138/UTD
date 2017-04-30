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
            Object inObject = message.getContent();
            switch (message.getMessageType()) {
                case INIT_CONNECTION:
                    System.err.println("Found unprocessed INIT message.");
                    break;
                case INIT_VOTE:
                    Node.getInstance().voteDataInitialize();
                    System.out.println(Node.getInstance().getLocalVoteData() + "\n>");
                    break;
                case VOTE_REQ:
                    Node.getInstance().replyVote(message.getSenderID());
                    break;
                case VOTE_REQ_NACK:
                case VOTE_REQ_ACK:
                    if (inObject == null || inObject instanceof VoteData)
                        Node.getInstance().addVote(message.getSenderID(), (VoteData)inObject);
                    else
                        System.err.println(message.getMessageType() + " message does not contain VoteData.");
                    break;
                case ABORT:
                    Node.getInstance().cancel();
                    break;
                case COMMIT:
                    if (inObject instanceof VoteData) {
                        Node.getInstance().commit((VoteData) inObject);
                        System.out.println(Node.getInstance().getLocalVoteData());
                    } else
                        System.err.println("COMMIT message does not contain VoteData.");
                    break;
            }
        }
    }
}
