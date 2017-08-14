import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Integer id = Integer.valueOf(args[0]);
        if (!HostInfo.getInstance().getServerNodes().containsKey(id) &&
                !HostInfo.getInstance().getClientNodes().containsKey(id))
            return;
        int mode = HostInfo.getInstance().getServerNodes().containsKey(id) ? 0 :
                HostInfo.getInstance().getClientNodes().get(id).getMode();

        try {
            switch (mode) {
                case 0:new Server(HostInfo.getInstance().getServerNodes().get(id)).start(); break;
                case 1:new Client(id).start(); break;
                case 2:new Client(id).autostart(); break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
