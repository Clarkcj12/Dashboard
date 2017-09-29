package network.palace.dashboard.commands;

import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;
import network.palace.dashboard.handlers.*;

/**
 * Created by Marc on 3/25/17.
 */
public class Commandchatstatus extends MagicCommand {

    public Commandchatstatus() {
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
                if (!sr.isPark()) {
                    continue;
                }
                count += sr.getCount();
                if (sr.getCount() != 0) {
                    c++;
                }
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
