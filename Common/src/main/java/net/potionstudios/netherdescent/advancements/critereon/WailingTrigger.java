package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import net.potionstudios.netherdescent.world.level.block.custom.WailingBulbBlossomBlock;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WailingTrigger extends SimpleCriterionTrigger<WailingTrigger.TriggerInstance> {
	@Override
	public @NotNull Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(@NotNull ServerPlayer player, BlockPos pos, Entity entity) {
		LootContext lootcontext = EntityPredicate.createContext(player, entity);
		super.trigger(player, instance -> instance.matches(player.serverLevel(), pos, lootcontext));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<LocationPredicate> location, Optional<MinMaxBounds.Ints> powered, Optional<ContextAwarePredicate> entity) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
				instance.group(
						EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
						LocationPredicate.CODEC.optionalFieldOf("location").forGetter(TriggerInstance::location),
						MinMaxBounds.Ints.CODEC.optionalFieldOf("powered").forGetter(TriggerInstance::powered),
						EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("entity").forGetter(TriggerInstance::entity)
				).apply(instance, TriggerInstance::new)
		);

		public boolean matches(ServerLevel level, BlockPos pos, LootContext entityContext) {
			if (location.isPresent() && !location.get().matches(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5))
				return false;
			if (entity.isPresent() && !entity.get().matches(entityContext))
				return false;
			if (powered.isPresent()) {
				BlockState state = level.getBlockState(pos);
				if (state.hasProperty(WailingGillsBlock.POWER))
					return powered.get().matches(state.getValue(WailingGillsBlock.POWER));
				if (state.hasProperty(WailingBulbBlossomBlock.ACTIVE))
					return powered.get().matches(state.getValue(WailingBulbBlossomBlock.ACTIVE) ? 15 : 0);
				return false;
			}
			return true;
		}

		public static Criterion<TriggerInstance> interactedWithBlock(HolderGetter<Block> blockHolderGetter, Block block) {
			return NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().createCriterion(
					new TriggerInstance(Optional.empty(), Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, block)).build()), Optional.empty(), Optional.empty())
			);
		}

		public static Criterion<TriggerInstance> interactedWithPoweredBlock(HolderGetter<Block> blockHolderGetter, Block block, MinMaxBounds.Ints powered) {
			return NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().createCriterion(
					new TriggerInstance(Optional.empty(), Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, block)).build()), Optional.of(powered), Optional.empty())
			);
		}

		public static Criterion<TriggerInstance> interactedWithPoweredBlockAndEntity(HolderGetter<Block> blockHolderGetter, Block block, MinMaxBounds.Ints powered, EntityPredicate.Builder entity) {
			return NetherDescentCriteriaTriggers.WAILING_INTERACTION.get().createCriterion(
					new TriggerInstance(Optional.empty(), Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, block)).build()), Optional.of(powered), Optional.of(EntityPredicate.wrap(entity.build())))
			);
		}
	}
}
