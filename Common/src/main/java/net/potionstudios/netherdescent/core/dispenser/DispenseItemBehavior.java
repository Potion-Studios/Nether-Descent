package net.potionstudios.netherdescent.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.NDRodBlock;
import org.jetbrains.annotations.NotNull;

public interface DispenseItemBehavior {

    static void registerDispenseItemBehavior() {
        DispenserBlock.registerProjectileBehavior(NetherDescentItems.SOUL_FIRE_CHARGE.get());
        DispenserBlock.registerBehavior(NetherDescentBlocks.SOUL_FIRE_ROD.get(), new OptionalDispenseItemBehavior() {
            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack item) {
                Level level = blockSource.level();
                BlockPos blockPos = blockSource.pos().relative(blockSource.state().getValue(DispenserBlock.FACING));
                NDRodBlock rodBlock = NetherDescentBlocks.SOUL_FIRE_ROD.get();
                if (level.isEmptyBlock(blockPos) && rodBlock.canSpawnBlaze(level, blockPos)) {
                    if (!level.isClientSide()) {
                        level.setBlock(blockPos, rodBlock.defaultBlockState(), 3);
                        level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
                    }
                    item.shrink(1);
                    setSuccess(true);
                } else setSuccess(ArmorItem.dispenseArmor(blockSource, item));
                return item;
            }
        });
    }
}
