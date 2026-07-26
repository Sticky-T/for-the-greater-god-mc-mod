package io.github.Launchpad_Tristan.for_the_greater_god;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {


    public static ComponentType<DivineRelicData> DIVINE_RELIC;


    public static void initialize() {


        DIVINE_RELIC =
                Registry.register(
                        Registries.DATA_COMPONENT_TYPE,
                        Identifier.of(
                                "for_the_greater_god",
                                "divine_relic"
                        ),
                        ComponentType.<DivineRelicData>builder()
                                .codec(
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