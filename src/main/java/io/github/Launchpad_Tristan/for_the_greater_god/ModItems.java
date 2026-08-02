package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

public class ModItems {

    public static final ResourceKey<Item> DIVINE_GEAR_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "divine_gear")
            );

    public static final ResourceKey<Item> NETHERITE_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "netherite_scythe")
            );

    public static final ResourceKey<Item> IRON_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "iron_scythe")
            );

    public static final ResourceKey<Item> WOODEN_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "wooden_scythe")
            );

    public static final ResourceKey<Item> STONE_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "stone_scythe")
            );

    public static final ResourceKey<Item> COPPER_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "copper_scythe")
            );

    public static final ResourceKey<Item> GOLDEN_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "golden_scythe")
            );

    public static final ResourceKey<Item> DIAMOND_SCYTHE_KEY =
            ResourceKey.create(
                    Registries.ITEM,
                    Identifier.fromNamespaceAndPath(For_the_greater_god.MOD_ID, "diamond_scythe")
            );


    public static final Item DIVINE_GEAR = Registry.register(
            BuiltInRegistries.ITEM,
            DIVINE_GEAR_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    20.0f,
                    new Item.Properties()
                            .setId(DIVINE_GEAR_KEY)
            )
    );


    public static final Item NETHERITE_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            NETHERITE_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.NETHERITE,
                    9.0f,
                    new Item.Properties()
                            .setId(NETHERITE_SCYTHE_KEY)
            )
    );


    public static final Item IRON_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            IRON_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.IRON,
                    5.0f,
                    new Item.Properties()
                            .setId(IRON_SCYTHE_KEY)
            )
    );

    public static final Item WOODEN_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            WOODEN_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.WOOD,
                    2.0f,
                    new Item.Properties().setId(WOODEN_SCYTHE_KEY)
            )
    );

    public static final Item STONE_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            STONE_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.STONE,
                    3.0f,
                    new Item.Properties().setId(STONE_SCYTHE_KEY)
            )
    );

    public static final Item COPPER_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            COPPER_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.COPPER,
                    4.5f,
                    new Item.Properties()
                            .setId(COPPER_SCYTHE_KEY)
            )
    );

    public static final Item GOLDEN_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            GOLDEN_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.GOLD,
                    4.0f,
                    new Item.Properties().setId(GOLDEN_SCYTHE_KEY)
            )
    );

    public static final Item DIAMOND_SCYTHE = Registry.register(
            BuiltInRegistries.ITEM,
            DIAMOND_SCYTHE_KEY,
            new ScytheItem(
                    ToolMaterial.DIAMOND,
                    7.0f,
                    new Item.Properties().setId(DIAMOND_SCYTHE_KEY)
            )
    );


    public static void initialize() {
    }
}
