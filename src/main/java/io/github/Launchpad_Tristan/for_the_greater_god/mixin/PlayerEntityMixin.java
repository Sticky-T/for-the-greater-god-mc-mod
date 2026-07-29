package io.github.Launchpad_Tristan.for_the_greater_god.mixin;

import io.github.Launchpad_Tristan.for_the_greater_god.DivineComponents;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {


    @Inject(
            method = "dropItem(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/entity/ItemEntity;",
            at = @At("HEAD"),
            cancellable = true
    )
    private void preventDivineDrop(
            ItemStack stack,
            boolean throwRandomly,
            CallbackInfoReturnable<ItemEntity> cir
    ) {

        if (DivineComponents.isRelic(stack)) {

            cir.setReturnValue(null);

        }

    }

}