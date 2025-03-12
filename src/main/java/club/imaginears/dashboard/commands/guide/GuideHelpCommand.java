package club.imaginears.dashboard.commands.guide;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.RankTag;

public class GuideHelpCommand extends DashboardCommand {

    public GuideHelpCommand() {
        super(Rank.TRAINEE, RankTag.GUIDE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length < 2) {
            player.sendMessage(ChatColor.AQUA + "/h accept [username] - Accept a help request");
            player.sendMessage(ChatColor.AQUA + "/h tp [username] - Teleport cross-server to a player");
            return;
        }
        Dashboard dashboard = Launcher.getDashboard();
        Player tp = dashboard.getPlayer(args[1]);
        if (tp == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return;
        }
        switch (args[0].toLowerCase()) {
            case "accept": {
                dashboard.getGuideUtil().acceptHelpRequest(player, tp);
                break;
            }
            case "tp": {
                dashboard.getGuideUtil().teleport(player, tp);
                break;
            }
            default: {
                player.sendMessage(ChatColor.AQUA + "/h accept [username] - Accept a help request");
                player.sendMessage(ChatColor.AQUA + "/h tp [username] - Teleport cross-server to a player");
                break;
            }
        }
    }
}
