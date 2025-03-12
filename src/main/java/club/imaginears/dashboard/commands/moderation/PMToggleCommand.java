package club.imaginears.dashboard.commands.moderation;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

/**
 * Created by Marc on 10/8/16
 */
public class PMToggleCommand extends DashboardCommand {

    public PMToggleCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        boolean enabled = dashboard.getChatUtil().privateMessagesEnabled();
        dashboard.getChatUtil().setPrivateMessages(!enabled);
        dashboard.getModerationUtil().togglePrivate(dashboard.getChatUtil().privateMessagesEnabled(), player.getUsername());
    }
}