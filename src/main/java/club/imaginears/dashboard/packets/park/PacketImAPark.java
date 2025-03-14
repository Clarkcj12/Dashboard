package club.imaginears.dashboard.packets.park;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 6/11/17.
 */
public class PacketImAPark extends BasePacket {

    public PacketImAPark() {
        this.id = PacketID.Park.IMAPARK.getID();
    }

    public PacketImAPark fromJSON(JsonObject obj) {
        this.id = obj.get("id").getAsInt();
        return this;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", this.id);
        return obj;
    }
}