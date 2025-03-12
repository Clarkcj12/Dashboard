package club.imaginears.dashboard.discordSocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DiscordDatabaseInfo {

    @Getter private String minecraftUsername = "";
    @Getter private String minecraftUUID = "";
    @Getter private String discordUsername = "";

}
