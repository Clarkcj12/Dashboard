package com.palacemc.dashboard.commands;

import com.palacemc.dashboard.handlers.ChatColor;
import com.palacemc.dashboard.handlers.MagicCommand;
import com.palacemc.dashboard.handlers.Player;

public class Commandwhereami extends MagicCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        player.sendMessage(ChatColor.BLUE + "You are on the server " + ChatColor.GOLD + player.getServer());
    }
}