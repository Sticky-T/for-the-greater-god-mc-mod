package io.github.Launchpad_Tristan.for_the_greater_god;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;
import java.util.UUID;


public class DivineAbilityEvents {


    private static final HashMap<UUID, Integer> kronosHits =
            new HashMap<>();


    private static final HashMap<UUID, Integer> sneakTimer =
            new HashMap<>();



    public static void register() {


        // Combat abilities
        AttackEntityCallback.EVENT.register(
                (player, world, hand, entity, hitResult) -> {


                    if (!(entity instanceof LivingEntity living)) {
                        return InteractionResult.PASS;
                    }


                    if (world.isClientSide()) {
                        return InteractionResult.PASS;
                    }



                    handleCombat(
                            player,
                            living
                    );


                    return InteractionResult.PASS;

                }
        );




        // Passive effects
        ServerTickEvents.END_SERVER_TICK.register(server -> {


            for(ServerPlayer player :
                    server.getPlayerList().getPlayers()) {


                handlePoseidon(player);

                handleHades(player);

            }

        });

    }





    private static void handleCombat(
            Player player,
            LivingEntity target
    ) {


        var stack =
                player.getMainHandItem();


        if(!DivineComponents.isRelic(stack)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(stack);



        // =====================
        // ZEUS
        // =====================

        if(data.god().equals("Zeus")) {


            if(RandomSource.create().nextFloat() < 0.5f) {
                ServerLevel world =
                        (ServerLevel) player.level();


                LightningBolt lightning =
                        new LightningBolt(
                                EntityType.LIGHTNING_BOLT,
                                world
                        );


                lightning.snapTo(
                        target.getX(),
                        target.getY(),
                        target.getZ()
                );


                world.addFreshEntity(
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
                            player.getUUID(),
                            0
                    ) + 1;


            if(hits >= 4) {


                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.SLOWNESS,
                                100,
                                1
                        )
                );


                target.hurtServer(
                        (ServerLevel) player.level(),
                        player.damageSources().playerAttack(player),
                        10.0f
                );

                hits = 0;

            }


            kronosHits.put(
                    player.getUUID(),
                    hits
            );

        }

        // =====================
        // HADES
        // =====================

        if(data.god().equals("Hades")) {


            int sneakTicks =
                    sneakTimer.getOrDefault(
                            player.getUUID(),
                            0
                    );


            if(sneakTicks >= 100) {


                target.addEffect(
                        new MobEffectInstance(
                                MobEffects.BLINDNESS,
                                100,
                                1
                        )
                );


                target.hurtServer(
                        (ServerLevel) player.level(),
                        player.damageSources().playerAttack(player),
                        10.0f
                );
            }

            sneakTicks = 0;

            sneakTimer.put(
                    player.getUUID(),
                    sneakTicks
            );

        }


    }






    private static void handlePoseidon(
            ServerPlayer player
    ) {


        var helmet =
                player.getItemBySlot(
                        net.minecraft.world.entity.EquipmentSlot.HEAD
                );


        if(!DivineComponents.isRelic(helmet)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(helmet);


        if(!data.god().equals("Poseidon")) {
            return;
        }



        if(player.isUnderWater()) {


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.WATER_BREATHING,
                            40,
                            0,
                            false,
                            false
                    )
            );


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.NIGHT_VISION,
                            40,
                            0,
                            false,
                            false
                    )
            );

            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.HASTE,
                            40,
                            3,
                            false,
                            false
                    )
            );
        }

    }





    private static void handleHades(
            ServerPlayer player
    ) {


        var helmet =
                player.getItemBySlot(
                        net.minecraft.world.entity.EquipmentSlot.HEAD
                );


        if(!DivineComponents.isRelic(helmet)) {
            return;
        }


        DivineRelicData data =
                DivineComponents.getData(helmet);


        if(!data.god().equals("Hades")) {
            return;
        }



        if(player.isShiftKeyDown()) {


            player.addEffect(
                    new MobEffectInstance(
                            MobEffects.INVISIBILITY,
                            40,
                            0,
                            false,
                            false
                    )
            );

            int sneakTicks =
                    sneakTimer.getOrDefault(
                            player.getUUID(),
                            0
                    ) + 1;

            sneakTimer.put(
                    player.getUUID(),
                    sneakTicks
            );
        }

    }

}