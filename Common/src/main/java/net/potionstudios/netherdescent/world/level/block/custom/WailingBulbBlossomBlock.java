package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import org.jetbrains.annotations.NotNull;

public class WailingBulbBlossomBlock extends Block {
	public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
	public WailingBulbBlossomBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false));
	}

	@Override
	public void stepOn(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
		super.stepOn(level, pos, state, entity);
		if (!level.isClientSide()) {
            if (!state.getValue(ACTIVE) && entity instanceof LivingEntity livingEntity && !livingEntity.isSpectator()) {
                Holder<Enchantment> soulSpeed = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SOUL_SPEED);
                for (EquipmentSlot equipmentSlot : EquipmentSlotGroup.ARMOR)
                    if (EnchantmentHelper.getItemEnchantmentLevel(soulSpeed, livingEntity.getItemBySlot(equipmentSlot)) == 0) {
                        level.setBlock(pos, state.setValue(ACTIVE, true), 3);
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 0, false, false));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 160, 0, false, false));
						if (livingEntity instanceof ServerPlayer player)
							NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().trigger(player, pos, livingEntity);
                        level.scheduleTick(pos, this, 100);
                    }
            }
		}
	}

    @Override
    protected void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        super.tick(state, level, pos, random);
        if (state.getValue(ACTIVE))
            level.setBlock(pos, state.setValue(ACTIVE, false), 3);
    }

    @Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(ACTIVE));
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
	}
}
