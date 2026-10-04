package net.potionstudios.netherdescent.advancements.critereon;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Validatable;
import net.minecraft.world.level.storage.loot.ValidationContextSource;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.potionstudios.netherdescent.advancements.NetherDescentCriteriaTriggers;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class FungalBulbsBlockTrigger extends SimpleCriterionTrigger<FungalBulbsBlockTrigger.TriggerInstance> {
    @Override
    public @NonNull Codec<FungalBulbsBlockTrigger.TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public void trigger(ServerPlayer player, Entity projectile) {
        LootContext lootContext = EntityPredicate.createContext(player, projectile);
        this.trigger(player, arg3 -> arg3.matches(lootContext));
    }

    public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Optional<Holder<LootItemCondition>> projectile) implements SimpleCriterionTrigger.SimpleInstance {
        public static final Codec<FungalBulbsBlockTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        LootItemCondition.CODEC.optionalFieldOf("player").forGetter(FungalBulbsBlockTrigger.TriggerInstance::player),
                        LootItemCondition.CODEC.optionalFieldOf("projectile").forGetter(FungalBulbsBlockTrigger.TriggerInstance::projectile)
                )
                .apply(instance, FungalBulbsBlockTrigger.TriggerInstance::new)
        );

        public static Criterion<FungalBulbsBlockTrigger.TriggerInstance> fungalBulbsHit(final Optional<Holder<LootItemCondition>> projectile) {
            return NetherDescentCriteriaTriggers.FUNGAL_BULBS_BLOCK_HIT.get().createCriterion(new FungalBulbsBlockTrigger.TriggerInstance(Optional.empty(), projectile));
        }

        public boolean matches(LootContext lootContext) {
            return this.projectile.isEmpty() || ((LootItemCondition)((Holder<?>)this.projectile.get()).value()).test(lootContext);
        }

        @Override
        public void validate(@NonNull ValidationContextSource validator) {
            SimpleInstance.super.validate(validator);
            Validatable.validateHolder(validator.entityContext(), "projectile", this.projectile);
        }
    }
}
