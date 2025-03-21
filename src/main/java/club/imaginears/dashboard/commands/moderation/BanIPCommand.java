package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.AddressBan;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class BanIPCommand extends DashboardCommand {

    public BanIPCommand() {
        super(Rank.LEAD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "/banip [IP Address] [Reason]");
            return;
        }
        String ip = args[0];
        StringBuilder r = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            r.append(args[i]).append(" ");
        }
        String reason = r.substring(0, 1).toUpperCase() + r.substring(1);
        String finalReason = reason.trim();
        dashboard.getSchedulerManager().runAsync(() -> {
            try {
                AddressBan existing = dashboard.getMongoHandler().getAddressBan(ip);
                if (existing != null) {
                    player.sendMessage(ChatColor.RED + "This IP " + (!ip.contains("*") ? "Address " : "Range ") +
                            "is already banned! Unban it to change the reason.");
                    return;
                }
                AddressBan ban = new AddressBan(ip, finalReason, player.getUniqueId().toString());
                dashboard.getMongoHandler().banAddress(ban);
                if (!ip.contains("*")) {
                    for (Player tp : dashboard.getOnlinePlayers()) {
                        if (tp.getAddress().equals(ip)) {
                            try {
                                tp.kickPlayer(ChatColor.RED + "Your IP Has Been Banned For " + ChatColor.AQUA + finalReason);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                } else {
                    for (Player tp : dashboard.getOnlinePlayers()) {
                        String[] list = tp.getAddress().split("\\.");
                        String range = list[0] + "." + list[1] + "." + list[2] + ".*";
                        if (range.equalsIgnoreCase(ip)) {
                            try {
                                tp.kickPlayer(ChatColor.RED + "Your IP Range Has Been Banned For " + ChatColor.AQUA + finalReason);
                            } catch (Exception ignored) {
                            }
                        }
                    }
                }
                dashboard.getModerationUtil().announceBan(ban);
            } catch (Exception e) {
                Launcher.getDashboard().getLogger().error("Error processing ip ban", e);
            }
        });
    }
}