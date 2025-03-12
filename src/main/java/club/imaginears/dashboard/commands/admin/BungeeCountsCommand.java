package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.server.DashboardSocketChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Marc on 6/10/17.
 */
public class BungeeCountsCommand extends DashboardCommand {

    public BungeeCountsCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        List<DashboardSocketChannel> list = Dashboard.getChannels(PacketConnectionType.ConnectionType.BUNGEECORD);
        HashMap<UUID, Integer> counts = new HashMap<>();
        for (Player p : Launcher.getDashboard().getOnlinePlayers()) {
            UUID bid = p.getBungeeID();
            if (counts.containsKey(bid)) {
                counts.put(bid, counts.get(bid) + 1);
            } else {
                counts.put(bid, 1);
            }
        }
        player.sendMessage(ChatColor.GREEN + "BungeeCord Player Counts:");
        for (Map.Entry<UUID, Integer> entry : counts.entrySet()) {
            DashboardSocketChannel channel = null;
            for (DashboardSocketChannel c : list) {
                if (c.getBungeeID().equals(entry.getKey())) {
                    channel = c;
                    break;
                }
            }
            if (channel != null)
                player.sendMessage(ChatColor.GREEN + "Bungee (" + channel.getServerName() + "): " + counts.get(channel.getBungeeID()));
        }
    }
}
