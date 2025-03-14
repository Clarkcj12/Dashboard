package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

import java.util.List;

public class BannedProvidersCommand extends DashboardCommand {

    public BannedProvidersCommand() {
        super(Rank.LEAD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        List<String> bannedProviders = dashboard.getMongoHandler().getBannedProviders();
        if (bannedProviders.isEmpty()) {
            player.sendMessage(ChatColor.GREEN + "No Banned Providers!");
            return;
        }
        StringBuilder msg = new StringBuilder(ChatColor.GREEN + "Banned Providers:");
        for (String s : bannedProviders) {
            msg.append("\n- ").append(s);
        }
        player.sendMessage(msg.toString());
    }
}