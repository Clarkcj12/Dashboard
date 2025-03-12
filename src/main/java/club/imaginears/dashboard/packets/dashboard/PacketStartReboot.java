package club.imaginears.dashboard.packets.dashboard;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 8/20/16
 */
public class PacketStartReboot extends BasePacket {

    public PacketStartReboot() {
        this.id = PacketID.Dashboard.STARTREBOOT.getID();
    }

    public PacketStartReboot fromJSON(JsonObject obj) {
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