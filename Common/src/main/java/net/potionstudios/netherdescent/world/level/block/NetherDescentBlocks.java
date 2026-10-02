package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentFeatures;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentTreeFeatures;
import net.potionstudios.netherdescent.tags.NetherDescentBlockTags;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.custom.*;
import net.potionstudios.netherdescent.world.level.block.plants.*;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.state.properties.NetherDescentBlockSetTypes;
import net.potionstudios.netherdescent.world.level.block.wood.ArisianLeavesBlock;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class NetherDescentBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> BLOCK_ITEMS = new ArrayList<>();

    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocks = new ArrayList<>();

    public static final Supplier<Block> BLUE_NETHERRACK = registerBlockItem(NetherDescentBlockIds.BLUE_NETHERRACK, BlueNetherrackBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_GOLD_ORE = registerCubeAllBlockItem(NetherDescentBlockIds.BLUE_NETHER_GOLD_ORE, (properties) -> new DropExperienceBlock(UniformInt.of(0, 1), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_QUARTZ_ORE = registerCubeAllBlockItem(NetherDescentBlockIds.BLUE_NETHER_QUARTZ_ORE, (properties) -> new DropExperienceBlock(UniformInt.of(2, 5), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE));
    public static final NetherDescentBlockSet BLUE_NETHER_BRICKS = new NetherDescentBlockSet(NetherDescentBlockIds.BLUE_NETHER_BRICKS, NetherDescentBlockIds.BLUE_NETHER_BRICK_STAIRS, NetherDescentBlockIds.BLUE_NETHER_BRICK_SLAB, NetherDescentBlockIds.BLUE_NETHER_BRICK_WALL, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
    public static final Supplier<FenceBlock> BLUE_NETHER_BRICK_FENCE = registerBlockItem(NetherDescentBlockIds.BLUE_NETHER_BRICK_FENCE, FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICK_FENCE));
    public static final NetherDescentBlockSet MOSSY_BLUE_NETHER_BRICKS = new NetherDescentBlockSet(NetherDescentBlockIds.MOSSY_BLUE_NETHER_BRICKS, NetherDescentBlockIds.MOSSY_BLUE_NETHER_BRICK_STAIRS, NetherDescentBlockIds.MOSSY_BLUE_NETHER_BRICK_SLAB, NetherDescentBlockIds.MOSSY_BLUE_NETHER_BRICK_WALL, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
    public static final Supplier<Block> CHISELED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem(NetherDescentBlockIds.CHISELED_BLUE_NETHER_BRICKS, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_NETHER_BRICKS));
    public static final Supplier<Block> CRACKED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem(NetherDescentBlockIds.CRACKED_BLUE_NETHER_BRICKS, BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_NETHER_BRICKS));

    public static final Supplier<NetherDescentNyliumBlock> WAILING_NYLIUM = registerBlockItem(NetherDescentBlockIds.WAILING_NYLIUM, (properties) -> new NetherDescentNyliumBlock(properties, Blocks.SOUL_SOIL, () -> NetherDescentFeatures.WAILING_GARTH_VEGETATION), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_PURPLE));
    public static final Supplier<NDGrowingPlantHeadBlock> WAILING_VINES = registerBlockItem(NetherDescentBlockIds.WAILING_VINES, (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.WAILING_VINES_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).lightLevel((state) -> 10));
    public static final Supplier<NDGrowingPlantBodyBlock> WAILING_VINES_PLANT = registerBlock(NetherDescentBlockIds.WAILING_VINES_PLANT, (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.WAILING_VINES), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).lightLevel((state) -> 10));
    public static final Supplier<Block> WAILING_GRASS = registerBlockItem(NetherDescentBlockIds.WAILING_GRASS, (properties) -> new NetherRootsBlock(NetherDescentBlockTags.SUPPORTS_WAILING_GRASS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS));
    public static final Supplier<WailingGillsBlock> WAILING_GILLS = registerCubeAllBlockItem(NetherDescentBlockIds.WAILING_GILLS, WailingGillsBlock::new, BlockBehaviour.Properties.of().sound(SoundType.SCULK).lightLevel((blockState) -> 14).isRedstoneConductor((blockState, blockGetter, blockPos) -> true));
    public static final Supplier<WailingBulbBlossomBlock> WAILING_BULB_BLOSSOM = registerBlockItem(NetherDescentBlockIds.WAILING_BULB_BLOSSOM, WailingBulbBlossomBlock::new, BlockBehaviour.Properties.of().sound(SoundType.SCULK).noOcclusion().lightLevel((state) -> state.getValue(WailingBulbBlossomBlock.ACTIVE) ? 14 : 8));
    public static final Supplier<Block> WAILING_WART_BLOCK = registerBasicBlockWithItem(NetherDescentBlockIds.WAILING_WART_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_PURPLE));
    public static final NetherDescentWoodSet WAILING = new NetherDescentWoodSet("wailing", MapColor.COLOR_PURPLE, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, WAILING_NYLIUM, NetherDescentTreeFeatures.WAILING_FUNGI_TREES);

    public static final Supplier<NetherDescentNyliumBlock> EMBUR_NYLIUM = registerBlockItem(NetherDescentBlockIds.EMBUR_NYLIUM, (properties) -> new NetherDescentNyliumBlock(properties, BLUE_NETHERRACK, () -> NetherDescentFeatures.EMBUR_BOG_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<Block> EMBUR_GEL_BLOCK = registerBlockItem(NetherDescentBlockIds.EMBUR_GEL_BLOCK, Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).speedFactor(1.2F));
    public static final Supplier<NDGrowingPlantHeadBlock> EMBUR_GEL_VINES = registerBlockItem(NetherDescentBlockIds.EMBUR_GEL_VINES, (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollision().speedFactor(1.2F).strength(0.2F).dynamicShape());
    public static final Supplier<NDGrowingPlantBodyBlock> EMBUR_GEL_VINES_PLANT = registerBlock(NetherDescentBlockIds.EMBUR_GEL_VINES_PLANT, (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.EMBUR_GEL_VINES), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_ORANGE).noCollision().speedFactor(1.2F).strength(0.2F).dynamicShape());
    public static final Supplier<NetherSproutsBlock> EMBUR_SPROUTS = registerBlockItem(NetherDescentBlockIds.EMBUR_SPROUTS, NetherSproutsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<EmburLilyBlock> EMBUR_LILY = registerBlock(NetherDescentBlockIds.EMBUR_LILY, EmburLilyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_ORANGE));
    public static final PottedBlock EMBUR_ROOTS = new PottedBlock(NetherDescentBlockIds.EMBUR_ROOTS, NetherDescentBlockIds.POTTED_EMBUR_ROOTS, registerBlockItem(NetherDescentBlockIds.EMBUR_ROOTS, properties -> new EmburRootsBlock(properties, NetherDescentBlockTags.SUPPORTS_EMBUR_ROOTS), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_EMBUR_ROOTS = registerBlockItem(NetherDescentBlockIds.TALL_EMBUR_ROOTS, NetherDescentDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<EmburCaveMossBlock> EMBUR_CAVE_MOSS = registerBlockItem(NetherDescentBlockIds.EMBUR_CAVE_MOSS, EmburCaveMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(EmburCaveMossBlock.emission(6)).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<BonemealableFeaturePlacerBlock> EMBUR_MOSS_BLOCK = registerCubeAllBlockItem(NetherDescentBlockIds.EMBUR_MOSS_BLOCK, (properties) -> new BonemealableFeaturePlacerBlock(NetherDescentFeatures.EMBUR_MOSS_PATCH_BONEMEAL, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<NDMossyCarpetBlock> EMBUR_MOSS_CARPET = registerBlockItem(NetherDescentBlockIds.EMBUR_MOSS_CARPET, NDMossyCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<HangingMossBlock> EMBUR_HANGING_MOSS = registerBlockItem(NetherDescentBlockIds.EMBUR_HANGING_MOSS, HangingMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollision().strength(0.2F).dynamicShape());
    public static final NetherDescentWoodSet EMBUR = new NetherDescentWoodSet("embur", MapColor.COLOR_BROWN, NetherDescentWoodSet.LogStem.PEDU, NetherDescentWoodSet.GrowerItem.WART, EMBUR_NYLIUM, NetherDescentTreeFeatures.EMBUR_WARTS);

    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_SPROUTS = registerBlockItem(NetherDescentBlockIds.TALL_ARISIAN_SPROUTS, HangingDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final PottedBlock ARISIAN_SPROUTS = new PottedBlock(NetherDescentBlockIds.ARISIAN_SPROUTS, NetherDescentBlockIds.POTTED_ARISIAN_SPROUTS, registerBlockItem(NetherDescentBlockIds.ARISIAN_SPROUTS, (properties) -> new BonemealAbleHangingBushBlock(properties, TALL_ARISIAN_SPROUTS, Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0), Block.box(3.0, 4.0, 3.0, 13.0, 16.0, 13.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<BonemealableFeaturePlacerBlock> ARISIAN_MOSS_BLOCK = registerBlockItem(NetherDescentBlockIds.ARISIAN_MOSS_BLOCK, (properties) -> new BonemealableFeaturePlacerBlock(NetherDescentFeatures.ARISIAN_MOSS_PATCH_BONEMEAL, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingMossyCarpetBlock> ARISIAN_MOSS_CARPET = registerBlockItem(NetherDescentBlockIds.ARISIAN_MOSS_CARPET, HangingMossyCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<NDGrowingPlantHeadBlock> ARISIAN_TANGLE_ROOTS = registerBlockItem(NetherDescentBlockIds.ARISIAN_TANGLE_ROOTS, (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES));
    public static final Supplier<NDGrowingPlantBodyBlock> ARISIAN_TANGLE_ROOTS_PLANT = registerBlock(NetherDescentBlockIds.ARISIAN_TANGLE_ROOTS_PLANT, (properties) -> new NDGrowingPlantBodyBlock(properties, ARISIAN_TANGLE_ROOTS), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES));
    public static final PottedBlock ARISIAN_BLOSSOM = new PottedBlock(NetherDescentBlockIds.ARISIAN_BLOSSOM, NetherDescentBlockIds.POTTED_ARISIAN_BLOSSOM, registerBlockItem(NetherDescentBlockIds.ARISIAN_BLOSSOM, ArisianBlossomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> state.getValue(ArisianBlossomBlock.LIT) ? 14 : 0)));
    public static final Supplier<BranchBlock> ARISIAN_BRANCH = registerBlockItem(NetherDescentBlockIds.ARISIAN_BRANCH, BranchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_DANDELIONS = registerBlockItem(NetherDescentBlockIds.TALL_ARISIAN_DANDELIONS, HangingDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(state -> 12).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingNDBushBlock> ARISIAN_DANDELIONS = registerBlockItem(NetherDescentBlockIds.ARISIAN_DANDELIONS, (properties) -> new BonemealAbleHangingBushBlock(properties, TALL_ARISIAN_DANDELIONS, Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0), Block.box(4.0, 2.0, 4.0, 12.0, 16.0, 12.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(state -> 12));
    public static final NetherDescentWoodSet ARISIAN = new NetherDescentWoodSet("arisian", MapColor.COLOR_LIGHT_BLUE, NetherDescentWoodSet.LogStem.LOG, NetherDescentWoodSet.GrowerItem.SAPLING, ARISIAN_MOSS_BLOCK, NetherDescentTreeFeatures.ARISIAN_TREE1, NetherDescentTreeFeatures.HANGING_ARISIAN_TREES, true);
    public static final Supplier<ArisianLeavesBlock> ARISIAN_LEAVES = registerBlockItem(NetherDescentBlockIds.ARISIAN_LEAVES, ArisianLeavesBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel((state) -> state.getValue(ArisianLeavesBlock.LIT) ? 14 : 0));

    public static final Supplier<ThornSproutBlock> THORN_SPROUT = registerBlockItem(NetherDescentBlockIds.THORN_SPROUT, ThornSproutBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).mapColor(MapColor.COLOR_BROWN).offsetType(BlockBehaviour.OffsetType.NONE).sound(SoundType.FUNGUS));

    public static final Supplier<NetherDescentNyliumBlock> SYTHIAN_NYLIUM = registerBlockItem(NetherDescentBlockIds.SYTHIAN_NYLIUM, (properties) -> new NetherDescentNyliumBlock(properties, Blocks.NETHERRACK, () -> NetherDescentFeatures.SYTHIAN_TORRIDS_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_YELLOW));
    public static final PottedBlock SYTHIAN_SPROUTS = new PottedBlock(NetherDescentBlockIds.SYTHIAN_SPROUTS, NetherDescentBlockIds.POTTED_SYTHIAN_SPROUTS, registerBlockItem(NetherDescentBlockIds.SYTHIAN_SPROUTS, (properties) -> new NetherDescentBush(properties, Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_YELLOW)));
    public static final PottedBlock SYTHIAN_ROOTS = new PottedBlock(NetherDescentBlockIds.SYTHIAN_ROOTS, NetherDescentBlockIds.POTTED_SYTHIAN_ROOTS, registerBlockItem(NetherDescentBlockIds.SYTHIAN_ROOTS, SythianRootsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_YELLOW)));
    public static final Supplier<Block> SYTHIAN_SOIL = registerBasicBlockWithItem(NetherDescentBlockIds.SYTHIAN_SOIL, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final Supplier<SythianFarmBlock> SYTHIAN_FARMLAND = registerBlockItem(NetherDescentBlockIds.SYTHIAN_FARMLAND, (properties) -> new SythianFarmBlock(properties, SYTHIAN_SOIL), BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND).mapColor(MapColor.COLOR_YELLOW));
    public static final Supplier<NDGrowingPlantHeadBlock> HANGING_SYTHIAN_ROOTS = registerBlockItem(NetherDescentBlockIds.HANGING_SYTHIAN_ROOTS, (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_YELLOW).noCollision().strength(0.2F).dynamicShape());
    public static final Supplier<NDGrowingPlantBodyBlock> HANGING_SYTHIAN_ROOTS_PLANT = registerBlock(NetherDescentBlockIds.HANGING_SYTHIAN_ROOTS_PLANT, (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_YELLOW).noCollision().strength(0.2F).dynamicShape());
    public static final Supplier<Block> SYTHIAN_WART_BLOCK = registerBasicBlockWithItem(NetherDescentBlockIds.SYTHIAN_WART_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_YELLOW));
    public static final Supplier<Block> SYTHIAN_SHOOT = registerBlock(NetherDescentBlockIds.SYTHIAN_SHOOT, SythianShootBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING).lightLevel((state) -> 5));
    public static final PottedBlock SYTHIAN_STALK = new PottedBlock(NetherDescentBlockIds.SYTHIAN_STALK, NetherDescentBlockIds.POTTED_SYTHIAN_STALK, registerBlockItem(NetherDescentBlockIds.SYTHIAN_STALK, SythianStalkBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO).lightLevel((state) -> state.getValue(SythianStalkBlock.LEAVES) != BambooLeaves.NONE ? 10 : 5)));
    public static final NetherDescentWoodSet SYTHIAN = new NetherDescentWoodSet("sythian", MapColor.COLOR_YELLOW, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, SYTHIAN_NYLIUM, NetherDescentTreeFeatures.SYTHIAN_FUNGI_TREES);
    public static final Supplier<ScaffoldingBlock> SYTHIAN_SCAFFOLDING = registerBlock(NetherDescentBlockIds.SYTHIAN_SCAFFOLDING, SythianScaffoldingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).mapColor(MapColor.COLOR_YELLOW).lightLevel((blockState) -> 14));

    public static final Supplier<NetherDescentNyliumBlock> CRIMSON_BLACKSTONE_NYLIUM = registerBlockItem(NetherDescentBlockIds.CRIMSON_BLACKSTONE_NYLIUM, (properties) -> new NetherDescentNyliumBlock(properties, Blocks.BLACKSTONE, () -> NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_RED));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_CRIMSON_ROOTS = registerBlockItem(NetherDescentBlockIds.TALL_CRIMSON_ROOTS, NetherDescentDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_RED));
    public static final Supplier<CrimsonBerryBushBlock> CRIMSON_BERRY_BUSH = registerBlock(NetherDescentBlockIds.CRIMSON_BERRY_BUSH, CrimsonBerryBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).mapColor(MapColor.COLOR_RED));
    public static final Supplier<CrimsonCarpetBlock> CRIMSON_CARPET = registerBlockItem(NetherDescentBlockIds.CRIMSON_CARPET, CrimsonCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CARPET.red()).sound(SoundType.MOSS_CARPET));

    public static final Supplier<FungalBulbsBlock> FUNGAL_BULBS = registerBlockItem(NetherDescentBlockIds.FUNGAL_BULBS, FungalBulbsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHROOMLIGHT).lightLevel((state) -> 13).instabreak());

    public static final Supplier<Block> PENDORITE_BLOCK = registerBasicBlockWithItem(NetherDescentBlockIds.PENDORITE_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK.waxed().unaffected()).mapColor(MapColor.METAL));
    public static final Supplier<DropExperienceBlock> PENDORITE_ORE = registerCubeAllBlockItem(NetherDescentBlockIds.PENDORITE_ORE, (properties) -> new DropExperienceBlock(UniformInt.of(3, 7), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).mapColor(MapColor.METAL));
    public static final Supplier<Block> RAW_PENDORITE_BLOCK = registerBasicBlockWithItem(NetherDescentBlockIds.RAW_PENDORITE_BLOCK, BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).mapColor(MapColor.METAL));
    public static final Supplier<Block> CUT_PENDORITE = registerBasicBlockWithItem(NetherDescentBlockIds.CUT_PENDORITE, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER.waxed().unaffected()).mapColor(MapColor.METAL));
    public static final Supplier<StairBlock> CUT_PENDORITE_STAIRS = registerBlockItem(NetherDescentBlockIds.CUT_PENDORITE_STAIRS, (properties) -> new StairBlock(CUT_PENDORITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS.waxed().unaffected()));
    public static final Supplier<SlabBlock> CUT_PENDORITE_SLAB = registerBlockItem(NetherDescentBlockIds.CUT_PENDORITE_SLAB, SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB.waxed().unaffected()));
    public static final Supplier<Block> CHISELED_PENDORITE = registerBasicBlockWithItem(NetherDescentBlockIds.CHISELED_PENDORITE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_COPPER.waxed().unaffected()).mapColor(MapColor.METAL));
    public static final Supplier<WaterloggedTransparentBlock> PENDORITE_GRATE = registerBlockItem(NetherDescentBlockIds.PENDORITE_GRATE, WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE.waxed().unaffected()));
    public static final Supplier<DoorBlock> PENDORITE_DOOR = registerBlockItem(NetherDescentBlockIds.PENDORITE_DOOR, (properties) -> new DoorBlock(NetherDescentBlockSetTypes.PENDORITE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR.waxed().unaffected()));
    public static final Supplier<TrapDoorBlock> PENDORITE_TRAPDOOR = registerBlockItem(NetherDescentBlockIds.PENDORITE_TRAPDOOR, (properties) -> new TrapDoorBlock(NetherDescentBlockSetTypes.PENDORITE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR.waxed().unaffected()));
    public static final Supplier<LanternBlock> PENDORITE_LANTERN = registerBlockItem(NetherDescentBlockIds.PENDORITE_LANTERN, LanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));
    public static final Supplier<TorchBlock> PENDORITE_TORCH = registerBlock(NetherDescentBlockIds.PENDORITE_TORCH, (properties) -> new NDTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH));
    public static final Supplier<WallTorchBlock> PENDORITE_WALL_TORCH = register(NetherDescentBlockIds.PENDORITE_WALL_TORCH, () -> new NDWallTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, Blocks.wallVariant(PENDORITE_TORCH.get(), true).noCollision().instabreak().lightLevel((blockStatex) -> 14).sound(SoundType.WOOD).pushReaction(PushReaction.DESTROY).setId(NetherDescentBlockIds.PENDORITE_WALL_TORCH)));
    public static final Supplier<ChainBlock> PENDORITE_CHAIN = registerBlockItem(NetherDescentBlockIds.PENDORITE_CHAIN, ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN));
    public static final Supplier<IronBarsBlock> PENDORITE_BARS = registerBlockItem(NetherDescentBlockIds.PENDORITE_BARS, IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS));
    public static final Supplier<NetherDescentCampfireBlock> PENDORITE_CAMPFIRE = registerBlockItem(NetherDescentBlockIds.PENDORITE_CAMPFIRE, (properties) -> new NetherDescentCampfireBlock(false, 2, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final Supplier<NDRodBlock> PENDORITE_FIRE_ROD = registerBlockItem(NetherDescentBlockIds.PENDORITE_FIRE_ROD, NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));

    public static final Supplier<NDRodBlock> SOUL_FIRE_ROD = registerBlockItem(NetherDescentBlockIds.SOUL_FIRE_ROD, NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));
    public static final Supplier<NDRodBlock> BLAZE_FIRE_ROD = registerBlockItem(NetherDescentBlockIds.BLAZE_FIRE_ROD, NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));

    public static final Supplier<NetherDescentFeatureDoublePlantBlock> TALL_CRIMSON_FUNGI = registerBlockItem(NetherDescentBlockIds.TALL_CRIMSON_FUNGI, (properties) -> new NetherDescentFeatureDoublePlantBlock(properties, null), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS));

    public static final Supplier<HornetNestBlock> HORNET_NEST = registerBlock(NetherDescentBlockIds.HORNET_NEST, HornetNestBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST));

    public static final Supplier<Block> BARTERING_TABLE = registerBlockItem(NetherDescentBlockIds.BARTERING_TABLE, Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE));

    public static Supplier<Block> registerBasicBlockWithItem(ResourceKey<Block> key, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key, Block::new, properties);
    }

    public static Supplier<Block> registerBasicBlockWithItem(String key, BlockBehaviour.Properties properties) {
        return registerBasicBlockWithItem(key(key), properties);
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(ResourceKey<Block> key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> holder = registerBlockItem(key, block, properties);
        if (PlatformHandler.PLATFORM_HANDLER.isDatagen()) cubeAllBlocks.add(holder);
        return holder;
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(String key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key(key), block, properties);
    }

    public static <B extends Block> Supplier<B> registerBlockItem(ResourceKey<Block> key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> holder = registerBlock(key, block, properties);
        Supplier<Item> item = NetherDescentItems.register(ResourceKey.create(Registries.ITEM, key.identifier()), properties1 -> new BlockItem(holder.get(), properties1), new Item.Properties().useBlockDescriptionPrefix());
        BLOCK_ITEMS.add(item);
        return holder;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return registerBlockItem(key(key), block, properties);
    }

    public static <B extends Block> Supplier<B> registerBlock(ResourceKey<Block> key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> blockSupplier = register(key, block, properties);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return registerBlock(key(id), block, properties);
    }

    public static <B extends Block> Supplier<B> registerBlock(ResourceKey<Block> key, Supplier<B> block) {
        Supplier<B> blockSupplier = register(key, block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        return registerBlock(key(id), block);
    }

    public static <B extends Block> Supplier<B> register(ResourceKey<Block> key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, key.identifier().getPath(), () -> block.apply(properties.setId(key)));
    }

    public static <B extends Block> Supplier<B> register(String id, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return register(key(id), block, properties);
    }

    public static <B extends Block> Supplier<B> register(ResourceKey<Block> key, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, key.identifier().getPath(), block);
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return register(key(id), block);
    }

    public static ResourceKey<Block> key(String id) {
        return NetherDescent.key(Registries.BLOCK, id);
    }

    public static void blocks() {
        NetherDescent.LOGGER.info("Registering Nether Descent Blocks");
    }
}
