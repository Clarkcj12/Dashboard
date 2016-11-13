package com.palacemc.dashboard.commands;

import com.palacemc.dashboard.Dashboard;
import com.palacemc.dashboard.handlers.Player;
import com.palacemc.dashboard.handlers.ChatColor;
import com.palacemc.dashboard.handlers.MagicCommand;
import com.palacemc.dashboard.handlers.Rank;

public class Commandfind extends MagicCommand {

    public Commandfind() {
        super(Rank.SQUIRE);
        tabCompletePlayers = true;
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length != 1) {
            player.sendMessage(ChatColor.RED + "/find [Player]");
            return;
        }
        Player tp = Dashboard.getPlayer(args[0]);
        if (tp == null) {
            player.sendMessage(ChatColor.RED + args[0] + " is not online!");
            return;
        }
        player.sendMessage(ChatColor.BLUE + tp.getName() + " is on the server " + ChatColor.GOLD + tp.getServer());
    }
}