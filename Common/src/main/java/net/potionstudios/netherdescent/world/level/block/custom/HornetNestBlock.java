package net.potionstudios.netherdescent.world.level.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import net.potionstudios.netherdescent.world.level.block.entity.HornetNestBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HornetNestBlock extends BaseEntityBlock {
	public static final MapCodec<HornetNestBlock> CODEC = simpleCodec(HornetNestBlock::new);
	public HornetNestBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (!level.isClientSide() && blockEntity instanceof HornetNestBlockEntity hornetNestBlockEntity) {
            if (!EnchantmentHelper.hasTag(tool, EnchantmentTags.PREVENTS_BEE_SPAWNS_WHEN_MINING)) {
                hornetNestBlockEntity.emptyAllLivingFromHive(player, state, HornetNestBlockEntity.HornetReleaseStatus.EMERGENCY);
                this.angerNearbyHornets(level, pos);
            }
        }
    }

    private void angerNearbyHornets(Level level, BlockPos pos) {
        AABB aABB = new AABB(pos).inflate(8.0, 6.0, 8.0);
        List<Hornet> list = level.getEntitiesOfClass(Hornet.class, aABB);
        if (!list.isEmpty()) {
            List<Player> list2 = level.getEntitiesOfClass(Player.class, aABB);
            if (list2.isEmpty()) {
                return;
            }

            for (Hornet hornet : list) {
                if (hornet.getTarget() == null) {
                    Player player = Util.getRandom(list2, level.random);
                    hornet.setTarget(player);
                }
            }
        }
    }

    private boolean nestContainsHornets(Level level, BlockPos pos) {
		return level.getBlockEntity(pos) instanceof HornetNestBlockEntity hornetNestBlockEntity && hornetNestBlockEntity.isEmpty();
	}

	@Override
	protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HornetNestBlockEntity(pos, state);
	}

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return super.getTicker(level, state, blockEntityType);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
        if (!level.isClientSide() && player.isCreative()
            && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)
            && level.getBlockEntity(pos) instanceof HornetNestBlockEntity hornetNestBlockEntity)
            if (!hornetNestBlockEntity.isEmpty()) {
                ItemStack itemStack = new ItemStack(this);
                itemStack.applyComponents(hornetNestBlockEntity.collectComponents());
                ItemEntity itemEntity = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), itemStack);
                itemEntity.setDefaultPickUpDelay();
                level.addFreshEntity(itemEntity);
            }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
        Entity entity = params.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (entity instanceof PrimedTnt
                || entity instanceof Creeper
                || entity instanceof WitherSkull
                || entity instanceof WitherBoss
                || entity instanceof MinecartTNT) {
            BlockEntity blockEntity = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
            if (blockEntity instanceof HornetNestBlockEntity hornetNestBlockEntity) {
                hornetNestBlockEntity.emptyAllLivingFromHive(null, state, HornetNestBlockEntity.HornetReleaseStatus.EMERGENCY);
            }
        }

        return super.getDrops(state, params);
    }
}
