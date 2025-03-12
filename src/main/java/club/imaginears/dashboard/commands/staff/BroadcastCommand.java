package club.imaginears.dashboard.commands.staff;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class BroadcastCommand extends DashboardCommand {

    public BroadcastCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length <= 0) {
            player.sendMessage(ChatColor.RED + "/b [Message]");
            return;
        }
        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }
        String sname = player.getUsername();
        String msg = ChatColor.WHITE + "[" + ChatColor.AQUA + "Information" +
                ChatColor.WHITE + "] " + ChatColor.GREEN + ChatColor.translateAlternateColorCodes('&', message.toString());
        String staff = ChatColor.WHITE + "[" + ChatColor.AQUA +
                sname + ChatColor.WHITE + "] " + ChatColor.GREEN +
                ChatColor.translateAlternateColorCodes('&', message.toString());
        for (Player tp : dashboard.getOnlinePlayers()) {
            if (dashboard.getPlayer(tp.getUniqueId()).getRank().getRankId() >= Rank.TRAINEE.getRankId()) {
                tp.sendMessage(staff);
            } else {
                tp.sendMessage(msg);
            }
        }
    }
}
