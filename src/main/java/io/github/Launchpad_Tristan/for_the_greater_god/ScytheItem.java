package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;

public class ScytheItem extends Item {

    private final ToolMaterial material;

    public ScytheItem(
            ToolMaterial material,
            Item.Settings settings
    ) {
        super(settings);
        this.material = material;
    }


    public ToolMaterial getMaterial() {
        return material;
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {

            target.damage(
                    (ServerWorld) attacker.getWorld(),
                    attacker.getDamageSources().magic(),
                    10.0f
            );

        }
    }
}