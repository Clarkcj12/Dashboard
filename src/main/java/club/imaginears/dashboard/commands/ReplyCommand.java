package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

import java.util.Collections;

public class ReplyCommand extends DashboardCommand {

    public ReplyCommand() {
        aliases = Collections.singletonList("r");
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        Dashboard dashboard = Launcher.getDashboard();
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/reply [Message]");
            return;
        }
        Player tp = dashboard.getPlayer(player.getReply());
        if (player.getReply() == null || tp == null) {
            player.sendMessage(ChatColor.RED + "You don't have anyone to respond to!");
            return;
        }
        if (player.getRank().getRankId() < Rank.TRAINEE.getRankId() && dashboard.getChatUtil().isMuted(player)) return;
        if (player.getRank().getRankId() < Rank.CHARACTER.getRankId()) {
            if (!tp.canRecieveMessages() || tp.isIgnored(player.getUniqueId()) && tp.getRank().getRankId() < Rank.CHARACTER.getRankId()) {
                player.sendMessage(ChatColor.RED + "This person has messages disabled!");
                return;
            }
            if (!dashboard.getChatUtil().privateMessagesEnabled()) {
                player.sendMessage(ChatColor.RED + "Private messages are currently disabled.");
                return;
            }
        }
        StringBuilder msg = new StringBuilder();
        for (String arg : args) {
            msg.append(arg).append(" ");
        }
        msg = new StringBuilder(player.getRank().getRankId() < Rank.TRAINEE.getRankId() ? dashboard.getChatUtil().removeCaps(player,
                msg.toString().trim()) : msg.toString().trim());
        if (player.getRank().getRankId() < Rank.TRAINEE.getRankId()) {
            if (dashboard.getChatUtil().containsSwear(player, msg.toString()) || dashboard.getChatUtil().isAdvert(player, msg.toString())
                    || dashboard.getChatUtil().spamCheck(player, msg.toString()) || dashboard.getChatUtil().containsUnicode(player, msg.toString())) {
                return;
            }
            if (dashboard.getChatUtil().strictModeCheck(msg.toString())) {
                player.sendMessage(ChatColor.RED + "Your message was similar to another recently said in chat and was marked as spam. We apologize if this was done in error, we're constantly improving our chat filter.");
                dashboard.getModerationUtil().announceSpamMessage(player.getUsername(), msg.toString());
                dashboard.getLogger().info("CANCELLED CHAT EVENT STRICT MODE");
                return;
            }
        }
        if (tp.hasMentions()) {
            tp.mention();
        }
        if (player.isIgnored(tp.getUniqueId())) {
            player.sendMessage(ChatColor.RED + "You ignore this player, they won't be able to respond!");
        }
        tp.sendMessage(player.getRank().getFormattedName() + ChatColor.GRAY + " " + player.getUsername() +
                ChatColor.GREEN + " -> " + ChatColor.LIGHT_PURPLE + "You: " + ChatColor.WHITE + msg);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You " + ChatColor.GREEN + "-> " +
                tp.getRank().getFormattedName() + ChatColor.GRAY + " " + tp.getUsername() + ": " +
                ChatColor.WHITE + msg);
        tp.setReply(player.getUniqueId());
        player.setReply(tp.getUniqueId());
        dashboard.getChatUtil().socialSpyMessage(player, tp, msg.toString(), "reply");
        dashboard.getChatUtil().logMessage(player.getUniqueId(), "/reply " + tp.getUsername() + " " + msg);
    }
}