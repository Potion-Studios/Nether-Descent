package net.potionstudios.netherdescent.world.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class SoulFireChargeItem extends FireChargeItem {
    public SoulFireChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        boolean bl = false;
        if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
            blockPos = blockPos.relative(context.getClickedFace());
            if (BaseFireBlock.canBePlacedAt(level, blockPos, context.getHorizontalDirection())) {
                this.playSound(level, blockPos);
                level.setBlockAndUpdate(blockPos, BaseFireBlock.getState(level, blockPos));
                level.gameEvent(context.getPlayer(), GameEvent.BLOCK_PLACE, blockPos);
                bl = true;
            }
        } else {
            this.playSound(level, blockPos);
            level.setBlockAndUpdate(blockPos, blockState.setValue(BlockStateProperties.LIT, true));
            level.gameEvent(context.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
            bl = true;
        }

        if (bl) {
            context.getItemInHand().shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return InteractionResult.FAIL;
        }
    }

    private void playSound(Level level, BlockPos pos) {
        RandomSource randomSource = level.getRandom();
        level.playSound(null, pos, SoundEvents.FIRECHARGE_USE, SoundSource.BLOCKS, 1.0F, (randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F + 1.0F);
    }
}
