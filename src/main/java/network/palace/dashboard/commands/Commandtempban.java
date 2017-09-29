package network.palace.dashboard.commands;

import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;
import network.palace.dashboard.handlers.*;
import network.palace.dashboard.utils.DateUtil;

import java.sql.Date;
import java.util.UUID;

public class Commandtempban extends MagicCommand {

    public Commandtempban() {
        super(Rank.MOD);
        tabCompletePlayers = true;
    }

    @Override
    public void execute(final Player player, String label, final String[] args) {
        if (args.length < 3) {
            player.sendMessage(ChatColor.RED + "/tempban [Player] [Time] [Reason]");
            player.sendMessage(ChatColor.RED + "Time Examples:");
            player.sendMessage(ChatColor.RED + "6h = Six Hours");
            player.sendMessage(ChatColor.RED + "6d = Six Days");
            player.sendMessage(ChatColor.RED + "6w = Six Weeks");
            player.sendMessage(ChatColor.RED + "6mon = Six Months");
            return;
        }
        final String username = args[0];
        final long timestamp = DateUtil.parseDateDiff(args[1], true);
        Dashboard dashboard = Launcher.getDashboard();
        dashboard.getSchedulerManager().runAsync(() -> {
            String reason;
            StringBuilder r = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                r.append(args[i]).append(" ");
            }
            reason = (r.substring(0, 1).toUpperCase() + r.substring(1)).trim();
            String source = player.getUsername();
            Player tp = dashboard.getPlayer(username);
            UUID uuid;
            if (tp == null) {
                uuid = dashboard.getSqlUtil().uuidFromUsername(username);
            } else {
                uuid = tp.getUniqueId();
            }
            Ban ban = new Ban(uuid, username, false, timestamp, reason, source);
            if (tp != null) {
                tp.kickPlayer(ChatColor.RED + "You Have Been Temporarily Banned For " + ChatColor.AQUA + reason +
                        ". " + ChatColor.RED + "Your Temporary Ban Will Expire in " + ChatColor.AQUA +
                        DateUtil.formatDateDiff(timestamp));
            }
            dashboard.getSqlUtil().banPlayer(uuid, reason, false, new Date(timestamp), source);
            dashboard.getModerationUtil().announceBan(ban);
        });
    }
}