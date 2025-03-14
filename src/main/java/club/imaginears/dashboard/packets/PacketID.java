package club.imaginears.dashboard.packets;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Marc on 6/15/15
 */
@AllArgsConstructor
public enum PacketID {
    HEARTBEAT(0), LOGIN(1), KICK(2), GLOBAL_PLAY_ONCE(3), AREA_START(4), AREA_STOP(5), CLIENT_ACCEPTED(6), AUDIO_SYNC(7),
    NOTIFICATION(8), EXEC_SCRIPT(9), COMPUTER_SPEAK(10), INCOMING_WARP(11), SERVER_SWITCH(12), GETPLAYER(13),
    PLAYERINFO(14), CONTAINER(17);

    @Getter final int ID;

    @AllArgsConstructor
    public enum Dashboard {
        STATUSREQUEST(18), SERVERSTATUS(19), REMOVED_STAFFLISTCOMMAND(20), REMOVED_LISTFRIENDCOMMAND(21), CONNECTIONTYPE(22),
        PLAYERJOIN(23), PLAYERDISCONNECT(24), PLAYERCHAT(25), MESSAGE(26), SERVERSWITCH(27), PLAYERRANK(28),
        STARTREBOOT(29), REMOVED_LISTREQUESTCOMMAND(30), REMOVED_FRIENDREQUEST(31), SENDTOSERVER(32), UPDATEMOTD(33),
        REMOVED_BSEENCOMMAND(34), SERVERLIST(35), REMOVESERVER(36), ADDSERVER(37), TARGETLOBBY(38), REMOVED_JOINCOMMAND(39),
        UPTIMECOMMAND(40), ONLINECOUNT(41), REMOVED_AUDIOCOMMAND(42), TABCOMPLETE(43), COMMANDLIST(44), REMOVED_IPSEENCOMMAND(45),
        MAINTENANCE(46), MAINTENANCELIST(47), SETPACK(48), GETPACK(49), MENTION(50), AUDIOCONNECT(51), SERVERNAME(52),
        LINK(53), WDLPROTECT(54), RANKCHANGE(55), WARNING(56), EMPTYSERVER(57), REMOVED_PARTYREQUEST(58), LOBBYDATA(59),
        TITLE(60), PLAYERLIST(63), UPDATEECONOMY(67), CONFIRMPLAYER(68), DISABLEPLAYER(69), IGNORELIST(72),
        LOG_STATISTIC(75);

        @Getter final int ID;
    }

    @AllArgsConstructor
    public enum Park {
        WARP(56), REFRESHHOTELS(59), BROADCAST(60), MUTECHAT(61), REFRESHWARPS(62), IMAPARK(71),
        SHOW_REQUEST(77), SHOW_REQUEST_RESPONSE(78), SHOW_START(79), SHOW_STOP(80), CREATE_QUEUE(81),
        REMOVE_QUEUE(82), UPDATE_QUEUE(83), ADMIT_QUEUE(84), ANNOUNCE_QUEUE(85), PLAYER_QUEUE(86);

        @Getter final int ID;
    }

    @AllArgsConstructor
    public enum Arcade {
        GAMESTATUS(64);
        @Getter final int ID;
    }

    @AllArgsConstructor
    public enum Bungee {
        BUNGEEID(65), PLAYERLISTINFO(66), SERVERICON(70), ONLINEPLAYERLIST(73), UPDATEBUNGEECONFIG(74),
        COMPONENT_MESSAGE(76);
        @Getter final int ID;
    }

    @AllArgsConstructor
    public enum Inventory {
        INVENTORY_CONTENT(58);
        @Getter private final int ID;
    }
}