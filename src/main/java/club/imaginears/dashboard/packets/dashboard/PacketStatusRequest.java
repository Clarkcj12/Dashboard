package club.imaginears.dashboard.packets.dashboard;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 6/15/15
 */
public class PacketStatusRequest extends BasePacket {

    public PacketStatusRequest() {
        this.id = PacketID.Dashboard.STATUSREQUEST.getID();
    }

    public PacketStatusRequest fromJSON(JsonObject obj) {
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