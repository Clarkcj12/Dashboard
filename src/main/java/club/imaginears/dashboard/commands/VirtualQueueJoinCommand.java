package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;

public class VirtualQueueJoinCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length == 1) Launcher.getDashboard().getParkQueueManager().joinQueue(player, args[0]);
    }
}
