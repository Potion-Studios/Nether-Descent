package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;

class ChestLootGenerator implements LootTableSubProvider {
    protected final LootTableSubProvider.Context output;
    protected ChestLootGenerator(LootTableSubProvider.Context context) {
        this.output = context;
    }

    @Override
    public void run() {
        output.accept(NetherDescent.key(Registries.LOOT_TABLE, "chests/nether_bridge"),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ContextIntProviders.between(2, 4))
                                .add(LootItem.lootTableItem(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()).setWeight(3))
                                .add(EmptyLootItem.emptyItem().setWeight(70))));
    }
}
