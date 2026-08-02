package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import java.util.Map;

public class BaseProtection {


    public static void tick(MinecraftServer server) {

        for (ServerLevel world : server.getAllLevels()) {

            // Bases only exist in overworld
            if (!world.dimension().equals(ServerLevel.OVERWORLD)) {
                continue;
            }


            TeamData data = TeamData.get(world);


            for (ServerPlayer player : world.players()) {

                checkPlayer(player, data);

            }
        }
    }



    private static void checkPlayer(
            ServerPlayer player,
            TeamData data
    ) {

        String playerTeam = null;


        if (FTGGUtils.getPlayerTeam(player) != null) {

            playerTeam =
                    FTGGUtils.getPlayerTeam(player)
                            .getName();
        }



        for (Map.Entry<String, net.minecraft.core.BlockPos> entry :
                getBases(data).entrySet()) {


            String baseTeam = entry.getKey();

            BlockPos base = entry.getValue();



            // Ignore own team's base
            if (baseTeam.equalsIgnoreCase(playerTeam)) {
                continue;
            }



            double distance =
                    player.position()
                            .distanceTo(
                                    base.getCenter()
                            );


            if (distance <= 50) {


                player.addEffect(
                        new MobEffectInstance(
                                MobEffects.SLOWNESS,
                                40,
                                1,
                                false,
                                false
                        )
                );


                player.sendSystemMessage(
                        Component.literal(
                                "You are entering "
                                        + baseTeam
                                        + "'s territory!"
                        ).withStyle(
                                ChatFormatting.RED
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
