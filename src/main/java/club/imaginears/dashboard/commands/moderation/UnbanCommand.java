package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

import java.util.Collections;
import java.util.UUID;

public class UnbanCommand extends DashboardCommand {

    public UnbanCommand() {
        super(Rank.MOD);
        aliases = Collections.singletonList("pardon");
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/unban [Player] [Username]");
            return;
        }
        String username = args[0];
        UUID uuid = dashboard.getMongoHandler().usernameToUUID(username);
        if (uuid == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return;
        }
        dashboard.getMongoHandler().unbanPlayer(uuid);
        dashboard.getModerationUtil().announceUnban(username, player.getUsername());
    }
}