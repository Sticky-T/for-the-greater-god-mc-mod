package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class DivineGear {

    private static final String KEY = "DivineGear";


    public static void markDivine(ItemStack stack) {

        NbtCompound nbt = new NbtCompound();

        nbt.putBoolean(KEY, true);

        stack.set(
                DataComponentTypes.CUSTOM_DATA,
                NbtComponent.of(nbt)
        );
    }


    public static boolean isDivineGear(ItemStack stack) {

        NbtComponent component =
                stack.get(DataComponentTypes.CUSTOM_DATA);


        if (component == null) {
            return false;
        }


        NbtCompound nbt =
                component.copyNbt();


        return nbt.getBoolean(KEY).orElse(false);
    }
}
