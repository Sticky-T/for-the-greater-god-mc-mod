package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.UUID;

public class FTGGUtils {


    /**
     * Gets the player's current god team.
     */
    public static Team getPlayerTeam(ServerPlayerEntity player) {

        Scoreboard scoreboard =
                player.getServer()
                        .getScoreboard();

        return scoreboard.getScoreHolderTeam(
                player.getNameForScoreboard()
        );

    }



    /**
     * Checks if a player belongs to a specific team.
     */
    public static boolean isOnTeam(
            ServerPlayerEntity player,
            String teamName
    ) {

        Team team = getPlayerTeam(player);

        return team != null
                && team.getName().equalsIgnoreCase(teamName);
    }



    /**
     * Checks if a player is the leader of a team.
     */
    public static boolean isTeamLeader(
            ServerPlayerEntity player,
            String teamName
    ) {

        ServerWorld world =
                player.getWorld();

        TeamData data =
                TeamData.get(world);


        UUID leader =
                data.getLeader(teamName);


        return leader != null
                && leader.equals(player.getUuid());
    }



    /**
     * Sends formatted messages.
     */
    public static void sendMessage(
            ServerPlayerEntity player,
            String message,
            Formatting color
    ) {

        player.sendMessage(
                Text.literal(message)
                        .formatted(color),
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
    public static boolean isOverworld(ServerPlayerEntity player) {

        return player.getWorld()
                .getRegistryKey()
                .equals(ServerWorld.OVERWORLD);
    }
}
