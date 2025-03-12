package club.imaginears.dashboard.commands.guide;

import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.chat.ComponentBuilder;
import club.imaginears.dashboard.chat.HoverEvent;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;
import club.imaginears.dashboard.handlers.RankTag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GuideListCommand extends DashboardCommand {

    public GuideListCommand() {
        super(Rank.TRAINEE, RankTag.GUIDE);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        List<Player> guides = new ArrayList<>();
        for (Player tp : Launcher.getDashboard().getOnlinePlayers()) {
            if (tp.hasTag(RankTag.GUIDE)) {
                guides.add(tp);
            }
        }
        guides.sort(Comparator.comparing(o -> o.getUsername().toLowerCase()));

        ComponentBuilder comp = new ComponentBuilder("Online Guides (" + guides.size() + "): ").color(ChatColor.DARK_GREEN);
        int i = 0;
        for (Player p : guides) {
            comp.append(p.getUsername(), ComponentBuilder.FormatRetention.NONE).color(ChatColor.GREEN)
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Currently on: ")
                            .color(ChatColor.GREEN).append(p.getServer()).color(ChatColor.AQUA).create()));
            if (i < (guides.size() - 1)) comp.append(", ");
            i++;
        }
        player.sendMessage(comp.create());
    }
}
