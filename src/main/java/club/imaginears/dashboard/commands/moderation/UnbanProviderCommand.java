package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class UnbanProviderCommand extends DashboardCommand {

    public UnbanProviderCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/unbanprovider [Provider]");
            return;
        }
        StringBuilder provider = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            provider.append(args[i]);
            if (i < (args.length - 1)) {
                provider.append(" ");
            }
        }
        dashboard.getMongoHandler().unbanProvider(provider.toString());
        dashboard.getModerationUtil().announceUnban("Provider " + provider.toString(), player.getUsername());
    }
}