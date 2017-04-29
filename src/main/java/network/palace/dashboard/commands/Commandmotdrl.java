package network.palace.dashboard.commands;

import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;
import network.palace.dashboard.handlers.ChatColor;
import network.palace.dashboard.handlers.MagicCommand;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.handlers.Rank;
import network.palace.dashboard.packets.dashboard.PacketConnectionType;
import network.palace.dashboard.packets.dashboard.PacketUpdateMOTD;
import network.palace.dashboard.server.DashboardSocketChannel;
import network.palace.dashboard.server.WebSocketServerHandler;

/**
 * Created by Marc on 8/26/16
 */
public class Commandmotdrl extends MagicCommand {

    public Commandmotdrl() {
        super(Rank.KNIGHT);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        player.sendMessage(ChatColor.GREEN + "Loading MOTD from file...");
        dashboard.loadMOTD();
        player.sendMessage(ChatColor.GREEN + "MOTD Loaded! Notifying Bungees...");
        PacketUpdateMOTD packet = new PacketUpdateMOTD(dashboard.getMotd(), dashboard.getMotdmaintenance(),
                dashboard.getInfo());
        for (Object o : WebSocketServerHandler.getGroup()) {
            DashboardSocketChannel dash = (DashboardSocketChannel) o;
            if (!dash.getType().equals(PacketConnectionType.ConnectionType.BUNGEECORD)) {
                continue;
            }
            dash.send(packet);
        }
        player.sendMessage(ChatColor.GREEN + "All Bungees have been notified!");
    }
}
