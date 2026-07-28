package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class TeamEvents {

    public static void initialize() {

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {

            ServerPlayerEntity player = handler.player;

            // Wait one tick so the player is fully loaded
            server.execute(() -> giveTeamGear(player));
        });
    }

    private static void giveTeamGear(ServerPlayerEntity player) {

        ServerWorld world = player.getServer().getOverworld();

        TeamData data = TeamData.get(world);

        String team = data.getTeam(player.getUuid());

        if (team == null) {
            return;
        }

        boolean leader = data.isLeader(team, player.getUuid());

        TeamGear.giveGear(
                player,
                team,
                leader
        );
    }
}
