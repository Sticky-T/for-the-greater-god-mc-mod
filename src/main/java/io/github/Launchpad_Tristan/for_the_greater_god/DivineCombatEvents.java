package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DivineCombatEvents {


    private static final Map<UUID, Integer> kronosHits =
            new HashMap<>();



    public static void register() {


        AttackEntityCallback.EVENT.register(
                (player, world, hand, target, hitResult) -> {


                    if (world.isClientSide()) {
                        return InteractionResult.PASS;
                    }


                    if (!(target instanceof LivingEntity living)) {
                        return InteractionResult.PASS;
                    }



                    ItemStack weapon =
                            player.getItemInHand(hand);



                    // =========================
// ZEUS - THUNDERSTRUCK
// =========================

                    if (DivineComponents.hasAbility(
                            weapon,
                            "Thunderstruck"
                    )) {

                        if (player.getRandom().nextFloat() <= 0.33f) {

                            if (world instanceof net.minecraft.server.level.ServerLevel serverWorld) {

                                LightningBolt lightning =
                                        new LightningBolt(
                                                EntityType.LIGHTNING_BOLT,
                                                serverWorld
                                        );

                                lightning.snapTo(
                                        living.getX(),
                                        living.getY(),
                                        living.getZ()
                                );

                                serverWorld.addFreshEntity(lightning);

                            }

                        }

                    }




                    // =========================
                    // HADES - DEATHLY PRESENCE
                    // =========================

                    ItemStack helmet =
                            player.getItemBySlot(
                                    net.minecraft.world.entity.EquipmentSlot.HEAD
                            );


                    if (DivineComponents.hasGod(
                            helmet,
                            "Hades"
                    )) {


                        if (DivineGearEvents.isHadesCharged(player)) {


                            if (player.level() instanceof ServerLevel serverWorld) {

                                living.hurtServer(
                                        serverWorld,
                                        player.damageSources().playerAttack(player),
                                        10.0F
                                );

                            }



                            DivineGearEvents.resetHades(player);

                        }

                    }





                    // =========================
                    // KRONOS - UNHOLY EDGE
                    // =========================

                    if (DivineComponents.hasAbility(
                            weapon,
                            "Unholy Edge"
                    )) {


                        UUID uuid =
                                player.getUUID();


                        int hits =
                                kronosHits.getOrDefault(
                                        uuid,
                                        0
                                ) + 1;



                        if (hits >= 4) {


                            if (player.level() instanceof ServerLevel serverWorld) {

                                living.hurtServer(
                                        serverWorld,
                                        player.damageSources().playerAttack(player),
                                        10.0F
                                );

                            }


                            hits = 0;

                        }



                        kronosHits.put(
                                uuid,
                                hits
                        );

                    }


                    return InteractionResult.PASS;

                }
        );

    }

}