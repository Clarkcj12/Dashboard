package club.imaginears.dashboard.packets.park;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 9/18/16
 */
public class PacketRefreshWarps extends BasePacket {
    private String server;

    public PacketRefreshWarps() {
        this("");
    }

    public PacketRefreshWarps(String server) {
        this.id = PacketID.Park.REFRESHWARPS.getID();
        this.server = server;
    }

    public String getServer() {
        return server;
    }

    public PacketRefreshWarps fromJSON(JsonObject obj) {
        this.id = obj.get("id").getAsInt();
        this.server = obj.get("server").getAsString();
        return this;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", this.id);
        obj.addProperty("server", this.server);
        return obj;
    }
}