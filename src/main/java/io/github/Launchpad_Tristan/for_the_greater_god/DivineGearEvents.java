package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Unit;

public class DivineGearEvents {


    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for(PlayerEntity player :
                    server.getPlayerManager().getPlayerList()) {

                checkInventory(player);
                checkArmor(player);
                checkOffhand(player);

            }

        });

    }



    private static void checkInventory(PlayerEntity player) {

        for(ItemStack stack :
                player.getInventory().getMainStacks()) {

            protect(stack);

        }

    }



    private static void checkArmor(PlayerEntity player) {

        protect(
                player.getEquippedStack(
                        EquipmentSlot.HEAD
                )
        );

        protect(
                player.getEquippedStack(
                        EquipmentSlot.CHEST
                )
        );

        protect(
                player.getEquippedStack(
                        EquipmentSlot.LEGS
                )
        );

        protect(
                player.getEquippedStack(
                        EquipmentSlot.FEET
                )
        );

    }



    private static void checkOffhand(PlayerEntity player) {

        protect(
                player.getOffHandStack()
        );

    }



    private static void protect(ItemStack stack) {

        if(DivineComponents.isRelic(stack)) {

            stack.set(
                    DataComponentTypes.UNBREAKABLE,
                    Unit.INSTANCE
            );

        }

    }

}