package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.packets.dashboard.PacketMaintenance;
import club.imaginears.dashboard.packets.dashboard.PacketMaintenanceWhitelist;
import club.imaginears.dashboard.server.DashboardSocketChannel;
import club.imaginears.dashboard.server.WebSocketServerHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by Marc on 9/12/16
 */
public class MaintenanceCommand extends DashboardCommand {

    public MaintenanceCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(final Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        dashboard.setMaintenance(!dashboard.isMaintenance());
        PacketMaintenance packet = new PacketMaintenance(dashboard.isMaintenance());
        if (dashboard.isMaintenance()) {
            player.sendMessage(ChatColor.GREEN + "Loading list of staff members for maintenance mode...");
            List<UUID> staff = dashboard.getMongoHandler().getPlayersByRank(Rank.TRAINEE, Rank.TRAINEEBUILD,
                    Rank.MOD, Rank.BUILDER, Rank.ARCHITECT, Rank.COORDINATOR, Rank.DEVELOPER, Rank.LEAD, Rank.MANAGER, Rank.DIRECTOR);
            dashboard.setMaintenanceWhitelist(staff);
            PacketMaintenanceWhitelist whitelist = new PacketMaintenanceWhitelist(staff);
            player.sendMessage(ChatColor.GREEN + "Maintenance Mode enabled! Notifying Bungees...");
            for (Object o : WebSocketServerHandler.getGroup()) {
                DashboardSocketChannel bungee = (DashboardSocketChannel) o;
                if (!bungee.getType().equals(PacketConnectionType.ConnectionType.BUNGEECORD)) {
                    continue;
                }
                bungee.send(packet);
                bungee.send(whitelist);
            }
            player.sendMessage(ChatColor.GREEN + "Bungees notified! Disconnecting all Guests...");
            for (Player tp : dashboard.getOnlinePlayers()) {
                if (tp.getRank().getRankId() >= Rank.TRAINEE.getRankId()) {
                    continue;
                }
                tp.kickPlayer(ChatColor.AQUA + "Maintenance Mode has been enabled!\nFollow " + ChatColor.BLUE +
                        "@PalaceDev " + ChatColor.AQUA + "on Twitter for updates.");
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Player tp : dashboard.getOnlinePlayers()) {
                        if (tp.getRank().getRankId() < Rank.TRAINEE.getRankId()) return;
                    }
                    player.sendMessage(ChatColor.GREEN + "All Guests have been disconnected!");
                    cancel();
                }
            }, 0L, 1000L);
        } else {
            player.sendMessage(ChatColor.GREEN + "Maintenance Mode " + ChatColor.RED + "disabled! " +
                    ChatColor.GREEN + "Notifying Bungees...");
            for (Object o : WebSocketServerHandler.getGroup()) {
                DashboardSocketChannel bungee = (DashboardSocketChannel) o;
                if (!bungee.getType().equals(PacketConnectionType.ConnectionType.BUNGEECORD)) {
                    continue;
                }
                bungee.send(packet);
            }
            player.sendMessage(ChatColor.GREEN + "Bungees notified!");
        }
    }
}