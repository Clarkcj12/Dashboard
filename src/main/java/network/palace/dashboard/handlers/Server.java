package network.palace.dashboard.handlers;

import lombok.Getter;
import lombok.Setter;
import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;

import java.util.UUID;

/**
 * Created by Marc on 5/29/16
 */
public class Server {
    private UUID uuid = UUID.randomUUID();
    private String name;
    private String address;
    private int port;
    private boolean park;
    private int count;
    private String serverType;
    private boolean online = false;
    @Getter @Setter private boolean inventory = false;

    public Server(String name, String address, int port, boolean park, int count, String serverType) {
        this.name = name;
        this.address = address;
        this.port = port;
        this.park = park;
        this.count = count;
        this.serverType = serverType;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public boolean isPark() {
        return park;
    }

    public int getCount() {
        return count;
    }

    public String getServerType() {
        return serverType;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void changeCount(int i) {
        this.count += i;
    }

    public void emptyServer() {
        Server s = null;
        Dashboard dashboard = Launcher.getDashboard();
        for (Server server : dashboard.getServerUtil().getServers()) {
            if (server.getUniqueId().equals(getUniqueId())) {
                continue;
            }
            if (server.getServerType().equalsIgnoreCase("hub")) {
                if (s == null) {
                    s = server;
                    continue;
                }
                if (server.getCount() < s.getCount()) {
                    s = server;
                }
            }
        }
        for (Player tp : dashboard.getOnlinePlayers()) {
            if (tp.getServer().equals(getName())) {
                dashboard.getServerUtil().sendPlayer(tp, s.getName());
            }
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}