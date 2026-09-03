package net.potionstudios.netherdescent.neoforge.datagen.generators.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.core.component.NetherDescentDataComponents;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class BlockLootGenerator extends BlockLootSubProvider {

    private final List<Block> knownBlocks = new ArrayList<>();

    protected BlockLootGenerator(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        NetherDescentBlocks.BLOCKS.forEach(entry -> {
	        NetherDescent.LOGGER.info("Generating loot table for block: {}", Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(entry.get())));
            Block block = entry.get();
            if (block instanceof SlabBlock)
                add(block, createSlabItemTable(block));
            else if (block.defaultBlockState().getSoundType() == SoundType.GLASS)
                dropWhenSilkTouch(block);
            else if (Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getPath().contains("_grass"))
                if (block instanceof DoublePlantBlock) add(block, createDoublePlantWithSeedDrops(block, block));
                else add(block, createGrassDrops(block));
            else if (block instanceof DoublePlantBlock)
                add(block, createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            else if (block instanceof CampfireBlock)
                add(block, arg -> createSilkTouchDispatchTable(arg, this.applyExplosionCondition(arg, LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
            else if (block instanceof VineBlock)
                add(block, createShearsDispatchTable(block, LootItem.lootTableItem(block)));
            else if (block instanceof TransparentBlock || block instanceof StainedGlassPaneBlock)
                dropWhenSilkTouch(block);
            else if (block instanceof FlowerPotBlock)
                dropPottedContents(block);
            else if (block instanceof FlowerBedBlock)
                add(block, createSegmentedBlockDrops(block));
            else if (block instanceof NetherSproutsBlock)
                add(block, itemLike -> createShearsOnlyDrop(block));
            else if (block instanceof MultifaceBlock)
                add(block, createMultifaceBlockDrops(block, hasShears()));
            else if (block instanceof LanternBlock)
                add(block, this::createSingleItemTable);
            else {
                if (!Item.byBlock(block).getDefaultInstance().is(Items.AIR))
                    dropSelf(block);
                else NetherDescent.LOGGER.warn("Block {} has no loot table defined and does not have a corresponding item, skipping loot table generation.", BuiltInRegistries.BLOCK.getKey(block));
            }
        });

        NetherDescent.LOGGER.info("Generating loot tables for nylium blocks");

        add(NetherDescentBlocks.EMBUR_NYLIUM.get(), (arg) -> this.createSingleItemTableWithSilkTouch(arg, NetherDescentBlocks.BLUE_NETHERRACK.get()));
        add(NetherDescentBlocks.SYTHIAN_NYLIUM.get(), (arg) -> createSingleItemTableWithSilkTouch(arg, Blocks.NETHERRACK));
        add(NetherDescentBlocks.WAILING_NYLIUM.get(), (arg) -> createSingleItemTableWithSilkTouch(arg, Blocks.SOUL_SOIL));
        add(NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get(), (arg) -> createSingleItemTableWithSilkTouch(arg, Blocks.BLACKSTONE));

        dropSelf(NetherDescentBlocks.ARISIAN_DANDELIONS.get());
        dropSelf(NetherDescentBlocks.ARISIAN_BLOSSOM.get());


        dropOther(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), NetherDescentBlocks.SYTHIAN_SOIL.get());
        add(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get(), (arg2) -> this.createSilkTouchDispatchTable(arg2, this.applyExplosionDecay(arg2, LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))))));
        add(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get(), (arg) -> this.createOreDrop(arg, Items.QUARTZ));
        add(NetherDescentBlocks.PENDORITE_ORE.get(), createSilkTouchDispatchTable(NetherDescentBlocks.PENDORITE_ORE.get(), this.applyExplosionDecay(NetherDescentBlocks.PENDORITE_ORE.get(), LootItem.lootTableItem(NetherDescentItems.RAW_PENDORITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))))));
	    add(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), (block) -> this.createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
        add(NetherDescentBlocks.HORNET_NEST.get(), (block) ->
                LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()
                                    .when(this.hasSilkTouch())
                                    .setRolls(ConstantValue.exactly(1))
                                    .add(
                                            LootItem.lootTableItem(block)
                                                    .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(NetherDescentDataComponents.HORNETS.get()))
                                    )
                    ));
        add(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), createMossyCarpetBlockDrops(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()));
        add(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), createMossyCarpetBlockDrops(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get()));
        add(NetherDescentBlocks.ARISIAN_LEAVES.get(), createLeavesDrops(NetherDescentBlocks.ARISIAN_LEAVES.get(), NetherDescentBlocks.ARISIAN.growerItem().get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected void add(@NotNull Block block, LootTable.@NotNull Builder lootTableBuilder) {
        knownBlocks.add(block);
        super.add(block, lootTableBuilder);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
