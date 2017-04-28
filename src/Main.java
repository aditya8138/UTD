import core.Node;

public class Main {

    public static void main(String[] args) {

        char nodeLabel = args.length > 0 ? args[0].toUpperCase().charAt(0) : 'A';
        Node.getInstance().setID(nodeLabel);
        Node.getInstance().start();
    }
}
