package club.imaginears.dashboard.handlers;

import club.imaginears.dashboard.server.DashboardSocketChannel;

public interface SocketCallback {

    boolean verify(DashboardSocketChannel channel);
}
