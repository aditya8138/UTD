package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hanlin on 4/27/17.
 */
public class CLIEngine implements Runnable {
    public CLIEngine() {
    }

    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        while (!Node.getInstance().isShutDown()) {
            System.out.print("> ");
            String line = null;

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

                if (line.equalsIgnoreCase("display connection")) {
                    System.out.println(Node.getInstance().printCommunicationThreads());
                    continue;
                }

                if (line.toLowerCase().startsWith("disconnect")) {
                    System.out.println("Disconnecting...");
                    Node.getInstance().disconnectNode(line.split(" "));
                    continue;
                }

                if (line.toLowerCase().startsWith("connect")) {
                    System.out.println("Connecting...");
                    Node.getInstance().connectNode(line.split(" "));
                    continue;
                }

                if (line.toLowerCase().startsWith("init")) {
                    System.out.println("Initializing vote data...");
                    Node.getInstance().initiateVoteDataInitialization();
                    continue;
                }

                if (line.toLowerCase().startsWith("write")) {
                    System.out.println("Attempting to write...");
                    Node.getInstance().write();
                    continue;
                }
            } catch (IOException e) {
                System.err.println("Error while reading line from console. Leaving network...");
                Node.getInstance().shutDown();
                return;
            }
        }
    }

}
