package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class IPCommand extends DashboardCommand {

    public IPCommand() {
        super(Rank.LEAD);
        tabCompletePlayers = true;
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/ip [Player]");
            return;
        }
        Player tp = Launcher.getDashboard().getPlayer(args[0]);
        if (tp == null) {
            player.sendMessage(ChatColor.RED + "That player wasn't found!");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "IP of " + tp.getUsername() + " is " + tp.getAddress());
    }
}