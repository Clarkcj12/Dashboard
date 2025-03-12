package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.packets.dashboard.PacketLink;

public class SocialCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        player.sendMessage(ChatColor.YELLOW + "" + ChatColor.BOLD + "Palace Network Social Links:");
        player.send(new PacketLink(player.getUniqueId(), "https://forums.palace.network", "Forums: https://forums.palace.network",
                ChatColor.GREEN, false, false));
        player.send(new PacketLink(player.getUniqueId(), "https://palnet.us/Discord",
                "Discord: https://palnet.us/Discord", ChatColor.YELLOW, false, false));
        player.send(new PacketLink(player.getUniqueId(), "https://twitter.com/PalaceNetwork",
                "Twitter: @PalaceNetwork", ChatColor.AQUA, false, false));
        player.send(new PacketLink(player.getUniqueId(), "https://youtube.com/MCMagicParks",
                "YouTube: https://youtube.com/MCMagicParks", ChatColor.RED, false, false));
        player.send(new PacketLink(player.getUniqueId(), "https://facebook.com/PalaceNetworkMC",
                "Facebook: https://facebook.com/PalaceNetworkMC", ChatColor.BLUE, false, false));
        player.send(new PacketLink(player.getUniqueId(), "https://instagram.com/palacenetwork",
                "Instagram: https://instagram.com/palacenetwork", ChatColor.LIGHT_PURPLE, false, false));
    }
}