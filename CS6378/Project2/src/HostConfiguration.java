/**
 * Created by hanlin on 3/28/17.
 */
public class HostConfiguration {
    private Integer port;
    private String hostname;
    private Integer id;
    private Integer serverNum;
    private Integer clientNum;
    private Integer mode;

    public HostConfiguration(Integer port, String hostname, Integer id, Integer serverNum, Integer clientNum, Integer mode) {
        this.port = port;
        this.hostname = hostname;
        this.id = id;
        this.serverNum = serverNum;
        this.clientNum = clientNum;
        this.mode = mode;
    }

    public Integer getPort() {
        return port;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getId() {
        return id;
    }

    public Integer getServerNum() {
        return serverNum;
    }

    public Integer getClientNum() {
        return clientNum;
    }

    public Integer getMode() {
        return mode;
    }

    @Override
    public String toString() {
        return "HostConfiguration{" +
                "port=" + port +
                ", hostname='" + hostname + '\'' +
                ", id=" + id +
                ", serverNum=" + serverNum +
                ", clientNum=" + clientNum +
                ", mode=" + mode +
                '}';
    }
}
