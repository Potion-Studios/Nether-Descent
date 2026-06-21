package net.potionstudios.netherdescent.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class FungalBulbsBlockTrigger extends SimpleCriterionTrigger<FungalBulbsBlockTrigger.TriggerInstance> {
    static final ResourceLocation ID = NetherDescent.id("fungal_bulbs_hit");

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(@NotNull JsonObject json, @NotNull ContextAwarePredicate predicate, @NotNull DeserializationContext deserializationContext) {
        ContextAwarePredicate contextAwarePredicate = EntityPredicate.fromJson(json, "projectile", deserializationContext);
        return new TriggerInstance(predicate, contextAwarePredicate);
    }

    public void trigger(ServerPlayer player, Entity projectile) {
        LootContext lootContext = EntityPredicate.createContext(player, projectile);
        this.trigger(player, arg3 -> arg3.matches(lootContext));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ContextAwarePredicate projectile;
        public TriggerInstance(ContextAwarePredicate player, ContextAwarePredicate projectile) {
            super(FungalBulbsBlockTrigger.ID, player);
            this.projectile = projectile;
        }

        public static TriggerInstance fungalBulbsHit(ContextAwarePredicate projectile) {
            return new TriggerInstance(ContextAwarePredicate.ANY, projectile);
        }

        @Override
        public @NotNull JsonObject serializeToJson(@NotNull SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            jsonObject.add("projectile", this.projectile.toJson(context));
            return jsonObject;
        }

        public boolean matches(LootContext lootContext) {
            return this.projectile.matches(lootContext);
        }
    }
}
