package club.imaginears.dashboard.commands.chat;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.RankTag;

import java.util.List;

public class GuideChatCommand extends DashboardCommand {

    public GuideChatCommand() {
        super(Rank.TRAINEE, RankTag.GUIDE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/gc [Message]");
            return;
        }
        String message = String.join(" ", args);
        String response;
        Rank rank = player.getRank();
        List<RankTag> tags = player.getTags();

        response = ChatColor.WHITE + "[" + ChatColor.DARK_GREEN + "GUIDE" + ChatColor.WHITE + "] " + RankTag.format(tags) +
                rank.getFormattedName() + " " + ChatColor.GRAY + player.getUsername() + ": " + ChatColor.DARK_AQUA + message;
        dashboard.getChatUtil().guideChatMessage(response);
        dashboard.getChatUtil().logMessage(player.getUniqueId(), "/gc " + player.getUsername() + " " + message);
    }
}