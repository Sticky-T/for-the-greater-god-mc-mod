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
            float bonusDamage,
            Item.Settings settings
    ) {
        super(settings);
        this.material = material;
        this.bonusDamage = bonusDamage;
    }


    public ToolMaterial getMaterial() {
        return material;
    }

    private final float bonusDamage;

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient()) {

            target.damage(
                    (ServerWorld) attacker.getWorld(),
                    attacker.getDamageSources().magic(),
                    bonusDamage
            );

        }
    }
}