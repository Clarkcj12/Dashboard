package com.palacemc.dashboard.commands;

import com.palacemc.dashboard.Dashboard;
import com.palacemc.dashboard.handlers.Player;
import com.palacemc.dashboard.handlers.ChatColor;
import com.palacemc.dashboard.handlers.MagicCommand;
import com.palacemc.dashboard.handlers.Rank;

import java.util.Arrays;

public class CommandReply extends MagicCommand {

    public CommandReply() {
        aliases = Arrays.asList("r");
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.RED + "/reply [Message]");
            return;
        }
        Player tp = Dashboard.getPlayer(player.getReply());
        if (player.getReply() == null || tp == null) {
            player.sendMessage(ChatColor.RED + "You don't have anyone to respond to!");
            return;
        }
        if (player.getRank().getRankId() < Rank.SQUIRE.getRankId()) {
            if (Dashboard.chatUtil.isMuted(player)) {
                return;
            }
            if (!tp.canRecieveMessages()) {
                player.sendMessage(ChatColor.RED + "This person has messages disabled!");
                return;
            }
            if (!Dashboard.chatUtil.privateMessagesEnabled()) {
                player.sendMessage(ChatColor.RED + "Private messages are currently disabled.");
                return;
            }
        }
        String msg = "";
        for (String arg : args) {
            msg += arg + " ";
        }
        msg = player.getRank().getRankId() < Rank.SQUIRE.getRankId() ? Dashboard.chatUtil.removeCaps(player,
                msg.trim()) : msg.trim();
        if (player.getRank().getRankId() < Rank.SQUIRE.getRankId()) {
            if (Dashboard.chatUtil.containsSwear(player, msg) || Dashboard.chatUtil.isAdvert(player, msg)
                    || Dashboard.chatUtil.spamCheck(player, msg) || Dashboard.chatUtil.containsUnicode(player, msg)) {
                return;
            }
            String mm = msg.toLowerCase().replace(".", "").replace("-", "").replace(",", "")
                    .replace("/", "").replace("_", "").replace(" ", "");
            if (mm.contains("skype") || mm.contains(" skyp ") || mm.startsWith("skyp ") || mm.endsWith(" skyp") || mm.contains("skyp*")) {
                player.sendMessage(ChatColor.RED + "Please do not ask for Skype information!");
                return;
            }
        }
        if (tp.hasMentions()) {
            tp.mention();
        }
        tp.sendMessage(player.getRank().getNameWithBrackets() + ChatColor.GRAY + " " + player.getName() +
                ChatColor.GREEN + " -> " + ChatColor.LIGHT_PURPLE + "You: " + ChatColor.WHITE + msg);
        player.sendMessage(ChatColor.LIGHT_PURPLE + "You " + ChatColor.GREEN + "-> " +
                tp.getRank().getNameWithBrackets() + ChatColor.GRAY + " " + tp.getName() + ": " +
                ChatColor.WHITE + msg);
        tp.setReply(player.getUniqueId());
        Dashboard.chatUtil.socialSpyMessage(player, tp, msg, "reply");
        Dashboard.chatUtil.logMessage(player.getUniqueId(), "/reply " + tp.getName() + " " + msg);
    }
}