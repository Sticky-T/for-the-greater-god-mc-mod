package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Unit;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DivineGearEvents {

    // Hades sneak timer
    private static final Map<UUID, Integer> sneakTime = new HashMap<>();


    public static void register() {

        ServerTickEvents.END_SERVER_TICK.register(server -> {

            for (ServerPlayer player :
                    server.getPlayerList().getPlayers()) {


                checkInventory(player);
                checkArmor(player);
                checkOffhand(player);


                applyPoseidon(player);
                applyHades(player);

            }

        });

    }



    private static void checkInventory(ServerPlayer player) {

        // Protect relics already in inventory
        for (ItemStack stack : player.getInventory().getNonEquipmentItems()) {

            protect(stack);

        }

        // Recover dropped relics nearby
        ServerLevel world = player.level();

        for (ItemEntity item : world.getEntitiesOfClass(
                ItemEntity.class,
                player.getBoundingBox().inflate(8),
                entity -> DivineComponents.isRelic(entity.getItem())
        )) {

            ItemStack stack = item.getItem().copy();

            // Try to put it back
            if (player.getInventory().add(stack)) {

                item.discard();

            }

        }

    }



    private static void checkArmor(Player player) {

        protect(player.getItemBySlot(EquipmentSlot.HEAD));
        protect(player.getItemBySlot(EquipmentSlot.CHEST));
        protect(player.getItemBySlot(EquipmentSlot.LEGS));
        protect(player.getItemBySlot(EquipmentSlot.FEET));

    }



    private static void checkOffhand(Player player) {

        protect(player.getOffhandItem());

    }



    private static void protect(ItemStack stack) {

        if (DivineComponents.isRelic(stack)) {

            stack.set(
                    DataComponents.UNBREAKABLE,
                    Unit.INSTANCE
            );

        }

    }



    // =========================
    // POSEIDON
    // =========================

    private static void applyPoseidon(ServerPlayer player) {


        boolean hasPoseidon = false;


        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        if (DivineComponents.hasGod(helmet, "Poseidon")) {
            hasPoseidon = true;
        }


        if (!hasPoseidon) {
            return;
        }



        if (player.isUnderWater()) {


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.WATER_BREATHING,
                            60,
                            0,
                            false,
                            false
                    )
            );


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.NIGHT_VISION,
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

    private static void applyHades(ServerPlayer player) {


        boolean hasHades = false;


        ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);

        if (DivineComponents.hasGod(helmet, "Hades")) {
            hasHades = true;
        }



        if (!hasHades) {

            sneakTime.remove(player.getUUID());
            return;

        }



        if (player.isShiftKeyDown()) {


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.INVISIBILITY,
                            40,
                            0,
                            false,
                            false
                    )
            );


            sneakTime.put(
                    player.getUUID(),
                    sneakTime.getOrDefault(
                            player.getUUID(),
                            0
                    ) + 1
            );


        } else {


            sneakTime.put(
                    player.getUUID(),
                    0
            );


        }

    }



    public static boolean isHadesCharged(Player player) {

        return sneakTime.getOrDefault(
                player.getUUID(),
                0
        ) >= 200; // 10 seconds (20 ticks/sec)

    }



    public static void resetHades(Player player) {

        sneakTime.put(
                player.getUUID(),
                0
        );

    }

}
