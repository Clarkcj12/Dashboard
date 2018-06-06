package network.palace.dashboard.utils;

import network.palace.dashboard.commands.*;
import network.palace.dashboard.handlers.ChatColor;
import network.palace.dashboard.handlers.DashboardCommand;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.packets.dashboard.PacketTabComplete;

import java.util.*;

public class CommandUtil {
    private HashMap<String, DashboardCommand> commands = new HashMap<>();

    public CommandUtil() {
        initialize();
    }

    private void initialize() {
        register("audio", new AudioCommand());
        register("b", new BroadcastCommand());
        register("ban", new BanCommand());
        register("banip", new BanIPCommand());
        register("bannedproviders", new BannedProvidersCommand());
        register("banprovider", new BanProviderCommand());
        register("bseen", new BseenCommand());
        register("bug", new Bugcommand());
        register("bungeecounts", new BungeeCountsCommand());
        register("cc", new ClearChatCommand());
        register("charlist", new CharListCommand());
        register("chat", new ChatCommand());
        register("chatdelay", new ChatDelayCommand());
        register("chatreload", new ChatReloadCommand());
        register("chatstatus", new ChatStatusCommand());
        register("cmds", new CmdsCommand());
//        register("convert", new ConvertCommand());
        register("dashboardversion", new DashboardVersion());
        register("discord", new DiscordCommand());
        register("find", new FindCommand());
        register("friend", new FriendCommand());
        register("ignore", new IgnoreCommand());
        register("ho", new AdminChatCommand());
        register("ip", new IPCommand());
        register("ipseen", new IPSeenCommand());
        register("join", new JoinCommand());
        register("kick", new KickCommand());
        register("kickall", new KickAllCommand());
        register("link", new LinkCommand());
        register("maintenance", new MaintenanceCommand());
        register("mentions", new MentionsCommand());
        register("modlog", new ModlogCommand());
        register("motdrl", new MotdReloadCommand());
        register("msg", new MsgCommand());
        register("msgtoggle", new MsgToggleCommand());
        register("mute", new MuteCommand());
        register("mutechat", new MuteChatCommand());
        register("namecheck", new NamecheckCommand());
        register("oc", new OnlineCountCommand());
        register("party", new PartyCommand());
        register("parties", new PartiesCommand());
        register("pchat", new PartyChatCommand());
        register("pmtoggle", new PMToggleCommand());
        register("processes", new ProcessesCommand());
        register("reboot", new RebootCommand());
        register("reloadicon", new ReloadIconCommand());
        register("reply", new ReplyCommand());
        register("sc", new StaffChatCommand());
        register("send", new SendCommand());
        register("server", new ServerCommand());
        register("social", new SocialCommand());
        register("staff", new StaffCommand());
        register("stafflist", new StaffListCommand());
        register("store", new StoreCommand());
        register("strict", new StrictCommand());
        register("tempban", new TempBanCommand());
        register("unban", new UnbanCommand());
        register("unbanip", new UnbanIPCommand());
        register("unbanprovider", new UnbanProviderCommand());
        register("unmute", new UnmuteCommand());
        register("updatehashes", new UpdateHashesCommand());
        register("uptime", new UptimeCommand());
        register("warn", new WarnCommand());
        register("whereami", new WhereAmICommand());
    }

    public boolean handleCommand(Player player, String message) {
        try {
            String[] parts = message.split(" ");
            String command = parts[0].toLowerCase();
            String[] args = new String[parts.length - 1];
            int i = 0;
            boolean first = true;
            for (String s : parts) {
                if (first) {
                    first = false;
                    continue;
                }
                args[i] = s;
                i++;
            }
            DashboardCommand cmd = null;
            if (!commands.containsKey(command)) {
                for (DashboardCommand c : new ArrayList<>(commands.values())) {
                    if (c.getAliases().contains(command)) {
                        cmd = c;
                        break;
                    }
                }
            } else {
                cmd = commands.get(command);
            }
            if (cmd == null) {
                return false;
            }
            execute(player, cmd, command, args);
            return true;
        } catch (Exception e) {
            player.sendMessage(ChatColor.RED + "An internal error occurred whilst executing this command.");
            e.printStackTrace();
            return true;
        }
    }

    public TreeMap<String, DashboardCommand> getCommands() {
        return new TreeMap<>(commands);
    }

    public DashboardCommand getCommand(String label) {
        return commands.get(label);
    }

    private void execute(Player player, DashboardCommand c, String command, String[] args) {
        if (!c.canPerformCommand(player.getRank())) {
            player.sendMessage(ChatColor.RED + "You do not have permission to execute this command!");
            return;
        }
        c.execute(player, command, args);
    }

    public void register(String label, DashboardCommand command) {
        commands.put(label.toLowerCase(), command);
    }

    public void tabComplete(Player player, String command, List<String> args, List<String> results) {
        DashboardCommand cmd = null;
        if (!commands.containsKey(command)) {
            for (DashboardCommand c : new ArrayList<>(commands.values())) {
                if (c.getAliases().contains(command)) {
                    cmd = c;
                    break;
                }
            }
        } else {
            cmd = commands.get(command);
        }
        if (cmd == null) {
            return;
        }
        Iterable<String> l = cmd.onTabComplete(player, args);
        List<String> list = new ArrayList<>();
        for (String s : l) {
            list.add(s);
        }
        if (!list.isEmpty()) {
            results.clear();
            results.addAll(list);
        }
        PacketTabComplete packet = new PacketTabComplete(player.getUniqueId(), command, args, results);
        player.send(packet);
    }

    public List<String> getCommandsAndAliases() {
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, DashboardCommand> entry : commands.entrySet()) {
            list.add(entry.getKey());
            list.addAll(entry.getValue().getAliases());
        }
        return list;
    }
}