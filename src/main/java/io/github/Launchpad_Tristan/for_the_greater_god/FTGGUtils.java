package io.github.Launchpad_Tristan.for_the_greater_god;

import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public class FTGGUtils {


    /**
     * Gets the player's current god team.
     */
    public static PlayerTeam getPlayerTeam(ServerPlayer player) {

        Scoreboard scoreboard =
                player.level().getServer()
                        .getScoreboard();

        return scoreboard.getPlayersTeam(
                player.getScoreboardName()
        );

    }



    /**
     * Checks if a player belongs to a specific team.
     */
    public static boolean isOnTeam(
            ServerPlayer player,
            String teamName
    ) {

        PlayerTeam team = getPlayerTeam(player);

        return team != null
                && team.getName().equalsIgnoreCase(teamName);
    }



    /**
     * Checks if a player is the leader of a team.
     */
    public static boolean isTeamLeader(
            ServerPlayer player,
            String teamName
    ) {

        ServerLevel world =
                player.level();

        TeamData data =
                TeamData.get(world);


        UUID leader =
                data.getLeader(teamName);


        return leader != null
                && leader.equals(player.getUUID());
    }



    /**
     * Sends formatted messages.
     */
    public static void sendMessage(
            ServerPlayer player,
            String message,
            ChatFormatting color
    ) {

        player.sendSystemMessage(
                Component.literal(message)
                        .withStyle(color),
                false
        );
    }



    /**
     * Checks distance between two positions.
     */
    public static boolean withinDistance(
            double x1,
            double y1,
            double z1,
            double x2,
            double y2,
            double z2,
            double distance
    ) {

        double dx = x1 - x2;
        double dy = y1 - y2;
        double dz = z1 - z2;


        return (dx * dx + dy * dy + dz * dz)
                <= distance * distance;
    }



    /**
     * Checks if a player is in the overworld.
     */
    public static boolean isOverworld(ServerPlayer player) {

        return player.level()
                .dimension()
                .equals(ServerLevel.OVERWORLD);
    }
}
