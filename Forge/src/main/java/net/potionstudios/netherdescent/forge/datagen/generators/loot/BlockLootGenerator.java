package net.potionstudios.netherdescent.forge.datagen.generators.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.custom.MossyCarpetBlock;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class BlockLootGenerator extends BlockLootSubProvider {
    private final List<Block> knownBlocks = new ArrayList<>();

    protected BlockLootGenerator() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        NetherDescentBlocks.BLOCKS.forEach(entry -> {
            Block block = entry.get();
            if (block instanceof SlabBlock)
                add(block, createSlabItemTable(block));
            else if (block.defaultBlockState().getSoundType() == SoundType.GLASS)
                dropWhenSilkTouch(block);
            else if (Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath().contains("_grass"))
                if (block instanceof DoublePlantBlock) add(block, createDoublePlantWithSeedDrops(block, block));
                else add(block, createGrassDrops(block));
            else if (block instanceof DoublePlantBlock)
                add(block, createSinglePropConditionTable(block, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
            else if (block instanceof CampfireBlock)
                add(block, arg -> createSilkTouchDispatchTable(arg, this.applyExplosionCondition(arg, LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
            else if (block instanceof VineBlock)
                add(block, createShearsDispatchTable(block, LootItem.lootTableItem(block)));
            else if (block instanceof StainedGlassPaneBlock)
                dropWhenSilkTouch(block);
            else if (block instanceof FlowerPotBlock)
                dropPottedContents(block);
            else if (block instanceof PinkPetalsBlock)
                add(block, createPetalsDrops(block));
            else if (block instanceof NetherSproutsBlock)
                add(block, itemLike -> BlockLootSubProvider.createShearsOnlyDrop(block));
            else if (block instanceof MultifaceBlock)
                add(block, createMultifaceBlockDrops(block, HAS_SHEARS));
            else if (block instanceof LanternBlock)
                add(block, this::createSingleItemTable);
            else dropSelf(block);
        });
        dropSelf(NetherDescentBlocks.ARISIAN_DANDELIONS.get());
        dropSelf(NetherDescentBlocks.ARISIAN_BLOSSOM.getBlock());
        dropOther(NetherDescentBlocks.SYTHIAN_FARMLAND.get(), NetherDescentBlocks.SYTHIAN_SOIL.get());
        add(NetherDescentBlocks.BLUE_NETHER_GOLD_ORE.get(), (arg2) -> createSilkTouchDispatchTable(arg2, this.applyExplosionDecay(arg2, LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        add(NetherDescentBlocks.BLUE_NETHER_QUARTZ_ORE.get(), (arg) -> this.createOreDrop(arg, Items.QUARTZ));
        add(NetherDescentBlocks.PENDORITE_ORE.get(), createSilkTouchDispatchTable(NetherDescentBlocks.PENDORITE_ORE.get(), this.applyExplosionDecay(NetherDescentBlocks.PENDORITE_ORE.get(), LootItem.lootTableItem(NetherDescentItems.RAW_PENDORITE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
	    add(NetherDescentBlocks.PENDORITE_CAMPFIRE.get(), (block) -> createSilkTouchDispatchTable(block, this.applyExplosionCondition(block, LootItem.lootTableItem(Items.CHARCOAL).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))))));
        add(NetherDescentBlocks.HORNET_NEST.get(), (block) ->
                LootTable.lootTable()
                    .withPool(
                            LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1))
                                    .add(
                                            LootItem.lootTableItem(block)
                                                    .when(HAS_SILK_TOUCH)
                                                    .apply(CopyNbtFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy("Hornets", "BlockEntityTag.Hornets"))
                                                    .otherwise(LootItem.lootTableItem(block))
                                    )
                    ));
        add(NetherDescentBlocks.EMBUR_MOSS_CARPET.get(), createMossyCarpetBlockDrops(NetherDescentBlocks.EMBUR_MOSS_CARPET.get()));
        add(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get(), createMossyCarpetBlockDrops(NetherDescentBlocks.ARISIAN_MOSS_CARPET.get()));
        add(NetherDescentBlocks.ARISIAN_LEAVES.get(), createLeavesDrops(NetherDescentBlocks.ARISIAN_LEAVES.get(), NetherDescentBlocks.ARISIAN.growerItem().getBlock(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    private LootTable.Builder createMossyCarpetBlockDrops(Block block) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .add(
                                        this.applyExplosionDecay(
                                                block,
                                                LootItem.lootTableItem(block)
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MossyCarpetBlock.BASE, true))
                                                        )
                                        )
                                )
                );
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
