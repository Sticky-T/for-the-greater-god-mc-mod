package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class DivineGear {

    private static final String KEY = "DivineGear";


    public static void markDivine(ItemStack stack) {

        CompoundTag nbt = new CompoundTag();

        nbt.putBoolean(KEY, true);

        stack.set(
                DataComponents.CUSTOM_DATA,
                CustomData.of(nbt)
        );
    }


    public static boolean isDivineGear(ItemStack stack) {

        CustomData component =
                stack.get(DataComponents.CUSTOM_DATA);


        if (component == null) {
            return false;
        }


        CompoundTag nbt =
                component.copyTag();


        return nbt.getBoolean(KEY).orElse(false);
    }
}
