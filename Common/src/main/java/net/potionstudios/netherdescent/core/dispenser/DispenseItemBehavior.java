package net.potionstudios.netherdescent.core.dispenser;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.NDRodBlock;
import org.jetbrains.annotations.NotNull;

public interface DispenseItemBehavior {

    static void registerDispenseItemBehavior() {
        DispenserBlock.registerBehavior(NetherDescentItems.SOUL_FIRE_CHARGE.get(), new DefaultDispenseItemBehavior() {
            @Override
            public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                Position position = DispenserBlock.getDispensePosition(source);
                double d0 = position.x() + direction.getStepX() * 0.3F;
                double d1 = position.y() + direction.getStepY() * 0.3F;
                double d2 = position.z() + direction.getStepZ() * 0.3F;
                Level level = source.getLevel();
                RandomSource randomsource = level.random;
                double d3 = randomsource.triangle(direction.getStepX(), 0.11485000000000001);
                double d4 = randomsource.triangle(direction.getStepY(), 0.11485000000000001);
                double d5 = randomsource.triangle(direction.getStepZ(), 0.11485000000000001);
                SmallSoulFireball smallfireball = new SmallSoulFireball(level, d0, d1, d2, d3, d4, d5);
                level.addFreshEntity(Util.make(smallfireball, arg2 -> arg2.setItem(stack)));
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(@NotNull BlockSource source) {
                source.getLevel().levelEvent(1018, source.getPos(), 0);
            }
        });
        DispenserBlock.registerBehavior(NetherDescentItems.PENDORITE_FIRE_CHARGE.get(), new DefaultDispenseItemBehavior() {
            @Override
            public @NotNull ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                Position position = DispenserBlock.getDispensePosition(source);
                double d0 = position.x() + direction.getStepX() * 0.3F;
                double d1 = position.y() + direction.getStepY() * 0.3F;
                double d2 = position.z() + direction.getStepZ() * 0.3F;
                Level level = source.getLevel();
                RandomSource randomsource = level.random;
                double d3 = randomsource.triangle(direction.getStepX(), 0.11485000000000001);
                double d4 = randomsource.triangle(direction.getStepY(), 0.11485000000000001);
                double d5 = randomsource.triangle(direction.getStepZ(), 0.11485000000000001);
                SmallFireball smallfireball = new SmallFireball(level, d0, d1, d2, d3, d4, d5);
                level.addFreshEntity(Util.make(smallfireball, arg2 -> arg2.setItem(stack)));
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(@NotNull BlockSource source) {
                source.getLevel().levelEvent(1018, source.getPos(), 0);
            }
        });
        DispenserBlock.registerBehavior(NetherDescentBlocks.SOUL_FIRE_ROD.get(), new OptionalDispenseItemBehavior() {
            @Override
            @NotNull
            protected ItemStack execute(@NotNull BlockSource blockSource, @NotNull ItemStack item) {
                Level level = blockSource.getLevel();
                BlockPos blockPos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
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
