package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.chat.ClickEvent;
import club.imaginears.dashboard.chat.ComponentBuilder;
import club.imaginears.dashboard.chat.HoverEvent;
import network.palace.dashboard.chat.*;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;

public class ApplyCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        player.sendMessage(new ComponentBuilder("\nClick to see what positions we have available!\n")
        .color(ChatColor.YELLOW).underlined(false).bold(true)
        .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://palnet.us/apply"))
        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to visit https://palnet.us/apply").color(ChatColor.GREEN).create())).create());
    }
}
