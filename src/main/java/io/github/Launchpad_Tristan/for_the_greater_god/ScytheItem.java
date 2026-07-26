package io.github.Launchpad_Tristan.for_the_greater_god;

import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;

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
}