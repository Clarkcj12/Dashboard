package club.imaginears.dashboard.commands.chat;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class AdminChatCommand extends DashboardCommand {

    public AdminChatCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length > 0) {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
            Dashboard dashboard = Launcher.getDashboard();
            for (Player tp : dashboard.getOnlinePlayers()) {
                if (tp.getRank().getRankId() >= Rank.DEVELOPER.getRankId() && !tp.isDisabled()) {
                    tp.sendMessage(ChatColor.RED + "[ADMIN CHAT] " + ChatColor.GRAY + player.getUsername() + ": " +
                            ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message.toString()));
                }
            }
            return;
        }
        player.sendMessage(ChatColor.RED + "/ho [Message]");
    }
}