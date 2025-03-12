package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.packets.dashboard.PacketLink;

/**
 * Created by Marc on 9/27/16
 */
public class BugCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        PacketLink packet = new PacketLink(player.getUniqueId(), "https://palnet.us/bugreport", "Click to report a bug",
                ChatColor.YELLOW, true);
        player.send(packet);
    }
}