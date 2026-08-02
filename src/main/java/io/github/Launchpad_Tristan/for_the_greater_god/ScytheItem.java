package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;

public class ScytheItem extends Item {

    private final ToolMaterial material;

    public ScytheItem(
            ToolMaterial material,
            float bonusDamage,
            Item.Properties settings
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
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {

            target.hurtServer(
                    (ServerLevel) attacker.level(),
                    attacker.damageSources().magic(),
                    bonusDamage
            );

        }
    }
}