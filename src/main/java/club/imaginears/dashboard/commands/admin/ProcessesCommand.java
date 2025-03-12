package club.imaginears.dashboard.commands.admin;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.server.DashboardSocketChannel;
import club.imaginears.dashboard.server.WebSocketServerHandler;

/**
 * Created by Marc on 10/8/16
 */
public class ProcessesCommand extends DashboardCommand {

    public ProcessesCommand() {
        super(Rank.DEVELOPER);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        StringBuilder msg = new StringBuilder(ChatColor.YELLOW + "Processes connected to Dashboard:");
        for (Object o : WebSocketServerHandler.getGroup()) {
            DashboardSocketChannel dash = (DashboardSocketChannel) o;
            String type = "";
            String ip = dash.remoteAddress().getAddress().getHostAddress() + ":" + dash.remoteAddress().getPort();
            switch (dash.getType()) {
                case BUNGEECORD:
                    type = "BungeeCord - " + ip;
                    break;
                case DAEMON:
                    type = "Daemon - " + ip;
                    break;
                case WEBCLIENT:
                    type = "Web Client - " + ip;
                    break;
                case INSTANCE:
                    type = dash.getServerName() + " - " + ip;
                    break;
                case AUDIOSERVER:
                    type = "The Audio Server - " + ip;
                    break;
                case UNKNOWN:
                    type = "Unknown - " + ip;
                    break;
            }
            msg.append(ChatColor.GREEN).append("\n- ").append(type);
        }
        player.sendMessage(msg.toString());
    }
}