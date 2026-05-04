package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class WailingTrigger extends SimpleCriterionTrigger<WailingTrigger.TriggerInstance> {
	@Override
	public @NotNull Codec<TriggerInstance> codec() {
		return TriggerInstance.CODEC;
	}

	public void trigger(@NotNull ServerPlayer player, BlockPos pos) {
		super.trigger(player, instance -> instance.matches(player.serverLevel(), pos));
	}

	public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<LocationPredicate> location) implements SimpleCriterionTrigger.SimpleInstance {
		public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
				instance.group(
						EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
						LocationPredicate.CODEC.optionalFieldOf("location").forGetter(TriggerInstance::location)
				).apply(instance, TriggerInstance::new)
		);

		public boolean matches(ServerLevel level, BlockPos pos) {
			return location.isEmpty() || location.get().matches(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
		}

		public static Criterion<TriggerInstance> interactedWithBlock(Block block) {
			return NetherDescentCriterionTriggers.WAILING_INTERACTION.get().createCriterion(
					new TriggerInstance(Optional.empty(), Optional.of(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block)).build()))
			);
		}
	}
}
