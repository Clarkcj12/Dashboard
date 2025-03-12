package club.imaginears.dashboard.commands.staff;

import club.imaginears.dashboard.handlers.DashboardCommand;
import club.imaginears.dashboard.handlers.Player;
import club.imaginears.dashboard.handlers.Rank;

public class MotionCaptureCommand extends DashboardCommand {

    public MotionCaptureCommand() {
        super(Rank.MOD);
    }

    @Override
    public void execute(Player player, String label, String[] args) {
        StringBuilder str = new StringBuilder();
        for (String s : args) {
            str.append(s).append(" ");
        }
        player.chat("/motioncapture:mc " + str.toString().trim());
    }
}
