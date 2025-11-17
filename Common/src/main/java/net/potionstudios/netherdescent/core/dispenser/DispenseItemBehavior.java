package net.potionstudios.netherdescent.core.dispenser;

import net.minecraft.world.level.block.DispenserBlock;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;

public interface DispenseItemBehavior {

    static void registerDispenseItemBehavior() {
        DispenserBlock.registerProjectileBehavior(NetherDescentItems.SOUL_FIRE_CHARGE.get());
    }
}
