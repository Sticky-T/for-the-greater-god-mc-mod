package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DivineDeathEvents {

    private static final Map<UUID, ItemStack[]> savedRelics = new HashMap<>();


    public static void register() {


        // Runs BEFORE the player dies and drops items
        ServerPlayerEvents.COPY_FROM.register(
                (oldPlayer, newPlayer, alive) -> {

                    ItemStack[] relics =
                            savedRelics.remove(
                                    oldPlayer.getUUID()
                            );

                    if (relics != null) {

                        for (ItemStack stack : relics) {

                            if (!stack.isEmpty()) {

                                newPlayer.addItem(
                                        stack
                                );

                            }
                        }
                    }
                }
        );



        ServerPlayerEvents.AFTER_RESPAWN.register(
                (oldPlayer, newPlayer, alive) -> {

                    restoreRelics(
                            oldPlayer,
                            newPlayer
                    );

                }
        );

    }



    private static void restoreRelics(
            ServerPlayer oldPlayer,
            ServerPlayer newPlayer
    ) {

        ItemStack[] relics =
                savedRelics.remove(
                        oldPlayer.getUUID()
                );


        if (relics == null) {
            return;
        }


        for (ItemStack stack : relics) {

            if (!stack.isEmpty()) {

                newPlayer.addItem(
                        stack
                );

            }

        }

    }


}