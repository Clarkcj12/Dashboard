package network.palace.dashboard.commands;

import network.palace.dashboard.handlers.ChatColor;
import network.palace.dashboard.handlers.MagicCommand;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.handlers.Rank;

public class Commandmsgtoggle extends MagicCommand {

    public Commandmsgtoggle() {
        super(Rank.WIZARD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        player.setRecieveMessages(!player.canRecieveMessages());
        String modifier = player.canRecieveMessages() ? ChatColor.GREEN + "enabled " : ChatColor.RED + "disabled ";
        player.sendMessage(ChatColor.YELLOW + "You have " + modifier + ChatColor.YELLOW +
                "receiving private messages!");
    }
}