package network.palace.dashboard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import network.palace.dashboard.discordSocket.SocketConnection;
import network.palace.dashboard.forums.Forum;
import network.palace.dashboard.handlers.ChatColor;
import network.palace.dashboard.handlers.Party;
import network.palace.dashboard.handlers.Player;
import network.palace.dashboard.packets.audio.PacketHeartbeat;
import network.palace.dashboard.scheduler.SchedulerManager;
import network.palace.dashboard.server.DashboardServerSocketChannel;
import network.palace.dashboard.server.DashboardSocketChannel;
import network.palace.dashboard.server.WebSocketServerHandler;
import network.palace.dashboard.server.WebSocketServerInitializer;
import network.palace.dashboard.slack.SlackAttachment;
import network.palace.dashboard.slack.SlackMessage;
import network.palace.dashboard.utils.*;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.PatternLayout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Innectic
 * @since 4/29/2017
 */
public class Launcher {

    @Getter private static Dashboard dashboard;

    public Launcher() {
        dashboard = new Dashboard();

        dashboard.setStartTime(System.currentTimeMillis());
        PatternLayout layout = new PatternLayout("[%d{HH:mm:ss}] [%p] - %m%n");
        dashboard.getLogger().addAppender(new ConsoleAppender(layout));
        try {
            dashboard.getLogger().addAppender(new FileAppender(layout, "dashboard.log", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dashboard.getLogger().info("Starting up Dashboard...");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dashboard.getLogger().warn("Shutting down Dashboard...");
            for (Player p : dashboard.getOnlinePlayers()) {
                if (!p.getChannel().equalsIgnoreCase("all")) {
                    p.setChannel("all");
                    p.sendMessage(ChatColor.GREEN + "You have been moved to the " + ChatColor.AQUA + "all " +
                            ChatColor.GREEN + "channel");
                }
            }
            File f = new File("parties.txt");
            try {
                f.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(f, false));
                for (Party p : dashboard.getPartyUtil().getParties()) {
                    bw.write(p.toString());
                    bw.newLine();
                }
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            dashboard.getVoteUtil().stop();
            dashboard.getSlackUtil().sendDashboardMessage(new SlackMessage(),
                    Collections.singletonList(new SlackAttachment("Dashboard went offline! #devs").color("danger")));
        }));
        dashboard.setRandom(new Random());
        dashboard.setSchedulerManager(new SchedulerManager());
        try {
            dashboard.getLogger().info("Initializing SQL Connections");
            dashboard.setSqlUtil(new SqlUtil());
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        dashboard.loadConfiguration();
        dashboard.loadMOTD();
        dashboard.loadServerTypes();
        dashboard.loadJoinServers();
        if (dashboard.isTestNetwork()) {
            dashboard.getLogger().info("Test network detected, disabling statistics collection!");
        } else {
            dashboard.setSocketConnection(new SocketConnection());
        }

        try {
            dashboard.setServerUtil(new ServerUtil());
        } catch (IOException e) {
            e.printStackTrace();
        }
        dashboard.setChatUtil(new ChatUtil());
        dashboard.setCommandUtil(new CommandUtil());
        dashboard.setPartyUtil(new PartyUtil());
        dashboard.setSlackUtil(new SlackUtil());
        dashboard.setWarningUtil(new WarningUtil());
        dashboard.setPasswordUtil(new PasswordUtil());
        try {
            dashboard.setSiteUtil(new SiteUtil());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dashboard.setAfkUtil(new AFKUtil());
        dashboard.setStatUtil(new StatUtil());
        dashboard.setVoteUtil(new VoteUtil());
        try {
            dashboard.setForum(new Forum());
        } catch (Exception e) {
            e.printStackTrace();
        }
        dashboard.setupShowReminder();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                PacketHeartbeat packet = new PacketHeartbeat();
                for (Object o : WebSocketServerHandler.getGroup()) {
                    DashboardSocketChannel dash = (DashboardSocketChannel) o;
                    dash.send(packet);
                }
            }
        }, 10 * 1000, 30 * 1000);
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(DashboardServerSocketChannel.class)
                    .childHandler(new WebSocketServerInitializer());
            Channel ch = b.bind(new InetSocketAddress(dashboard.HOST, dashboard.PORT)).sync().channel();
            dashboard.getLogger().info("Dashboard started at " + dashboard.HOST + ":" + dashboard.PORT);
            dashboard.getSlackUtil().sendDashboardMessage(new SlackMessage(),
                    Collections.singletonList(new SlackAttachment("Dashboard has successfully started up!").color("good")));
            ch.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
