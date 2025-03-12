package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.bungee.PacketUpdateBungeeConfig;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.server.DashboardSocketChannel;
import club.imaginears.dashboard.server.WebSocketServerHandler;

public class BConfigCommand extends DashboardCommand {

    public BConfigCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        PacketUpdateBungeeConfig packet = new PacketUpdateBungeeConfig();
        for (Object o : WebSocketServerHandler.getGroup()) {
            DashboardSocketChannel dash = (DashboardSocketChannel) o;
            if (!dash.getType().equals(PacketConnectionType.ConnectionType.BUNGEECORD)) {
                continue;
            }
            dash.send(packet);
        }
        player.sendMessage(ChatColor.GREEN + "Request sent to update bungee configs!");
    }
}