package club.imaginears.dashboard.packets.dashboard;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marc on 8/25/16
 */
public class PacketServerList extends BasePacket {
    private List<String> servers = new ArrayList<>();

    public PacketServerList() {
        this(new ArrayList<>());
    }

    public PacketServerList(List<String> servers) {
        this.id = PacketID.Dashboard.SERVERLIST.getID();
        this.servers = servers;
    }

    public List<String> getServers() {
        return servers;
    }

    public PacketServerList fromJSON(JsonObject obj) {
        JsonArray list = obj.get("servers").getAsJsonArray();
        for (JsonElement e : list) {
            this.servers.add(e.getAsString());
        }
        return this;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id", this.id);
            Gson gson = new Gson();
            obj.add("servers", gson.toJsonTree(this.servers).getAsJsonArray());
        } catch (Exception e) {
            return null;
        }
        return obj;
    }
}