package club.imaginears.dashboard.packets.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Innectic
 * @since 6/10/2017
 */
@AllArgsConstructor
public enum InventoryType {
    BACKPACK(0), LOCKER(1), BASE(2), BUILD(3);
    @Getter private int id;

    public static InventoryType fromId(int id) {
        for (InventoryType type : values()) {
            if (type.getId() == id) return type;
        }
        return LOCKER;
    }
}
