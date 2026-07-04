package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.NonNull;

import java.util.Arrays;
import java.util.function.ToIntFunction;

public class EmburCaveMossBlock extends GlowLichenBlock {
    public static final BooleanProperty GLOWING = BooleanProperty.create("glowing");

    public EmburCaveMossBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(GLOWING, false));
    }

    public static ToIntFunction<BlockState> emission(int light) {
        return blockState -> blockState.getValue(GLOWING) ? light : 0;
    }

    @Override
    protected @NonNull InteractionResult useItemOn(ItemStack stack, @NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Player player, @NonNull InteractionHand hand, @NonNull BlockHitResult hitResult) {
        if (stack.is(Items.GLOW_INK_SAC))
            level.setBlockAndUpdate(pos, state.setValue(GLOWING, true));
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected boolean canBeReplaced(@NonNull BlockState state, BlockPlaceContext useContext) {
        return !useContext.getItemInHand().is(this.asItem()) || hasAnyVacantFace(state);
    }

    private static boolean hasAnyVacantFace(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch((direction) -> !hasFace(state, direction));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(GLOWING));
    }
}
