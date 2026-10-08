package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityTypes;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.stream.Stream;

class EntityLootGenerator extends EntityLootSubProvider {
    private static final ArrayList<EntityType<?>> knownEntities = new ArrayList<>();
    protected EntityLootGenerator(LootTableSubProvider.Context context) {
        super(FeatureFlags.REGISTRY.allFlags(), context);
    }

    @Override
    public void generate() {
        add(NetherDescentEntityTypes.SOUL_BLAZE.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(NetherDescentItems.SOUL_BLAZE_ROD.get()).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F)))).when(LootItemKilledByPlayerCondition.killedByPlayer())));
	    add(NetherDescentEntityTypes.SOUL_GHAST.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(Items.GHAST_TEAR).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 1))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F))))).withPool(LootPool.lootPool().setRolls(ContextIntProviders.exactly(1)).add(LootItem.lootTableItem(Items.GUNPOWDER).apply(SetItemCountFunction.setCount(ContextIntProviders.between(0, 2))).apply(EnchantedCountIncreaseFunction.lootingMultiplier(enchantments, ContextFloatProviders.between(0.0F, 1.0F))))));
    }

    @Override
    protected void add(@NonNull EntityType<?> entityType, LootTable.@NonNull Builder builder) {
        super.add(entityType, builder);
        knownEntities.add(entityType);
    }

    @Override
    protected @NonNull Stream<EntityType<?>> getKnownEntityTypes() {
        return knownEntities.stream();
    }
}
