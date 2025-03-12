package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.packets.dashboard.PacketUptimeCommand;

/**
 * Created by Marc on 8/26/16
 */
public class UptimeCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        PacketUptimeCommand packet = new PacketUptimeCommand(player.getUniqueId(), Launcher.getDashboard().getStartTime());
        player.send(packet);
    }
}