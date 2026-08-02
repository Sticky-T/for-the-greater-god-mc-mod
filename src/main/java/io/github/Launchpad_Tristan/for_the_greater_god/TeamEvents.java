package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class TeamEvents {

    public static void initialize() {

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

            ServerPlayer player = handler.player;

            // Wait one tick so the player is fully loaded
            server.execute(() -> giveTeamGear(player));
        });
    }

    private static void giveTeamGear(ServerPlayer player) {

        ServerLevel world = player.level().getServer().overworld();

        TeamData data = TeamData.get(world);

        String team = data.getTeam(player.getUUID());

        if (team == null) {
            return;
        }

        boolean leader = data.isLeader(team, player.getUUID());

        TeamGear.giveGear(
                player,
                team,
                leader
        );
    }
}
