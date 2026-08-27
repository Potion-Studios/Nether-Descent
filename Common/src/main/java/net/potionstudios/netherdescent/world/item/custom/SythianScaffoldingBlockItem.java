package net.potionstudios.netherdescent.world.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ScaffoldingBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.potionstudios.netherdescent.world.level.block.custom.SythianScaffoldingBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SythianScaffoldingBlockItem extends ScaffoldingBlockItem {
    public SythianScaffoldingBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public @Nullable BlockPlaceContext updatePlacementContext(@NotNull BlockPlaceContext context) {
        BlockPos blockPos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = this.getBlock();
        if (!blockState.is(block)) {
            return SythianScaffoldingBlock.getDistance(level, blockPos) == 7 ? null : context;
        } else {
            Direction direction;
            if (context.isSecondaryUseActive()) {
                direction = context.isInside() ? context.getClickedFace().getOpposite() : context.getClickedFace();
            } else {
                direction = context.getClickedFace() == Direction.UP ? context.getHorizontalDirection() : Direction.UP;
            }

            int i = 0;
            BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable().move(direction);

            while (i < 7) {
                if (!level.isClientSide() && !level.isInWorldBounds(mutableBlockPos)) {
                    Player player = context.getPlayer();
                    int j = level.getMaxY();
                    if (player instanceof ServerPlayer && mutableBlockPos.getY() >= j) {
                        ((ServerPlayer)player).sendSystemMessage(Component.translatable("build.tooHigh", j - 1).withStyle(ChatFormatting.RED), true);
                    }
                    break;
                }

                blockState = level.getBlockState(mutableBlockPos);
                if (!blockState.is(this.getBlock())) {
                    if (blockState.canBeReplaced(context)) {
                        return BlockPlaceContext.at(context, mutableBlockPos, direction);
                    }
                    break;
                }

                mutableBlockPos.move(direction);
                if (direction.getAxis().isHorizontal()) {
                    i++;
                }
            }

            return null;
        }
    }
}
