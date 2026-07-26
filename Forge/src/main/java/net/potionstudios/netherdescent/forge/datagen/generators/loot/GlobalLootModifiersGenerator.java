package net.potionstudios.netherdescent.forge.datagen.generators.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.forge.loot.AddTableLootModifier;

public class GlobalLootModifiersGenerator extends GlobalLootModifierProvider {
    public GlobalLootModifiersGenerator(PackOutput output) {
        super(output, NetherDescent.MOD_ID);
    }

    @Override
    protected void start() {
        add("pendorite_horse_armor", new AddTableLootModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(BuiltInLootTables.NETHER_BRIDGE).build()
        }, NetherDescent.id("chests/nether_bridge")));
    }
}
