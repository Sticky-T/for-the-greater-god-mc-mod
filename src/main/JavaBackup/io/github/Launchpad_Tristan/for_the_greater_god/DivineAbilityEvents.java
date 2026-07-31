package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;

import java.util.HashMap;
import java.util.UUID;


public class DivineAbilityEvents {


    private static final HashMap<UUID, Integer> kronosHits =
            new HashMap<>();


    private static final HashMap<UUID, Long> sneakStart =
            new HashMap<>();



    public static void register() {


        // Combat abilities
        AttackEntityCallback.EVENT.register(
                (player, world, hand, entity, hitResult) -> {


                    if (!(entity instanceof LivingEntity living)) {
                        return ActionResult.PASS;
                    }


                    if (world.isClient()) {
                        return ActionResult.PASS;
                    }



                    handleCombat(
                            player,
                            living
                    );


                    return ActionResult.PASS;

                }
        );




        // Passive effects
        ServerTickEvents.END_SERVER_TICK.register(server -> {


            for(ServerPlayerEntity player :
                    server.getPlayerManager().getPlayerList()) {


                handlePoseidon(player);

                handleHades(player);

            }

        });

    }





    private static void handleCombat(
            PlayerEntity player,
            LivingEntity target
    ) {


        var stack =
                player.getMainHandStack();


        if(!DivineComponents.isRelic(stack)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(stack);



        // =====================
        // ZEUS
        // =====================

        if(data.god().equals("Zeus")) {


            if(Random.create().nextFloat() < 0.5f) {
                ServerWorld world =
                        (ServerWorld) player.getWorld();


                LightningEntity lightning =
                        new LightningEntity(
                                EntityType.LIGHTNING_BOLT,
                                world
                        );


                lightning.refreshPositionAfterTeleport(
                        target.getX(),
                        target.getY(),
                        target.getZ()
                );


                world.spawnEntity(
                        lightning
                );


            }

        }





        // =====================
        // KRONOS
        // =====================

        if(data.god().equals("Cronus")) {


            int hits =
                    kronosHits.getOrDefault(
                            player.getUuid(),
                            0
                    ) + 1;


            if(hits >= 4) {


                target.addStatusEffect(
                        new StatusEffectInstance(
                                StatusEffects.SLOWNESS,
                                100,
                                1
                        )
                );


                target.damage(
                        (ServerWorld) player.getWorld(),
                        player.getDamageSources().playerAttack(player),
                        10.0f
                );

                hits = 0;

            }


            kronosHits.put(
                    player.getUuid(),
                    hits
            );

        }


    }






    private static void handlePoseidon(
            ServerPlayerEntity player
    ) {


        var helmet =
                player.getEquippedStack(
                        net.minecraft.entity.EquipmentSlot.HEAD
                );


        if(!DivineComponents.isRelic(helmet)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(helmet);


        if(!data.god().equals("Poseidon")) {
            return;
        }



        if(player.isSubmergedInWater()) {


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.WATER_BREATHING,
                            40,
                            0,
                            false,
                            false
                    )
            );


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.NIGHT_VISION,
                            40,
                            0,
                            false,
                            false
                    )
            );

            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.HASTE,
                            40,
                            3,
                            false,
                            false
                    )
            );
        }

    }





    private static void handleHades(
            ServerPlayerEntity player
    ) {


        var helmet =
                player.getEquippedStack(
                        net.minecraft.entity.EquipmentSlot.HEAD
                );


        if(!DivineComponents.isRelic(helmet)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(helmet);


        if(!data.god().equals("Hades")) {
            return;
        }



        if(player.isSneaking()) {


            player.addStatusEffect(
                    new StatusEffectInstance(
                            StatusEffects.INVISIBILITY,
                            40,
                            0,
                            false,
                            false
                    )
            );

        }

    }

}