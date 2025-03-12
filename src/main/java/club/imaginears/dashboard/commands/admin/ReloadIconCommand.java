package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.bungee.PacketServerIcon;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.server.DashboardSocketChannel;

/**
 * Created by Marc on 4/30/17.
 */
public class ReloadIconCommand extends DashboardCommand {

    public ReloadIconCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        player.sendMessage(ChatColor.GREEN + "Calculating base64 hash and notifying bungees...");
        Dashboard dash = Launcher.getDashboard();
        String base64 = dash.getServerIconBase64();
        PacketServerIcon packet = new PacketServerIcon(base64);
        for (DashboardSocketChannel c : Dashboard.getChannels(PacketConnectionType.ConnectionType.BUNGEECORD)) {
            c.send(packet);
        }
        player.sendMessage(ChatColor.GREEN + "All server icons have been updated!");
    }
}
