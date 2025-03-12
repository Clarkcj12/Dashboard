package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.packets.dashboard.PacketUpdateMOTD;
import club.imaginears.dashboard.server.DashboardSocketChannel;
import club.imaginears.dashboard.server.WebSocketServerHandler;

/**
 * Created by Marc on 8/26/16
 */
public class MotdReloadCommand extends DashboardCommand {

    public MotdReloadCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        player.sendMessage(ChatColor.GREEN + "Loading MOTD from file...");
        dashboard.loadMOTD();
        player.sendMessage(ChatColor.GREEN + "MOTD Loaded! Notifying Bungees...");
        PacketUpdateMOTD packet = new PacketUpdateMOTD(dashboard.getMotd(), dashboard.getMotdMaintenance(),
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
