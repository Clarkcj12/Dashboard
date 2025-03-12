package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.utils.NameUtil;

import java.util.List;

public class NamecheckCommand extends DashboardCommand {

    public NamecheckCommand() {
        super(Rank.TRAINEE);
        tabCompletePlayers = true;
    }

    @Override
    public void execute(final Player player, String label, final String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/namecheck [username]");
            return;
        }
        player.sendMessage(ChatColor.GREEN + "Requesting name history for " + ChatColor.AQUA + args[0] +
                ChatColor.GREEN + " from Mojang...");
        Launcher.getDashboard().getSchedulerManager().runAsync(() -> {
            List<String> names = NameUtil.getNames(args[0]);
            if (names.isEmpty()) {
                player.sendMessage(ChatColor.RED + "That user could not be found!");
                return;
            }
            StringBuilder msg = new StringBuilder(ChatColor.GREEN + "Previous usernames of " + args[0] + " are:");
            for (int i = 0; i < names.size(); i++) {
                msg.append(ChatColor.AQUA).append("\n").append(i + 1).append(": ").append(ChatColor.GREEN).append(names.get(i));
            }
            player.sendMessage(msg.toString());
        });
    }
}