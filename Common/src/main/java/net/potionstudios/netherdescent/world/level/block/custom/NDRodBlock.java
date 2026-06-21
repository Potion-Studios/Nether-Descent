package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.entity.monster.PendoriteBlaze;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NDRodBlock extends EndRodBlock {
    private @Nullable BlockPattern blazeFull;
    public NDRodBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {}

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (state.is(NetherDescentBlocks.PENDORITE_FIRE_ROD.get()) && !oldState.is(state.getBlock()))
            trySpawnBlaze(level, pos);
    }

    public boolean canSpawnBlaze(LevelReader level, BlockPos pos) {
        return getOrCreatePendoriteBlaze().find(level, pos) != null;
    }

    private void trySpawnBlaze(Level level, BlockPos pos) {
        BlockPattern.BlockPatternMatch blockPatternMatch = getOrCreatePendoriteBlaze().find(level, pos);
        if (blockPatternMatch != null) {
            PendoriteBlaze pendoriteBlaze = NetherDescentEntityType.PENDORITE_BLAZE.get().create(level);
            if (pendoriteBlaze != null) {
                pendoriteBlaze.setPlayerCreated(true);
                spawnBlazeInWorld(level, blockPatternMatch, pendoriteBlaze, pos);
            }
        }
    }

    private static void spawnBlazeInWorld(Level level, BlockPattern.BlockPatternMatch patternMatch, Entity blaze, BlockPos pos) {
        clearPatternBlocks(level, patternMatch);
        blaze.moveTo(pos.getX() + 0.5, pos.getY() + 0.05, pos.getZ() + 0.5, 0.0F, 0.0F);
        level.addFreshEntity(blaze);

        for (ServerPlayer serverPlayer : level.getEntitiesOfClass(ServerPlayer.class, blaze.getBoundingBox().inflate(5.0)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, blaze);

        updatePatternBlocks(level, patternMatch);
    }

    public static void clearPatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for (int i = 0; i < patternMatch.getWidth(); i++)
            for (int j = 0; j < patternMatch.getHeight(); j++) {
                BlockInWorld blockInWorld = patternMatch.getBlock(i, j, 0);
                level.setBlock(blockInWorld.getPos(), Blocks.AIR.defaultBlockState(), 2);
                level.levelEvent(2001, blockInWorld.getPos(), Block.getId(blockInWorld.getState()));
            }
    }

    public static void updatePatternBlocks(Level level, BlockPattern.BlockPatternMatch patternMatch) {
        for (int i = 0; i < patternMatch.getWidth(); i++) {
            for (int j = 0; j < patternMatch.getHeight(); j++) {
                BlockInWorld blockInWorld = patternMatch.getBlock(i, j, 0);
                level.blockUpdated(blockInWorld.getPos(), Blocks.AIR);
            }
        }
    }

    private BlockPattern getOrCreatePendoriteBlaze() {
        if (this.blazeFull == null) {
            this.blazeFull = BlockPatternBuilder.start()
                    .aisle("~^~", "###", "~#~")
                    .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(NetherDescentBlocks.PENDORITE_FIRE_ROD.get())))
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(NetherDescentBlocks.PENDORITE_BLOCK.get())))
                    .where('~', blockInWorld -> blockInWorld.getState().isAir())
                    .build();
        }

        return this.blazeFull;
    }
}
