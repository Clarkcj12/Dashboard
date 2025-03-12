package club.imaginears.dashboard.commands.guide;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.RankTag;

public class GuideAnnounceCommand extends DashboardCommand {

    public GuideAnnounceCommand() {
        super(Rank.TRAINEE, RankTag.GUIDE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        Rank rank = player.getRank();
        if ((rank.getRankId() < Rank.MOD.getRankId() && rank.getRankId() >= Rank.TRAINEE.getRankId()) ||
                (rank.getRankId() < Rank.TRAINEE.getRankId() && player.hasTag(RankTag.GUIDE))) {
            // if trainee or non-staff guide
            if (args.length < 1) {
                player.sendMessage(ChatColor.RED + "/gannounce [Message]");
                return;
            }
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                s.append(args[i]);
                if (i <= (args.length - 1)) {
                    s.append(" ");
                }
            }
            dashboard.getGuideUtil().sendAnnouncementRequest(player, ChatColor.translateAlternateColorCodes('&', s.toString()));
        } else {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + "Guide Announce Commands:");
                player.sendMessage(ChatColor.AQUA + "/gannounce accept [Username] - Accept a player's announcement request");
                player.sendMessage(ChatColor.AQUA + "/gannounce decline [Username] - Decline a player's announcement request");
                return;
            }
            switch (args[0].toLowerCase()) {
                case "accept": {
                    dashboard.getGuideUtil().acceptAnnouncementRequest(player, args[1]);
                    break;
                }
                case "decline": {
                    dashboard.getGuideUtil().declineAnnouncementRequest(player, args[1]);
                    break;
                }
                default: {
                    player.sendMessage(ChatColor.AQUA + "/gannounce accept [Username] - Accept a player's announcement request");
                    player.sendMessage(ChatColor.AQUA + "/gannounce decline [Username] - Decline a player's announcement request");
                }
            }
        }
    }
}
