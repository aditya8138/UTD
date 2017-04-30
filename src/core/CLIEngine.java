package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIEngine implements Runnable {


    CLIEngine() {
    }

    public void run() {
        StringBuilder helpcontext = new StringBuilder();

        helpcontext.append("Usage:\n")
                .append("  <command> [options]\n\n")
                .append("Commands:\n")
                .append("  help                    Show help for commands.\n")
                .append("  init                    Initialize vote data for all connected nodes.\n")
                .append("  display [options]       Display current status.\n")
                .append("                          Available options:\n")
                .append("                              status,\n")
                .append("                              vote,\n")
                .append("                              connection/network.\n")
                .append("  connect [options]       Connect to some nodes.\n")
                .append("  connect [options]       Need to specify the label of the nodes.\n")
                .append("                          Example: connect B C D\n")
                .append("  disconnect [options]    Disconnect with some nodes.\n")
                .append("  disconnect [options]    Need to specify the label of the nodes.\n")
                .append("                          Example: disconnect B C D\n")
                .append("  write                   Write to the object.\n")
                .append("  quit/exit/q             Safe exit.\n");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (!Node.getInstance().isShutDown()) {
            String line;
            System.out.print("\n> ");

            try {
                line = bufferedReader.readLine();
                if (line.equals("quit") || line.equalsIgnoreCase("exit") || line.equals("q")) {
                    bufferedReader.close();
                    Node.getInstance().shutDown();
                    continue;
                }

                if (line.equals("help")) {
                    System.out.println(helpcontext.toString());
                    continue;
                }

                if (line.equals("")) {
                    continue;
                }

                if (line.equalsIgnoreCase("display status")) {
                    System.out.println(Node.getInstance());
                    continue;
                }

                if (line.equalsIgnoreCase("display vote")) {
                    System.out.println(Node.getInstance().getLocalVoteData());
                    continue;
                }

                if (line.equalsIgnoreCase("display connection") ||
                        line.equalsIgnoreCase("display network")) {
                    System.out.print(Node.getInstance().printCommunicationThreads());
                    continue;
                }

                if (line.toLowerCase().startsWith("disconnect")) {
                    Node.getInstance().disconnectNode(line.split(" "));
                    System.out.print(Node.getInstance().printCommunicationThreads());
                    continue;
                }

                if (line.toLowerCase().startsWith("connect")) {
                    System.out.println("Connecting...");
                    Node.getInstance().connectNode(line.split(" "));
                    System.out.print(Node.getInstance().printCommunicationThreads());
                    continue;
                }

                if (line.toLowerCase().startsWith("init")) {
                    System.out.println("Initializing vote data...");
                    Node.getInstance().initiateVoteDataInitialization();
                    System.out.println(Node.getInstance().getLocalVoteData());
                    continue;
                }

                if (line.toLowerCase().startsWith("write")) {
                    System.out.println("Attempting to write...");
                    Node.getInstance().write();
                    System.out.println(Node.getInstance().getLocalVoteData());
                }
            } catch (IOException e) {
                System.err.println("Error while reading line from console. Leaving network...");
                Node.getInstance().shutDown();
                return;
            }
        }
    }

}
