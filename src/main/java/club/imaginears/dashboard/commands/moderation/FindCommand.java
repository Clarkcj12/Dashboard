package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class FindCommand extends DashboardCommand {

    public FindCommand() {
        super(Rank.TRAINEE);
        tabCompletePlayers = true;
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/find [Player]");
            return;
        }
        Player tp = Launcher.getDashboard().getPlayer(args[0]);
        if (tp == null) {
            player.sendMessage(ChatColor.RED + args[0] + " is not online!");
            return;
        }
        player.sendMessage(ChatColor.BLUE + tp.getUsername() + " is on the server " + ChatColor.GOLD + tp.getServer());
    }
}