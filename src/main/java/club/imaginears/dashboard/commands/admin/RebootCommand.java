package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.packets.dashboard.PacketStartReboot;
import club.imaginears.dashboard.server.DashboardSocketChannel;
import club.imaginears.dashboard.server.WebSocketServerHandler;

import java.util.Timer;
import java.util.TimerTask;

public class RebootCommand extends DashboardCommand {

    public RebootCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        player.sendMessage(ChatColor.GREEN + "Starting shutdown of Dashboard...");
        PacketStartReboot packet = new PacketStartReboot();
        for (Object o : WebSocketServerHandler.getGroup()) {
            DashboardSocketChannel bungee = (DashboardSocketChannel) o;
            bungee.send(packet);
        }
        player.sendMessage(ChatColor.GREEN + "Bungees notified, disconnecting " + dashboard.getOnlinePlayers().size()
                + " players...");
        for (Player tp : dashboard.getOnlinePlayers()) {
            tp.kickPlayer(ChatColor.AQUA + "Please Pardon our Pixie Dust! We are restarting our servers right now.");
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (dashboard.getOnlinePlayers().isEmpty()) {
                    cancel();
                    System.exit(0);
                }
            }
        }, 1000);
    }
}