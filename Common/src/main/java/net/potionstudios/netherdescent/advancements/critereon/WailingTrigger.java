package net.potionstudios.netherdescent.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.level.block.custom.WailingBulbBlossomBlock;
import net.potionstudios.netherdescent.world.level.block.custom.WailingGillsBlock;
import org.jetbrains.annotations.NotNull;

public class WailingTrigger extends SimpleCriterionTrigger<WailingTrigger.TriggerInstance> {
	static final ResourceLocation ID = NetherDescent.id("wailing_interaction");

	public void trigger(@NotNull ServerPlayer player, BlockPos pos, Entity entity) {
		LootContext lootcontext = EntityPredicate.createContext(player, entity);
		super.trigger(player, instance -> instance.matches(player.serverLevel(), pos, lootcontext));
	}

	@Override
	protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate playerPredicate, @NotNull DeserializationContext context) {
		LocationPredicate location = LocationPredicate.fromJson(json.get("location"));
		MinMaxBounds.Ints powered = MinMaxBounds.Ints.fromJson(json.get("powered"));
		ContextAwarePredicate entity = EntityPredicate.fromJson(json, "entity", context);

		return new TriggerInstance(playerPredicate, location, powered, entity);
	}

	@Override
	public @NotNull ResourceLocation getId() {
		return ID;
	}

	public static class TriggerInstance extends AbstractCriterionTriggerInstance {
		private final LocationPredicate location;
		private final MinMaxBounds.Ints powered;
		private final ContextAwarePredicate entity;

		public TriggerInstance(ContextAwarePredicate player, LocationPredicate location, MinMaxBounds.Ints powered, ContextAwarePredicate entity) {
			super(WailingTrigger.ID, player);
			this.location = location;
			this.powered = powered;
			this.entity = entity;
		}

		public boolean matches(ServerLevel level, BlockPos pos, LootContext entityContext) {
			if (this.location != LocationPredicate.ANY && !this.location.matches(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5)) {
				return false;
			}
			if (this.entity != ContextAwarePredicate.ANY && !this.entity.matches(entityContext)) {
				return false;
			}
			if (!this.powered.isAny()) {
				BlockState state = level.getBlockState(pos);
				if (state.hasProperty(WailingGillsBlock.POWER)) {
					return this.powered.matches(state.getValue(WailingGillsBlock.POWER));
				}
				if (state.hasProperty(WailingBulbBlossomBlock.ACTIVE)) {
					return this.powered.matches(state.getValue(WailingBulbBlossomBlock.ACTIVE) ? 15 : 0);
				}
				return false;
			}
			return true;
		}

		@Override
		public @NotNull JsonObject serializeToJson(@NotNull SerializationContext context) {
			JsonObject jsonObject = super.serializeToJson(context);
			jsonObject.add("location", this.location.serializeToJson());
			jsonObject.add("powered", this.powered.serializeToJson());
			jsonObject.add("entity", this.entity.toJson(context));
			return jsonObject;
		}

		public static TriggerInstance interactedWithBlock(Block block) {
			return new TriggerInstance(ContextAwarePredicate.ANY, LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).build()).build(), MinMaxBounds.Ints.ANY, ContextAwarePredicate.ANY);
		}

		public static TriggerInstance interactedWithPoweredBlock(Block block, MinMaxBounds.Ints powered) {
			return new TriggerInstance(ContextAwarePredicate.ANY, LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).build()).build(), powered, ContextAwarePredicate.ANY);
		}

		public static TriggerInstance interactedWithPoweredBlockAndEntity(Block block, MinMaxBounds.Ints powered, EntityPredicate.Builder entity) {
			return new TriggerInstance(ContextAwarePredicate.ANY, LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(block).build()).build(), powered, EntityPredicate.wrap(entity.build()));
		}
	}
}
