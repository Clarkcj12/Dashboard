package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

/**
 * @author Innectic
 * @since 6/15/2017
 */
public class StrictCommand extends DashboardCommand {
    public StrictCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "You must supply an argument! /strict [toggle:threshold] [args...]");
            return;
        }

        Dashboard dashboard = Launcher.getDashboard();
        if (args[0].equalsIgnoreCase("toggle")) {
            String response = dashboard.isStrictMode() ? ChatColor.GREEN + "Leaving strict mode..." :
                    ChatColor.RED + "Entering strict mode... Matching level: " + dashboard.getStrictThreshold();
            dashboard.setStrictMode(!dashboard.isStrictMode());
            player.sendMessage(response);
        } else if (args[0].equalsIgnoreCase("threshold")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "You must supply a threshold! /strict threshold [threshold]");
                return;
            }
            if (!isDouble(args[1])) {
                player.sendMessage(ChatColor.RED + "Invalid threshold!");
                return;
            }
            double threshold = Double.parseDouble(args[1]);
            dashboard.setStrictThreshold(threshold);
            player.sendMessage(ChatColor.GREEN + "Strict mode threshold has been set to " + threshold);
        }
    }

    private boolean isDouble(String toTest) {
        try {
            Double.parseDouble(toTest);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
