package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;

public class LeaveServerQueueCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        String server;
        if ((server = Launcher.getDashboard().getServerUtil().leaveServerQueue(player)) != null) {
            player.sendMessage(ChatColor.GREEN + "You have left the queue to join " + ChatColor.YELLOW + server);
        }
    }
}
