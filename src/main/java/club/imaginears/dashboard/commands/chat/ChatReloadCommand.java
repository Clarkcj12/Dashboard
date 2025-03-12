package club.imaginears.dashboard.commands.chat;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

/**
 * Created by Marc on 9/24/16
 */
public class ChatReloadCommand extends DashboardCommand {

    public ChatReloadCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        player.sendMessage(ChatColor.GREEN + "Reloading chat settings (swears and links)...");
        dashboard.getChatUtil().reload();
        dashboard.getSchedulerManager().getBroadcastClock().reload();
        player.sendMessage(ChatColor.GREEN + "Chat settings reloaded!");
    }
}