package network.palace.dashboard.commands;

import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.handlers.ChatColor;
import network.palace.dashboard.handlers.MagicCommand;
import network.palace.dashboard.handlers.Rank;

public class Commandho extends MagicCommand {

    public Commandho() {
        super(Rank.WIZARD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length > 0) {
            StringBuilder message = new StringBuilder();
            for (String arg : args) {
                message.append(arg).append(" ");
            }
            Dashboard dashboard = Launcher.getDashboard();
            for (Player tp : dashboard.getOnlinePlayers()) {
                if (tp.getRank().getRankId() >= Rank.WIZARD.getRankId() && !tp.isDisabled()) {
                    tp.sendMessage(ChatColor.RED + "[ADMIN CHAT] " + ChatColor.GRAY + player.getName() + ": " +
                            ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', message.toString()));
                }
            }
            return;
        }
        player.sendMessage(ChatColor.RED + "/ho [Message]");
    }
}