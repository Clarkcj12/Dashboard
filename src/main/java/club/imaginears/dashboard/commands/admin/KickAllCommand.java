package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Marc on 10/3/16
 */
public class KickAllCommand extends DashboardCommand {

    public KickAllCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(final Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length <= 0) {
            player.sendMessage(ChatColor.RED + "/kickall [Message]");
            return;
        }
        StringBuilder r = new StringBuilder();
        for (String arg : args) {
            r.append(arg).append(" ");
        }
        r = new StringBuilder(ChatColor.translateAlternateColorCodes('&', r.toString().trim()));
        player.sendMessage(ChatColor.GREEN + "Disconnecting all players for " + r);
        for (Player tp : dashboard.getOnlinePlayers()) {
            if (tp.getRank().getRankId() >= Rank.DEVELOPER.getRankId()) continue;
            tp.kickPlayer(r.toString());
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                boolean empty = true;
                for (Player tp : dashboard.getOnlinePlayers()) {
                    if (tp.getRank().getRankId() < Rank.DEVELOPER.getRankId()) {
                        empty = false;
                        break;
                    }
                }
                if (empty) {
                    player.sendMessage(ChatColor.GREEN + "All players have been disconnected!");
                    cancel();
                }
            }
        }, 0, 1000);
    }
}