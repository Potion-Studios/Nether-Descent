package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.concurrent.CompletableFuture;

public class GlobalLootModifiersGenerator extends GlobalLootModifierProvider {
    public GlobalLootModifiersGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, NetherDescent.MOD_ID);
    }

    @Override
    protected void start() {
        add("pendorite_horse_armor", new AddTableLootModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(BuiltInLootTables.NETHER_BRIDGE.location()).build()
        }, NetherDescent.key(Registries.LOOT_TABLE, "chests/nether_bridge")));
    }
}
