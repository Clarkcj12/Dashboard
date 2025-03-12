package club.imaginears.dashboard.packets.audio;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 2/12/17.
 */
public class PacketHeartbeat extends BasePacket {

    public PacketHeartbeat() {
        this.id = PacketID.HEARTBEAT.getID();
    }

    public PacketHeartbeat fromJSON(JsonObject obj) {
        return this;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id", this.id);
        } catch (Exception e) {
            return null;
        }
        return obj;
    }
}
