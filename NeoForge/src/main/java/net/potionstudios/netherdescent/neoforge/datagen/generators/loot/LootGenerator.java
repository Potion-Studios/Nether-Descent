package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Set;

public class LootGenerator extends LootTableProvider {
    public LootGenerator() {
        super(Set.of(), ImmutableList.of(
                new SubProviderEntry(BlockLootGenerator::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(EntityLootGenerator::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(ChestLootGenerator::new, LootContextParamSets.CHEST)
        ));
    }
}
