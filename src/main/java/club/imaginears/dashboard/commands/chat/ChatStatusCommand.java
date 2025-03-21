package club.imaginears.dashboard.commands.chat;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.Server;

/**
 * Created by Marc on 3/25/17.
 */
public class ChatStatusCommand extends DashboardCommand {

    public ChatStatusCommand() {
        super(Rank.TRAINEE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        Server s = dashboard.getServer(player.getServer());
        boolean park = s.isPark();
        String name;
        int count = 0;
        boolean muted;
        if (park) {
            int c = 0;
            for (Server sr : dashboard.getServers()) {
                if (!sr.isPark() || sr.getCount() == 0) continue;
                c++;
                count += sr.getCount();
            }
            name = "ParkChat (" + c + " servers)";
            muted = dashboard.getChatUtil().isChatMuted("ParkChat");
        } else {
            name = s.getName();
            count = s.getCount();
            muted = dashboard.getChatUtil().isChatMuted(s.getName());
        }
        player.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.YELLOW + name + "\n" + ChatColor.GREEN +
                "Players: " + ChatColor.YELLOW + count + "\n" + ChatColor.GREEN + "Status: " +
                (muted ? (ChatColor.RED + "Muted") : (ChatColor.YELLOW + "Unmuted")));
    }
}
