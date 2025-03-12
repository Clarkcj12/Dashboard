package club.imaginears.dashboard.packets.dashboard;

import com.google.gson.JsonObject;
import club.imaginears.dashboard.packets.BasePacket;
import club.imaginears.dashboard.packets.PacketID;

import java.util.UUID;

/**
 * Created by Marc on 1/15/17.
 */
public class PacketUpdateEconomy extends BasePacket {
    private UUID uuid;

    public PacketUpdateEconomy() {
        this(null);
    }

    public PacketUpdateEconomy(UUID uuid) {
        this.id = PacketID.Dashboard.UPDATEECONOMY.getID();
        this.uuid = uuid;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public PacketUpdateEconomy fromJSON(JsonObject obj) {
        this.id = obj.get("id").getAsInt();
        try {
            this.uuid = UUID.fromString(obj.get("uuid").getAsString());
        } catch (Exception e) {
            this.uuid = null;
        }
        return this;
    }

    @Override
    public JsonObject getJSON() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", this.id);
        obj.addProperty("uuid", this.uuid.toString());
        return obj;
    }
}
