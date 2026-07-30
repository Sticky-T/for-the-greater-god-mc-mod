package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class TeamGear {


    public static void giveGear(
            ServerPlayerEntity player,
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


                player.giveItemStack(
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


                player.equipStack(
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


                player.equipStack(
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
                            "Kronos",
                            "Sharpness",
                            3
                    );

                }


                player.giveItemStack(
                        scythe
                );
            }
        }


        player.sendMessage(
                Text.literal(
                                "Your divine gear has been granted."
                        )
                        .formatted(
                                Formatting.GOLD
                        ),
                false
        );
    }





    public static void removeGear(
            ServerPlayerEntity player
    ) {


        // Inventory items
        for(ItemStack stack :
                player.getInventory().getMainStacks()) {


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
                    player.getEquippedStack(slot);


            if(DivineComponents.isRelic(stack)) {

                player.equipStack(
                        slot,
                        ItemStack.EMPTY
                );

            }
        }



        // Offhand
        ItemStack offhand =
                player.getOffHandStack();


        if(DivineComponents.isRelic(offhand)) {

            player.setStackInHand(
                    net.minecraft.util.Hand.OFF_HAND,
                    ItemStack.EMPTY
            );

        }

    }

}