package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class MsgToggleCommand extends DashboardCommand {

    public MsgToggleCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        player.setReceiveMessages(!player.canRecieveMessages());
        String modifier = player.canRecieveMessages() ? ChatColor.GREEN + "enabled " : ChatColor.RED + "disabled ";
        player.sendMessage(ChatColor.YELLOW + "You have " + modifier + ChatColor.YELLOW +
                "receiving private messages!");
    }
}