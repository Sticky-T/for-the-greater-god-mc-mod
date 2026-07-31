package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class BaseProtection {


    public static void tick(MinecraftServer server) {

        for (ServerWorld world : server.getWorlds()) {

            // Bases only exist in overworld
            if (!world.getRegistryKey().equals(ServerWorld.OVERWORLD)) {
                continue;
            }


            TeamData data = TeamData.get(world);


            for (ServerPlayerEntity player : world.getPlayers()) {

                checkPlayer(player, data);

            }
        }
    }



    private static void checkPlayer(
            ServerPlayerEntity player,
            TeamData data
    ) {

        String playerTeam = null;


        if (FTGGUtils.getPlayerTeam(player) != null) {

            playerTeam =
                    FTGGUtils.getPlayerTeam(player)
                            .getName();
        }



        for (Map.Entry<String, net.minecraft.util.math.BlockPos> entry :
                getBases(data).entrySet()) {


            String baseTeam = entry.getKey();

            BlockPos base = entry.getValue();



            // Ignore own team's base
            if (baseTeam.equalsIgnoreCase(playerTeam)) {
                continue;
            }



            double distance =
                    player.getPos()
                            .distanceTo(
                                    base.toCenterPos()
                            );


            if (distance <= 50) {


                player.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                40,
                                1,
                                false,
                                false
                        )
                );


                player.sendMessage(
                        Text.literal(
                                "You are entering "
                                        + baseTeam
                                        + "'s territory!"
                        ).formatted(
                                Formatting.RED
                        ),
                        true
                );


                break;
            }
        }
    }



    /*
     * Access all bases stored in TeamData.
     * This needs a getter in TeamData.
     */
    private static Map<String, BlockPos> getBases(
            TeamData data
    ) {

        return data.getAllBases();

    }
}
