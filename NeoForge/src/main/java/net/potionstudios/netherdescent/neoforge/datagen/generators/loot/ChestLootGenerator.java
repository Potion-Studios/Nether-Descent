package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ChestLootGenerator implements LootTableSubProvider {
    public ChestLootGenerator() {
    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        output.accept(NetherDescent.key(Registries.LOOT_TABLE, "chests/nether_bridge"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0f, 4.0f))
                                .add(LootItem.lootTableItem(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()).setWeight(3))
                                .add(EmptyLootItem.emptyItem().setWeight(70))));

    }
}
