package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class ToggleServerQueueCommand extends DashboardCommand {

    public ToggleServerQueueCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (Launcher.getDashboard().getServerUtil().toggleServerQueueEnabled()) {
            player.sendMessage(ChatColor.GREEN + "Server queues have been enabled!");
        } else {
            player.sendMessage(ChatColor.RED + "Server queues have been disabled!");
        }
    }
}
