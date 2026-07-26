package net.potionstudios.netherdescent.forge.datagen.generators.loot;

import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
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
    ChestLootGenerator() {

    }

    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
        output.accept(NetherDescent.id("chests/nether_bridge"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0f, 4.0f))
                                .add(LootItem.lootTableItem(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()).setWeight(3))
                                .add(EmptyLootItem.emptyItem().setWeight(70))));
    }
}
