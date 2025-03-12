package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

import java.util.Arrays;

public class DashboardVersion extends DashboardCommand {

    public DashboardVersion() {
        super(Rank.DEVELOPER);
        aliases.addAll(Arrays.asList("dashboardver", "dashver"));
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        player.sendMessage(ChatColor.GREEN + "Dashboard currently running v" + Launcher.getDashboard().getVersion());
    }
}
