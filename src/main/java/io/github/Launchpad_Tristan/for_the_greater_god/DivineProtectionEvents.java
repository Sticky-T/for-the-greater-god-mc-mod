package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class DivineProtectionEvents {


    public static void register() {


        ServerPlayerEvents.COPY_FROM.register(
                (oldPlayer, newPlayer, alive) -> {


                    List<ItemStack> relics =
                            new ArrayList<>();


                    collectRelics(
                            oldPlayer,
                            relics
                    );


                    newPlayer.level().getServer().execute(() -> {

                        for (ItemStack stack : relics) {

                            newPlayer.addItem(
                                    stack
                            );

                        }

                    });

                }
        );

    }





    private static void collectRelics(
            ServerPlayer player,
            List<ItemStack> output
    ) {


        // Inventory

        for (ItemStack stack :
                player.getInventory().getNonEquipmentItems()) {


            if (DivineComponents.isRelic(stack)) {

                output.add(
                        stack.copy()
                );

            }

        }



        // Armor

        EquipmentSlot[] slots = {

                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET

        };


        for (EquipmentSlot slot : slots) {


            ItemStack stack =
                    player.getItemBySlot(slot);


            if (DivineComponents.isRelic(stack)) {

                output.add(
                        stack.copy()
                );

            }

        }



        // Offhand

        ItemStack offhand =
                player.getOffhandItem();


        if (DivineComponents.isRelic(offhand)) {

            output.add(
                    offhand.copy()
            );

        }

    }

}