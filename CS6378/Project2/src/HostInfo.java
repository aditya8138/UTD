import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hanlin on 3/29/17.
 */
public class HostInfo {
    private static HostInfo ourInstance = new HostInfo();

    private HashMap<Integer, HostConfiguration> serverNodes;

    private HashMap<Integer, HostConfiguration> clientNodes;

    public static HostInfo getInstance() {
        return ourInstance;
    }

    private HostInfo() {
        this.serverNodes = new HashMap<>();
        this.clientNodes = new HashMap<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("all_nodes.cfg"));
            String eachLine = bufferedReader.readLine();
            Integer serverNum, clientNum;
            String[] numSplit = eachLine.split(" ");
            serverNum = Integer.valueOf(numSplit[0]);
            clientNum = Integer.valueOf(numSplit[1]);

            while ((eachLine = bufferedReader.readLine()) != null) {
                String[] split = eachLine.split(" ");
                Integer id = Integer.valueOf(split[0].trim());
                String host = split[1].trim();
                Integer port = Integer.valueOf(split[2].trim());
                Integer mode = Integer.valueOf(split[3].trim());

                if (Integer.valueOf(split[3].trim()) == 0)
                    this.serverNodes.put(id, new HostConfiguration(port, host, id, serverNum, clientNum, mode));
                else
                    this.clientNodes.put(id, new HostConfiguration(port, host, id, serverNum, clientNum, mode));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, HostConfiguration> getServerNodes() {
        return serverNodes;
    }

    public HashMap<Integer, HostConfiguration> getClientNodes() {
        return clientNodes;
    }

    public Integer HashPrimary(Integer n) {
        return n % this.serverNodes.size();
    }
    public Integer HashSecondary(Integer n) {
        return (n + 1) % this.serverNodes.size();
    }
    public Integer HashTertiary(Integer n) {
        return (n + 2) % this.serverNodes.size();
    }

    @Override
    public String toString() {
        StringBuilder info = new StringBuilder("HostInfo[serverNodes = {");

        for (Map.Entry<Integer, HostConfiguration> s: this.serverNodes.entrySet())
            info.append(s);
        info.append("}\nclientNodes = {");
        for (Map.Entry<Integer, HostConfiguration> c: this.clientNodes.entrySet())
            info.append(c);
        info.append("}]");

        return info.toString();
    }
}
