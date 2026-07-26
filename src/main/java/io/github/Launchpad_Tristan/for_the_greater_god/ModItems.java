package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final RegistryKey<Item> DIVINE_GEAR_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "divine_gear")
            );

    public static final RegistryKey<Item> NETHERITE_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "netherite_scythe")
            );

    public static final RegistryKey<Item> IRON_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "iron_scythe")
            );


    public static final Item DIVINE_GEAR = Registry.register(
            Registries.ITEM,
            DIVINE_GEAR_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    new Item.Settings()
                            .registryKey(DIVINE_GEAR_KEY)
            )
    );


    public static final Item NETHERITE_SCYTHE = Registry.register(
            Registries.ITEM,
            NETHERITE_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    new Item.Settings()
                            .registryKey(NETHERITE_SCYTHE_KEY)
            )
    );


    public static final Item IRON_SCYTHE = Registry.register(
            Registries.ITEM,
            IRON_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.IRON,
                    new Item.Settings()
                            .registryKey(IRON_SCYTHE_KEY)
            )
    );


    public static void initialize() {
    }
}
