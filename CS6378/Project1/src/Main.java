/**
 * Created by hanlin on 2/17/17.
 */
public class Main {
    public static void main(String[] args) {
        // write your code here
        Node node = new Node(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        node.init();
        node.start();
    }
}
