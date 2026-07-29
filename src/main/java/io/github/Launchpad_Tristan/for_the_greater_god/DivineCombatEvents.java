package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DivineCombatEvents {


    private static final Map<UUID, Integer> kronosHits =
            new HashMap<>();



    public static void register() {


        AttackEntityCallback.EVENT.register(
                (player, world, hand, target, hitResult) -> {


                    if (world.isClient()) {
                        return ActionResult.PASS;
                    }


                    if (!(target instanceof LivingEntity living)) {
                        return ActionResult.PASS;
                    }



                    ItemStack weapon =
                            player.getStackInHand(hand);



                    // =========================
// ZEUS - THUNDERSTRUCK
// =========================

                    if (DivineComponents.hasAbility(
                            weapon,
                            "Thunderstruck"
                    )) {

                        if (player.getRandom().nextFloat() <= 0.33f) {

                            if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {

                                LightningEntity lightning =
                                        new LightningEntity(
                                                EntityType.LIGHTNING_BOLT,
                                                serverWorld
                                        );

                                lightning.refreshPositionAfterTeleport(
                                        living.getX(),
                                        living.getY(),
                                        living.getZ()
                                );

                                serverWorld.spawnEntity(lightning);

                            }

                        }

                    }




                    // =========================
                    // HADES - DEATHLY PRESENCE
                    // =========================

                    ItemStack helmet =
                            player.getEquippedStack(
                                    net.minecraft.entity.EquipmentSlot.HEAD
                            );


                    if (DivineComponents.hasGod(
                            helmet,
                            "Hades"
                    )) {


                        if (DivineGearEvents.isHadesCharged(player)) {


                            if (player.getWorld() instanceof ServerWorld serverWorld) {

                                living.damage(
                                        serverWorld,
                                        player.getDamageSources().playerAttack(player),
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
                                player.getUuid();


                        int hits =
                                kronosHits.getOrDefault(
                                        uuid,
                                        0
                                ) + 1;



                        if (hits >= 4) {


                            if (player.getWorld() instanceof ServerWorld serverWorld) {

                                living.damage(
                                        serverWorld,
                                        player.getDamageSources().playerAttack(player),
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


                    return ActionResult.PASS;

                }
        );

    }

}