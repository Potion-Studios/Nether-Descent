package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class FungalBulbsBlockTrigger extends SimpleCriterionTrigger<FungalBulbsBlockTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<FungalBulbsBlockTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity projectile) {
        LootContext lootContext = EntityPredicate.createContext(player, projectile);
        this.trigger(player, arg3 -> arg3.matches(lootContext));
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> projectile) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<FungalBulbsBlockTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(FungalBulbsBlockTrigger.TriggerInstance::player),
                        EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("projectile").forGetter(FungalBulbsBlockTrigger.TriggerInstance::projectile)
                )
                .apply(instance, FungalBulbsBlockTrigger.TriggerInstance::new)
        );

        public static Criterion<FungalBulbsBlockTrigger.TriggerInstance> fungalBulbsHit(Optional<ContextAwarePredicate> projectile) {
            return NetherDescentCriteriaTriggers.FUNGAL_BULBS_BLOCK_HIT.get().createCriterion(new FungalBulbsBlockTrigger.TriggerInstance(Optional.empty(), projectile));
        }

        public boolean matches(LootContext lootContext) {
            return this.projectile.isEmpty() || this.projectile.get().matches(lootContext);
        }

        @Override
        public void validate(@NotNull CriterionValidator validator) {
            SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
            validator.validateEntity(this.projectile, ".projectile");
        }
    }
}
