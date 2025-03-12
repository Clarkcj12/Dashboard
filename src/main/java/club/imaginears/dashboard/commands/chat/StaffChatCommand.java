package club.imaginears.dashboard.commands.chat;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.RankTag;

import java.util.List;

public class StaffChatCommand extends DashboardCommand {

    public StaffChatCommand() {
        super(Rank.TRAINEE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/sc [Message]");
            return;
        }
        String message = String.join(" ", args);
        String response;
        Rank rank = player.getRank();
        List<RankTag> tags = player.getTags();

        response = ChatColor.WHITE + "[" + ChatColor.RED + "STAFF" + ChatColor.WHITE + "] " + RankTag.format(tags) +
                rank.getFormattedName() + " " + ChatColor.GRAY + player.getUsername() + ": " + ChatColor.GOLD +
                ChatColor.translateAlternateColorCodes('&', message);
        dashboard.getChatUtil().staffChatMessage(response);
        dashboard.getChatUtil().logMessage(player.getUniqueId(), "/sc " + player.getUsername() + " " + message);
    }
}