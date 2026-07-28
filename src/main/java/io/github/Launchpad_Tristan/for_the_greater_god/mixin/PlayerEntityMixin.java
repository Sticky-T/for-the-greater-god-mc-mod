package io.github.Launchpad_Tristan.for_the_greater_god.mixin;

import io.github.Launchpad_Tristan.for_the_greater_god.DivineGear;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(
            method = "dropInventory",
            at = @At("HEAD")
    )
    private void protectDivineGearFromDeath(CallbackInfo ci) {

        PlayerEntity player = (PlayerEntity) (Object) this;

        for (int i = 0; i < player.getInventory().size(); i++) {

            ItemStack stack = player.getInventory().getStack(i);

            if (DivineGear.isDivineGear(stack)) {

                // Make it not count as a droppable item
                player.getInventory().setStack(i, ItemStack.EMPTY);

                // Put it back after drops happen
                player.getInventory().insertStack(stack);
            }
        }
    }
}