package club.imaginears.dashboard.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import club.imaginears.dashboard.packets.inventory.Resort;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Marc on 6/10/17.
 */
@AllArgsConstructor
public class InventoryCache {
    @Getter private UUID uuid;
    @Getter private HashMap<Resort, ResortInventory> resorts;

    public void setInventory(Resort resort, ResortInventory inv) {
        resorts.put(resort, inv);
    }
}
