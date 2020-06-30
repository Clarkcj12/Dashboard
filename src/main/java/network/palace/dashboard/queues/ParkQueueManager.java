package network.palace.dashboard.queues;

import network.palace.dashboard.Dashboard;
import network.palace.dashboard.Launcher;
import network.palace.dashboard.chat.*;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.handlers.Server;
import network.palace.dashboard.packets.BasePacket;
import network.palace.dashboard.packets.dashboard.PacketConnectionType;
import network.palace.dashboard.packets.park.queue.*;

import java.util.*;

public class ParkQueueManager {
    private final List<VirtualQueue> queues = new ArrayList<>();

    public ParkQueueManager() {
        Dashboard dashboard = Launcher.getDashboard();
        new Timer().schedule(new TimerTask() {
            int i = 1;

            @Override
            public void run() {
                if (queues.isEmpty()) return;

                boolean announce = i++ >= 4;
                if (announce) i = 1;

                List<BasePacket> packets = new ArrayList<>();
                queues.stream().forEach(queue -> {
                    if (announce) queue.sendPositionMessages();

                    if (queue.isUpdated()) {
                        queue.setUpdated(false);
                        packets.add(new UpdateQueuePacket(queue.getId(), queue.isOpen(), queue.getMembers()));
                    }

                    if (!queue.getServer().isOnline()) return;

                    List<UUID> sendingToServer = queue.getSendingToServer();
                    sendingToServer.forEach(uuid -> {
                        Player player = dashboard.getPlayer(uuid);
                        if (player == null) return;
                        queue.sendToServer(player);
                    });

                    List<UUID> holdingAreaMembers = queue.getHoldingAreaMembers();
                    holdingAreaMembers.forEach(uuid -> {
                        Player player = dashboard.getPlayer(uuid);
                        if (player == null || sendingToServer.contains(player.getUniqueId())
                                || player.getServer().equals(queue.getServer().getName())) return;
                        queue.markAsSendingToServer(player);
                    });
                });
                Launcher.getDashboard().sendToAllConnections(channel -> {
                    if (!channel.getType().equals(PacketConnectionType.ConnectionType.INSTANCE)) return false;
                    Server server = dashboard.getServer(channel.getServerName(), true);
                    return server.isPark();
                }, packets);
            }
        }, 1000, 10 * 1000);
    }

    /**
     * Create a new VirtualQueue on a Server
     *
     * @param packet the packet containing information about the new queue
     * @param server the server the queue was created on
     */
    public void createQueue(CreateQueuePacket packet, Server server) {
        VirtualQueue queue = new VirtualQueue(packet.getQueueId(), packet.getQueueName(), packet.getHoldingArea(), server);
        queues.add(queue);
        Launcher.getDashboard().getModerationUtil().sendMessage(ChatColor.GREEN + "A virtual queue (" + queue.getName() +
                ChatColor.GREEN + ") has been created on " + ChatColor.AQUA + server.getName());

        Launcher.getDashboard().sendToAllConnections(channel -> {
            Server s = Launcher.getDashboard().getServer(channel.getServerName());
            return s != null && !s.getUniqueId().equals(server.getUniqueId()) && s.isPark();
        }, Collections.singletonList(packet));
    }

    /**
     * Remove a VirtualQueue from a server
     *
     * @param packet the packet containing the queue ID
     * @param server the server that sent the packet
     * @implNote A VirtualQueue can only be removed when it is closed, or this will silently fail
     * @implNote Only the server that created a VirtualQueue can remove it, or this will silently fail
     */
    public void removeQueue(RemoveQueuePacket packet, Server server) {
        VirtualQueue queue = getQueue(packet.getQueueId());
        if (queue != null && !queue.isOpen() && queue.getServer().getName().equals(server.getName())) {
            Launcher.getDashboard().getModerationUtil().sendMessage(ChatColor.GREEN + "A virtual queue (" + queue.getName() +
                    ChatColor.GREEN + ") has been removed from " + ChatColor.AQUA + server.getName());
            queues.remove(queue);

            Launcher.getDashboard().sendToAllConnections(channel -> {
                Server s = Launcher.getDashboard().getServer(channel.getServerName());
                return s != null && s.isPark();
            }, Collections.singletonList(packet));
        }
    }

    /**
     * Update a VirtualQueue
     *
     * @param packet the packet containing the update information
     * @param server the server that sent the packet
     * @implNote Only the server that created a VirtualQueue can update it, or this will silently fail
     */
    public void updateQueue(UpdateQueuePacket packet, Server server) {
        VirtualQueue queue = getQueue(packet.getQueueId());
        if (queue != null && queue.isOpen() != packet.isOpen() && queue.getServer().getName().equals(server.getName())) {
            queue.setOpen(packet.isOpen());
            Launcher.getDashboard().getModerationUtil().sendMessage(ChatColor.GREEN + "A virtual queue (" + queue.getName() +
                    ChatColor.GREEN + ") has been " + (queue.isOpen() ? "opened" : "closed"));
        }
    }

    /**
     * Move players up in a VirtualQueue
     *
     * @param packet the packet containing the queue id
     * @param server the server that sent the packet
     * @implNote Only the server that created a VirtualQueue can admit players, or this will silently fail
     */
    public void admitQueue(AdmitQueuePacket packet, Server server) {
        VirtualQueue queue = getQueue(packet.getQueueId());
        if (queue != null && queue.getServer().getName().equals(server.getName())) {
            queue.admit();
        }
    }

    /**
     * Send out an announcement for a VirtualQueue
     *
     * @param packet the packet containing the announcement information
     * @param server the server that sent the packet
     * @implNote Only the server that created a VirtualQueue can send announcements, or this will silently fail
     */
    public void announceQueue(AnnounceQueuePacket packet, Server server) {
        VirtualQueue queue = getQueue(packet.getQueueId());
        if (queue != null && queue.getServer().getName().equals(server.getName())) {
            BaseComponent[] components = new ComponentBuilder("[").color(ChatColor.WHITE)
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            TextComponent.fromLegacyText(ChatColor.GREEN + "Click to join the virtual queue " + queue.getName() + "!")))
                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vqjoin " + queue.getId()))
                    .append("Information").color(ChatColor.AQUA).append("] ").color(ChatColor.WHITE)
                    .appendLegacy(packet.getAnnouncement()).create();
            for (Player player : Launcher.getDashboard().getOnlinePlayers()) {
                player.sendMessage(components);
            }
        }
    }

    public void joinQueue(Player player, String id) {
        VirtualQueue queue = getQueue(id);
        if (queue == null) return;
        queue.joinQueue(player);
    }

    private VirtualQueue getQueue(String id) {
        for (VirtualQueue queue : queues) {
            if (queue.getId().equals(id)) return queue;
        }
        return null;
    }

    public void serverConnect(Server server) {
        List<BasePacket> packets = new ArrayList<>();
        queues.forEach(q -> packets.add(new CreateQueuePacket(q.getId(), q.getName(), q.getHoldingArea(), q.getServer().getName())));
        Launcher.getDashboard().sendToAllConnections(channel -> channel.getServerName().equals(server.getName()), packets);
    }

    public void serverDisconnect(Server s) {
        List<VirtualQueue> toRemove = new ArrayList<>();
        queues.forEach(q -> {
            if (q.getServer().getName().equals(s.getName())) {
                toRemove.add(q);
                q.setOpen(false);
            }
        });
        toRemove.forEach(q -> removeQueue(new RemoveQueuePacket(q.getId()), s));
    }
}
