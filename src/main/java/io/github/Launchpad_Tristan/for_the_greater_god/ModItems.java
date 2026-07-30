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

    public static final RegistryKey<Item> WOODEN_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "wooden_scythe")
            );

    public static final RegistryKey<Item> STONE_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "stone_scythe")
            );

    public static final RegistryKey<Item> GOLDEN_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "golden_scythe")
            );

    public static final RegistryKey<Item> DIAMOND_SCYTHE_KEY =
            RegistryKey.of(
                    RegistryKeys.ITEM,
                    Identifier.of(For_the_greater_god.MOD_ID, "diamond_scythe")
            );


    public static final Item DIVINE_GEAR = Registry.register(
            Registries.ITEM,
            DIVINE_GEAR_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    20.0f,
                    new Item.Settings()
                            .registryKey(DIVINE_GEAR_KEY)
            )
    );


    public static final Item NETHERITE_SCYTHE = Registry.register(
            Registries.ITEM,
            NETHERITE_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    9.0f,
                    new Item.Settings()
                            .registryKey(NETHERITE_SCYTHE_KEY)
            )
    );


    public static final Item IRON_SCYTHE = Registry.register(
            Registries.ITEM,
            IRON_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.IRON,
                    5.0f,
                    new Item.Settings()
                            .registryKey(IRON_SCYTHE_KEY)
            )
    );

    public static final Item WOODEN_SCYTHE = Registry.register(
            Registries.ITEM,
            WOODEN_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.WOOD,
                    2.0f,
                    new Item.Settings().registryKey(WOODEN_SCYTHE_KEY)
            )
    );

    public static final Item STONE_SCYTHE = Registry.register(
            Registries.ITEM,
            STONE_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.STONE,
                    3.0f,
                    new Item.Settings().registryKey(STONE_SCYTHE_KEY)
            )
    );

    public static final Item GOLDEN_SCYTHE = Registry.register(
            Registries.ITEM,
            GOLDEN_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.GOLD,
                    4.0f,
                    new Item.Settings().registryKey(GOLDEN_SCYTHE_KEY)
            )
    );

    public static final Item DIAMOND_SCYTHE = Registry.register(
            Registries.ITEM,
            DIAMOND_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.DIAMOND,
                    7.0f,
                    new Item.Settings().registryKey(DIAMOND_SCYTHE_KEY)
            )
    );


    public static void initialize() {
    }
}
