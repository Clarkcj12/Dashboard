package club.imaginears.dashboard.commands;

import club.imaginears.dashboard.Dashboard;
import club.imaginears.dashboard.Launcher;
import club.imaginears.dashboard.chat.ChatColor;
import club.imaginears.dashboard.chat.ClickEvent;
import club.imaginears.dashboard.chat.ComponentBuilder;
import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.packets.audio.PacketContainer;
import club.imaginears.dashboard.packets.audio.PacketKick;
import club.imaginears.dashboard.packets.dashboard.PacketConnectionType;
import club.imaginears.dashboard.server.DashboardSocketChannel;

import java.util.List;

public class AudioCommand extends DashboardCommand {

    @Override
    public void execute(Player player, String label, String[] args) {
        if (args.length != 0) {
            switch (args[0].toLowerCase()) {
                case "leave":
                case "disconnect":
                    List<DashboardSocketChannel> list = Dashboard.getChannels(PacketConnectionType.ConnectionType.AUDIOSERVER);
                    if (!list.isEmpty()) {
                        PacketKick packet = new PacketKick("You were disconnected!");
                        PacketContainer container = new PacketContainer(player.getUniqueId(), packet.getJSON().toString());
                        for (DashboardSocketChannel ch : list) {
                            ch.send(container);
                        }
                    }
                    break;
                default:
                    player.sendMessage(ChatColor.GREEN + "Audio Server Commands:\n" + ChatColor.AQUA + "- /audio " +
                            ChatColor.GREEN + "- Connect to Audio Server\n" + ChatColor.AQUA + "- /audio [leave/disconnect] " +
                            ChatColor.GREEN + "- Disconnect from the Audio Server\n" + ChatColor.AQUA + "- /audio help " +
                            ChatColor.GREEN + "- View this help menu");
                    break;
            }
            return;
        }
        player.sendMessage(new ComponentBuilder("\nClick here to connect to our Audio Server!\n")
                .color(ChatColor.GREEN).underlined(true).bold(true)
                .event((new ClickEvent(ClickEvent.Action.OPEN_URL,
                        (Launcher.getDashboard().isTestNetwork() ? "https://audio-test.palace.network/?t=" :
                                "https://audio.palace.network/?t=") + player.setAudioToken()))).create());
    }
}