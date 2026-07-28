package io.github.Launchpad_Tristan.for_the_greater_god.mixin;

import io.github.Launchpad_Tristan.for_the_greater_god.DivineGear;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Redirect(
            method = "dropInventory",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;"
            )
    )
    private net.minecraft.entity.ItemEntity preventDivineDeathDrop(
            PlayerEntity player,
            ItemStack stack,
            boolean throwRandomly
    ) {

        if (DivineGear.isDivineGear(stack)) {
            return null;
        }

        return player.dropItem(stack, throwRandomly);
    }
}