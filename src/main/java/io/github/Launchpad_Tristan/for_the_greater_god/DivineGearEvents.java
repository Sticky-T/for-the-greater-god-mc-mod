package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DivineGearEvents {

    // Hades sneak timer
    private static final Map<UUID, Integer> sneakTime = new HashMap<>();


    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayerEntity player :
                    server.getPlayerManager().getPlayerList()) {


                checkInventory(player);
                checkArmor(player);
                checkOffhand(player);


                applyPoseidon(player);
                applyHades(player);

            }

        });

    }



    private static void checkInventory(PlayerEntity player) {

        for (ItemStack stack :
                player.getInventory().getMainStacks()) {

            protect(stack);

        }

    }



    private static void checkArmor(PlayerEntity player) {

        protect(player.getEquippedStack(EquipmentSlot.HEAD));
        protect(player.getEquippedStack(EquipmentSlot.CHEST));
        protect(player.getEquippedStack(EquipmentSlot.LEGS));
        protect(player.getEquippedStack(EquipmentSlot.FEET));

    }



    private static void checkOffhand(PlayerEntity player) {

        protect(player.getOffHandStack());

    }



    private static void protect(ItemStack stack) {

        if (DivineComponents.isRelic(stack)) {

            stack.set(
                    DataComponentTypes.UNBREAKABLE,
                    Unit.INSTANCE
            );

        }

    }



    // =========================
    // POSEIDON
    // =========================

    private static void applyPoseidon(ServerPlayerEntity player) {


        boolean hasPoseidon = false;


        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        if (DivineComponents.hasGod(helmet, "Poseidon")) {
            hasPoseidon = true;
        }


        if (!hasPoseidon) {
            return;
        }



        if (player.isSubmergedInWater()) {


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.WATER_BREATHING,
                            60,
                            0,
                            false,
                            false
                    )
            );


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.NIGHT_VISION,
                            60,
                            0,
                            false,
                            false
                    )
            );

        }

    }





    // =========================
    // HADES
    // =========================

    private static void applyHades(ServerPlayerEntity player) {


        boolean hasHades = false;


        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);

        if (DivineComponents.hasGod(helmet, "Hades")) {
            hasHades = true;
        }



        if (!hasHades) {

            sneakTime.remove(player.getUuid());
            return;

        }



        if (player.isSneaking()) {


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.INVISIBILITY,
                            40,
                            0,
                            false,
                            false
                    )
            );


            sneakTime.put(
                    player.getUuid(),
                    sneakTime.getOrDefault(
                            player.getUuid(),
                            0
                    ) + 1
            );


        } else {


            sneakTime.put(
                    player.getUuid(),
                    0
            );


        }

    }



    public static boolean isHadesCharged(PlayerEntity player) {

        return sneakTime.getOrDefault(
                player.getUuid(),
                0
        ) >= 200; // 10 seconds (20 ticks/sec)

    }



    public static void resetHades(PlayerEntity player) {

        sneakTime.put(
                player.getUuid(),
                0
        );

    }

}
