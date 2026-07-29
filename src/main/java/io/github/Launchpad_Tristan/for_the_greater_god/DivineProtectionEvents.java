package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

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


                    newPlayer.getServer().execute(() -> {

                        for (ItemStack stack : relics) {

                            newPlayer.giveItemStack(
                                    stack
                            );

                        }

                    });

                }
        );

    }





    private static void collectRelics(
            ServerPlayerEntity player,
            List<ItemStack> output
    ) {


        // Inventory

        for (ItemStack stack :
                player.getInventory().getMainStacks()) {


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
                    player.getEquippedStack(slot);


            if (DivineComponents.isRelic(stack)) {

                output.add(
                        stack.copy()
                );

            }

        }



        // Offhand

        ItemStack offhand =
                player.getOffHandStack();


        if (DivineComponents.isRelic(offhand)) {

            output.add(
                    offhand.copy()
            );

        }

    }

}