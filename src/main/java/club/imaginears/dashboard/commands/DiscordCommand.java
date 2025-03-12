package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.discordSocket.DiscordUserInfo;
import club.imaginears.dashboard.discordSocket.SocketConnection;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.packets.dashboard.PacketLink;

/**
 * @author Innectic
 * @since 2/18/2017
 */
public class DiscordCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length < 2) {
            PacketLink packet = new PacketLink(player.getUniqueId(), "https://palnet.us/Discord",
                    "Click for more information about Discord!", ChatColor.YELLOW, true);
            player.send(packet);
        } else if (args.length >= 2 && args[0].equals("link")) {
            StringBuilder fullName = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                fullName.append(args[i]);
            }
            if (fullName.toString().contains("#") && fullName.toString().matches("(.*)#(\\d+)")) {
                DiscordUserInfo userInfo = new DiscordUserInfo(fullName.toString(), player.getUsername(), player.getUniqueId().toString(), player.getRank().toString());
                if (SocketConnection.sendLink(userInfo)) {
                    player.sendMessage(ChatColor.GREEN + "");
                }
            } else {
                player.sendMessage(ChatColor.DARK_RED + "Please specify a valid Discord ID!");
            }
        } else {
            PacketLink packet = new PacketLink(player.getUniqueId(), "https://palnet.us/Discord",
                    "Click for more information about Discord!", ChatColor.YELLOW, true);
            player.send(packet);
        }
    }
}
