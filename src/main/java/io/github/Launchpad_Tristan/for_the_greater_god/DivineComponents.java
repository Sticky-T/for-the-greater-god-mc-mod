package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.item.ItemStack;

public class DivineComponents {


    public static void setAbility(
            ItemStack stack,
            String god,
            String ability,
            int level
    ) {

        stack.set(
                ModComponents.DIVINE_RELIC,
                new DivineRelicData(
                        god,
                        ability,
                        level
                )
        );

    }



    public static boolean isRelic(
            ItemStack stack
    ) {

        return stack.contains(
                ModComponents.DIVINE_RELIC
        );

    }



    public static DivineRelicData getData(
            ItemStack stack
    ) {

        return stack.get(
                ModComponents.DIVINE_RELIC
        );

    }

}