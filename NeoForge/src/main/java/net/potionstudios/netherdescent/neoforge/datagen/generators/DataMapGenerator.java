package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.neoforged.neoforge.registries.datamaps.builtin.Strippable;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.BlockItemFeatures;
import net.potionstudios.netherdescent.world.item.tools.ToolInteractions;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DataMapGenerator extends DataMapProvider {
    /**
     * Create a new provider.
     *
     * @param packOutput     the output location
     * @param lookupProvider a {@linkplain CompletableFuture} supplying the registries
     */
    public DataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.@NotNull Provider provider) {
        Builder<Compostable, Item> compostableItemBuilder = builder(NeoForgeDataMaps.COMPOSTABLES);
        BlockItemFeatures.registerCompostables((item, chance) -> compostableItemBuilder.add(id(item.asItem()), new Compostable(chance, false), false));
        compostableItemBuilder.conditions(new ModLoadedCondition(NetherDescent.MOD_ID));

        Builder<FurnaceFuel, Item> fuelBuilder = builder(NeoForgeDataMaps.FURNACE_FUELS);
        BlockItemFeatures.registerFurnaceFuels((block, burnTime) -> fuelBuilder.add(id(block.asItem()), new FurnaceFuel(burnTime), false));
        fuelBuilder.conditions(new ModLoadedCondition(NetherDescent.MOD_ID));

        Builder<Strippable, Block> strippableBuilder = builder(NeoForgeDataMaps.STRIPPABLES);
        ToolInteractions.registerStrippableBlocks((block, stripped) -> strippableBuilder.add(block.builtInRegistryHolder(), new Strippable(stripped), false));
        strippableBuilder.conditions(new ModLoadedCondition(NetherDescent.MOD_ID));
    }

    private Identifier id(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
