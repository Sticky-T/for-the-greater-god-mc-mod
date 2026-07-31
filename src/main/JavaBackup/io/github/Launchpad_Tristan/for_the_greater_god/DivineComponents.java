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


    public static boolean isRelic(ItemStack stack) {

        return stack.contains(
                ModComponents.DIVINE_RELIC
        );

    }


    public static DivineRelicData getData(ItemStack stack) {

        return stack.get(
                ModComponents.DIVINE_RELIC
        );

    }


    public static boolean hasGod(
            ItemStack stack,
            String god
    ) {

        if (!isRelic(stack)) {
            return false;
        }

        DivineRelicData data = getData(stack);

        return data != null
                && data.god().equalsIgnoreCase(god);

    }


    public static boolean hasAbility(
            ItemStack stack,
            String ability
    ) {

        if (!isRelic(stack)) {
            return false;
        }

        DivineRelicData data = getData(stack);

        return data != null
                && data.ability().equalsIgnoreCase(ability);

    }


    public static int getLevel(ItemStack stack) {

        if (!isRelic(stack)) {
            return 0;
        }

        DivineRelicData data = getData(stack);

        return data == null
                ? 0
                : data.level();

    }

}