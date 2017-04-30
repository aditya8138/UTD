package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIEngine implements Runnable {
    CLIEngine() {
    }

    public void run() {
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

                if (line.equalsIgnoreCase("display connection")) {
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
