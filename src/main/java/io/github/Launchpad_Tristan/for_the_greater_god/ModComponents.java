package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class ModComponents {


    public static DataComponentType<DivineRelicData> DIVINE_RELIC;


    public static void initialize() {


        DIVINE_RELIC =
                Registry.register(
                        BuiltInRegistries.DATA_COMPONENT_TYPE,
                        Identifier.fromNamespaceAndPath(
                                "for_the_greater_god",
                                "divine_relic"
                        ),
                        DataComponentType.<DivineRelicData>builder()
                                .persistent(
                                        Codec.STRING
                                                .xmap(
                                                        str -> {
                                                            String[] split =
                                                                    str.split(":");

                                                            return new DivineRelicData(
                                                                    split[0],
                                                                    split[1],
                                                                    Integer.parseInt(split[2])
                                                            );
                                                        },

                                                        data ->
                                                                data.god()
                                                                        + ":"
                                                                        + data.ability()
                                                                        + ":"
                                                                        + data.level()
                                                )
                                )
                                .build()
                );
    }
}