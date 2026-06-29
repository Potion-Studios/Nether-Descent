package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class WailingGillsBlock extends Block {
    private static final MapCodec<WailingGillsBlock> CODEC = simpleCodec(WailingGillsBlock::new);
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public static final int TICK_DELAY = 5;

    public WailingGillsBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    protected @NotNull MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected int getSignal(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction direction) {
        return state.getValue(POWER);
    }

    @Override
    protected void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.onPlace(state, level, pos, oldState, movedByPiston);
    }

    @Override
    protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean movedByPiston) {
        if (!level.isClientSide()) refreshPower(level, pos, state);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    private void refreshPower(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
        int power = level.getBestNeighborSignal(pos);
        if (state.getValue(POWER) != power)
            level.setBlock(pos, state.setValue(POWER, power), 3);
    }


    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(POWER));
    }

    public static void tryApplyWailingGillEffects(BlockState state, ServerLevel level, BlockPos pos) {
        int powered = state.getValue(WailingGillsBlock.POWER);
        double distance = -15.0 - powered;

        AABB box = state.getShape(level, pos).bounds().expandTowards(0, distance, 0).move(pos);
        Predicate<Entity> entityPredicate = entity -> entity instanceof LivingEntity livingEntity && entityChecks(level, pos, livingEntity, distance);
        List<Entity> entities = level.getEntities((Entity) null, box, entityPredicate);
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                applyWailingGillEffects(level, pos, livingEntity, powered);            }
        }
    }

    private static boolean entityChecks(ServerLevel serverLevel, BlockPos pos, LivingEntity entity, double distance) {
        Holder<Enchantment> soulSpeed = serverLevel.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SOUL_SPEED);
        for (ItemStack itemStack : entity.getArmorSlots())
            if (EnchantmentHelper.getItemEnchantmentLevel(soulSpeed, itemStack) > 0)
                return false;


        if (entity.isSpectator())
            return false;
        int solidFloor = serverLevel.getMinBuildHeight();
        BlockPos.MutableBlockPos checkPos = pos.mutable();
        ChunkAccess chunk = serverLevel.getChunk(checkPos);
        for (int i = 1; i <= Math.abs(distance); i++) {
            checkPos.setY(pos.getY() - i);
            BlockState checkState = chunk.getBlockState(checkPos);
            if (checkState.isFaceSturdy(serverLevel, checkPos, Direction.UP) || checkState.getBlock() instanceof WailingGillsBlock) {
                solidFloor = checkPos.getY();
                break;
            }
        }

        return entity.getY() > solidFloor;

    }

    private static void applyWailingGillEffects(ServerLevel serverLevel, BlockPos pos, LivingEntity entity, int powered) {
        entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 6, powered, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, powered, false, false));
        if (entity instanceof ServerPlayer player)
            NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().trigger(player, pos, entity);
        else if (entity instanceof Animal animal && animal.getLeashHolder() instanceof ServerPlayer player)
            NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().trigger(player, pos, entity);
        ParticleOptions particleData = powered > 0 ? NetherDescentParticles.GILL_LEVITATE_POWERED.get() : NetherDescentParticles.GILL_LEVITATE.get();
        for (int i = 0; i < pos.getY() - entity.getY() - 1; i++)
            serverLevel.sendParticles(particleData, entity.getX(), entity.getY() + i, entity.getZ(), 2, 0.5, 0, 0.5, 1.2 + (powered * 0.9));
    }
}
