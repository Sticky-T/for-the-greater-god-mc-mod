package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TeamGear {


    public static void giveGear(
            ServerPlayer player,
            String team,
            boolean leader
    ) {

        removeGear(player);


        switch(team) {


            // =========================
            // ZEUS
            // =========================
            case "Zeus" -> {

                ItemStack sword =
                        new ItemStack(
                                leader
                                        ? Items.NETHERITE_SWORD
                                        : Items.DIAMOND_SWORD
                        );


                DivineComponents.setAbility(
                        sword,
                        "Zeus",
                        "Thunderstruck",
                        leader ? 3 : 1
                );


                player.addItem(
                        sword
                );
            }



            // =========================
            // POSEIDON
            // =========================
            case "Poseidon" -> {

                ItemStack helmet =
                        new ItemStack(
                                leader
                                        ? Items.NETHERITE_HELMET
                                        : Items.DIAMOND_HELMET
                        );


                DivineComponents.setAbility(
                        helmet,
                        "Poseidon",
                        "Ocean's Grace",
                        leader ? 3 : 1
                );


                player.setItemSlot(
                        EquipmentSlot.HEAD,
                        helmet
                );
            }



            // =========================
            // HADES
            // =========================
            case "Hades" -> {

                ItemStack helmet =
                        new ItemStack(
                                leader
                                        ? Items.NETHERITE_HELMET
                                        : Items.DIAMOND_HELMET
                        );


                DivineComponents.setAbility(
                        helmet,
                        "Hades",
                        "Deathly Presence",
                        leader ? 3 : 1
                );


                player.setItemSlot(
                        EquipmentSlot.HEAD,
                        helmet
                );
            }



            // =========================
            // KRONOS
            // =========================
            case "Cronus" -> {

                ItemStack scythe;


                if (leader) {

                    scythe =
                            new ItemStack(
                                    ModItems.NETHERITE_SCYTHE
                            );


                    DivineComponents.setAbility(
                            scythe,
                            "Cronus",
                            "Unholy Edge",
                            3
                    );

                } else {

                    scythe =
                            new ItemStack(
                                    ModItems.IRON_SCYTHE
                            );


                    DivineComponents.setAbility(
                            scythe,
                            "Cronus",
                            "Sharpness",
                            3
                    );

                }


                player.addItem(
                        scythe
                );
            }
        }


        player.sendSystemMessage(
                Component.literal(
                                "Your divine gear has been granted."
                        )
                        .withStyle(
                                ChatFormatting.GOLD
                        ),
                false
        );
    }





    public static void removeGear(
            ServerPlayer player
    ) {


        // Inventory items
        for(ItemStack stack :
                player.getInventory().getNonEquipmentItems()) {


            if(DivineComponents.isRelic(stack)) {

                stack.setCount(0);

            }
        }



        // Armor slots
        EquipmentSlot[] armorSlots = {

                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET

        };


        for(EquipmentSlot slot : armorSlots) {


            ItemStack stack =
                    player.getItemBySlot(slot);


            if(DivineComponents.isRelic(stack)) {

                player.setItemSlot(
                        slot,
                        ItemStack.EMPTY
                );

            }
        }



        // Offhand
        ItemStack offhand =
                player.getOffhandItem();


        if(DivineComponents.isRelic(offhand)) {

            player.setItemInHand(
                    net.minecraft.world.InteractionHand.OFF_HAND,
                    ItemStack.EMPTY
            );

        }

    }

}