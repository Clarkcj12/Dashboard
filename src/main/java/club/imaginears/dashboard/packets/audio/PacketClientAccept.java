package club.imaginears.dashboard.packets.audio;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

/**
 * Created by Marc on 6/15/15
 */
public class PacketClientAccept extends BasePacket {
    private String servername = "";

    public PacketClientAccept(String servername) {
        this.id = PacketID.CLIENT_ACCEPTED.getID();

        this.servername = servername;
    }

    public PacketClientAccept fromJSON(JsonObject obj) {
        try {
            this.servername = obj.get("servername").getAsString();
        } catch (Exception e) {
            return null;
        }
        return this;
    }

    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        try {
            obj.addProperty("id", this.id);
            obj.addProperty("servername", this.servername);
        } catch (Exception e) {
            return null;
        }
        return obj;
    }
}