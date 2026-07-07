package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.material.MapColor;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentFeatures;
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentTreeFeatures;
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

    public static final Supplier<Block> BLUE_NETHERRACK = registerBlockItem("blue_netherrack", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_GOLD_ORE = registerCubeAllBlockItem("blue_nether_gold_ore", (properties) -> new DropExperienceBlock(UniformInt.of(0, 1), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_QUARTZ_ORE = registerCubeAllBlockItem("blue_nether_quartz_ore", (properties) -> new DropExperienceBlock(UniformInt.of(2, 5), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE));
    public static final NetherDescentBlockSet BLUE_NETHER_BRICKS = new NetherDescentBlockSet("blue_nether_bricks", "blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<FenceBlock> BLUE_NETHER_BRICK_FENCE = registerBlockItem("blue_nether_brick_fence", FenceBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICK_FENCE));
	public static final NetherDescentBlockSet MOSSY_BLUE_NETHER_BRICKS = new NetherDescentBlockSet("mossy_blue_nether_bricks", "mossy_blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<Block> CHISELED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("chiseled_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_NETHER_BRICKS));
	public static final Supplier<Block> CRACKED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("cracked_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_NETHER_BRICKS));

    public static final Supplier<NetherDescentNyliumBlock> WAILING_NYLIUM = registerBlockItem("wailing_nylium", (properties) -> new NetherDescentNyliumBlock(properties, Blocks.SOUL_SAND, () -> NetherDescentFeatures.WAILING_GARTH_VEGETATION), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_PURPLE));
    public static final Supplier<NDGrowingPlantHeadBlock> WAILING_VINES = registerBlockItem("wailing_vines", (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.WAILING_VINES_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).lightLevel((_) -> 10));
    public static final Supplier<NDGrowingPlantBodyBlock> WAILING_VINES_PLANT = registerBlock("wailing_vines_plant", (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.WAILING_VINES), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).lightLevel((_) -> 10));
    public static final Supplier<Block> WAILING_GRASS = registerBlockItem("wailing_grass", NetherRootsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS));
	public static final Supplier<WailingGillsBlock> WAILING_GILLS = registerCubeAllBlockItem("wailing_gills", WailingGillsBlock::new, BlockBehaviour.Properties.of().sound(SoundType.SCULK).lightLevel((_) -> 14).isRedstoneConductor((_, _, _) -> true));
	public static final Supplier<WailingBulbBlossomBlock> WAILING_BULB_BLOSSOM = registerBlockItem("wailing_bulb_blossom", WailingBulbBlossomBlock::new, BlockBehaviour.Properties.of().noOcclusion().lightLevel((state) -> state.getValue(WailingBulbBlossomBlock.ACTIVE) ? 14 : 8));
    public static final Supplier<Block> WAILING_WART_BLOCK = registerBasicBlockWithItem("wailing_wart_block", BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_PURPLE));
    public static final NetherDescentWoodSet WAILING = new NetherDescentWoodSet("wailing", MapColor.COLOR_PURPLE, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, WAILING_NYLIUM, NetherDescentTreeFeatures.WAILING_FUNGI_TREES);

    public static final Supplier<NetherDescentNyliumBlock> EMBUR_NYLIUM = registerBlockItem("embur_nylium", (properties) -> new NetherDescentNyliumBlock(properties, BLUE_NETHERRACK, () -> NetherDescentFeatures.EMBUR_BOG_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<Block> EMBUR_GEL_BLOCK = registerBlockItem("embur_gel_block", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).speedFactor(1.2F));
    public static final Supplier<NDGrowingPlantHeadBlock> EMBUR_GEL_VINES = registerBlockItem("embur_gel_vines", (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollision().speedFactor(1.2F).strength(0.2F).dynamicShape());
    public static final Supplier<NDGrowingPlantBodyBlock> EMBUR_GEL_VINES_PLANT = registerBlock("embur_gel_vines_plant", (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.EMBUR_GEL_VINES), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_ORANGE).noCollision().speedFactor(1.2F).strength(0.2F).dynamicShape());
    public static final Supplier<NetherSproutsBlock> EMBUR_SPROUTS = registerBlockItem("embur_sprouts", NetherSproutsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<EmburLilyBlock> EMBUR_LILY = registerBlock("embur_lily", EmburLilyBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_ORANGE));
    public static final PottedBlock EMBUR_ROOTS = new PottedBlock("embur_roots", registerBlockItem("embur_roots", (properties) -> new EmburRootsBlock(BlockTags.SUPPORTS_CRIMSON_ROOTS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_EMBUR_ROOTS = registerBlockItem("tall_embur_roots", NetherDescentDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<EmburCaveMossBlock> EMBUR_CAVE_MOSS = registerBlockItem("embur_cave_moss", EmburCaveMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(EmburCaveMossBlock.emission(6)).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<BonemealableFeaturePlacerBlock> EMBUR_MOSS_BLOCK = registerCubeAllBlockItem("embur_moss_block", (properties) -> new BonemealableFeaturePlacerBlock(NetherDescentFeatures.EMBUR_MOSS_PATCH_BONEMEAL,properties),  BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<MossyCarpetBlock> EMBUR_MOSS_CARPET = registerBlockItem("embur_moss_carpet", MossyCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_ORANGE));
    public static final Supplier<HangingMossBlock> EMBUR_HANGING_MOSS = registerBlockItem("embur_hanging_moss", HangingMossBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollision().strength(0.2F).dynamicShape());
    public static final NetherDescentWoodSet EMBUR = new NetherDescentWoodSet("embur", MapColor.COLOR_BROWN, NetherDescentWoodSet.LogStem.PEDU, NetherDescentWoodSet.GrowerItem.WART, EMBUR_NYLIUM, NetherDescentTreeFeatures.EMBUR_WARTS);

    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_SPROUTS = registerBlockItem("tall_arisian_sprouts", HangingDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final PottedBlock ARISIAN_SPROUTS = new PottedBlock("arisian_sprouts", registerBlockItem("arisian_sprouts", (properties) -> new BonemealAbleHangingBushBlock(properties, TALL_ARISIAN_SPROUTS, Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0), Block.box(3.0, 4.0, 3.0, 13.0, 16.0, 13.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<BonemealableFeaturePlacerBlock> ARISIAN_MOSS_BLOCK = registerBlockItem("arisian_moss_block", (properties) -> new BonemealableFeaturePlacerBlock(NetherDescentFeatures.ARISIAN_MOSS_PATCH_BONEMEAL, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingMossyCarpetBlock> ARISIAN_MOSS_CARPET = registerBlockItem("arisian_moss_carpet", HangingMossyCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<NDGrowingPlantHeadBlock> ARISIAN_TANGLE_ROOTS = registerBlockItem("arisian_tangle_roots", (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES));
    public static final Supplier<NDGrowingPlantBodyBlock> ARISIAN_TANGLE_ROOTS_PLANT = registerBlock("arisian_tangle_roots_plant", (properties) -> new NDGrowingPlantBodyBlock(properties, ARISIAN_TANGLE_ROOTS), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES));
    public static final PottedBlock ARISIAN_BLOSSOM = new PottedBlock("arisian_blossom", registerBlockItem("arisian_blossom", ArisianBlossomBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> state.getValue(ArisianBlossomBlock.LIT) ? 14 : 0)));
    public static final Supplier<BranchBlock> ARISIAN_BRANCH = registerBlockItem("arisian_branch", BranchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_DANDELIONS = registerBlockItem("tall_arisian_dandelions", HangingDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(_ -> 12).mapColor(MapColor.COLOR_LIGHT_BLUE));
    public static final Supplier<HangingNDBushBlock> ARISIAN_DANDELIONS = registerBlockItem("arisian_dandelions", (properties) -> new BonemealAbleHangingBushBlock(properties, TALL_ARISIAN_DANDELIONS, Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0), Block.box(4.0, 2.0, 4.0, 12.0, 16.0, 12.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(_ -> 12));
    public static final NetherDescentWoodSet ARISIAN = new NetherDescentWoodSet("arisian", MapColor.COLOR_LIGHT_BLUE, NetherDescentWoodSet.LogStem.LOG, NetherDescentWoodSet.GrowerItem.SAPLING, ARISIAN_MOSS_BLOCK, NetherDescentTreeFeatures.ARISIAN_TREE1, NetherDescentTreeFeatures.HANGING_ARISIAN_TREES, true);
    public static final Supplier<ArisianLeavesBlock> ARISIAN_LEAVES = registerBlockItem("arisian_leaves", (properties) -> new ArisianLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel((state) -> state.getValue(ArisianLeavesBlock.LIT) ? 14 : 0)));

    public static final Supplier<ThornSproutBlock> THORN_SPROUT = registerBlockItem("thorn_sprout", ThornSproutBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).mapColor(MapColor.COLOR_BROWN).offsetType(BlockBehaviour.OffsetType.NONE).sound(SoundType.FUNGUS));

    public static final Supplier<NetherDescentNyliumBlock> SYTHIAN_NYLIUM = registerBlockItem("sythian_nylium", (properties) -> new NetherDescentNyliumBlock(properties, Blocks.NETHERRACK, () -> NetherDescentFeatures.SYTHIAN_TORRIDS_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_YELLOW));
    public static final PottedBlock SYTHIAN_SPROUTS = new PottedBlock("sythian_sprouts",registerBlockItem("sythian_sprouts", (properties) -> new NetherDescentBush(properties, Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0)), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_YELLOW)));
    public static final PottedBlock SYTHIAN_ROOTS = new PottedBlock("sythian_roots",registerBlockItem("sythian_roots", (properties) -> new SythianRootsBlock(BlockTags.SUPPORTS_CRIMSON_ROOTS, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_YELLOW)));
    public static final Supplier<Block> SYTHIAN_SOIL = registerBasicBlockWithItem("sythian_soil", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final Supplier<SythianFarmBlock> SYTHIAN_FARMLAND = registerBlockItem("sythian_farmland", (properties) -> new SythianFarmBlock(properties, SYTHIAN_SOIL), BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND));
    public static final Supplier<NDGrowingPlantHeadBlock> HANGING_SYTHIAN_ROOTS = registerBlockItem("hanging_sythian_roots", (properties) -> new NDGrowingPlantHeadBlock(properties, () -> NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get()), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_YELLOW).noCollision().strength(0.2F).dynamicShape());
    public static final Supplier<NDGrowingPlantBodyBlock> HANGING_SYTHIAN_ROOTS_PLANT = registerBlock("hanging_sythian_roots_plant", (properties) -> new NDGrowingPlantBodyBlock(properties, NetherDescentBlocks.HANGING_SYTHIAN_ROOTS), BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_YELLOW).noCollision().strength(0.2F).dynamicShape());
    public static final Supplier<Block> SYTHIAN_WART_BLOCK = registerBasicBlockWithItem("sythian_wart_block", BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_YELLOW));
    public static final Supplier<Block> SYTHIAN_SHOOT = registerBlock("sythian_shoot", SythianShootBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING).lightLevel((_) -> 5));
    public static final PottedBlock SYTHIAN_STALK = new PottedBlock("sythian_stalk", registerBlockItem("sythian_stalk", SythianStalkBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO).lightLevel((state) -> state.getValue(SythianStalkBlock.LEAVES) != BambooLeaves.NONE ? 10 : 5)));
    public static final NetherDescentWoodSet SYTHIAN = new NetherDescentWoodSet("sythian", MapColor.COLOR_YELLOW, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, SYTHIAN_NYLIUM, NetherDescentTreeFeatures.SYTHIAN_FUNGI_TREES);
    public static final Supplier<ScaffoldingBlock> SYTHIAN_SCAFFOLDING = registerBlock("sythian_scaffolding", SythianScaffoldingBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).mapColor(MapColor.COLOR_YELLOW).lightLevel((_) -> 14));

    public static final Supplier<NetherDescentNyliumBlock> CRIMSON_BLACKSTONE_NYLIUM = registerBlockItem("crimson_blackstone_nylium", (properties) -> new NetherDescentNyliumBlock(properties, Blocks.BLACKSTONE, () -> NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_RED));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_CRIMSON_ROOTS = registerBlockItem("tall_crimson_roots", NetherDescentDoublePlantBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_RED));
    public static final Supplier<CrimsonBerryBushBlock> CRIMSON_BERRY_BUSH = registerBlock("crimson_berry_bush", CrimsonBerryBushBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).mapColor(MapColor.COLOR_RED));
    public static final Supplier<CrimsonCarpetBlock> CRIMSON_CARPET = registerBlockItem("crimson_carpet", CrimsonCarpetBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET));

    public static final Supplier<FungalBulbsBlock> FUNGAL_BULBS = registerBlockItem("fungal_bulbs", FungalBulbsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SHROOMLIGHT).lightLevel((_) -> 13));

    public static final Supplier<Block> PENDORITE_BLOCK = registerBasicBlockWithItem("pendorite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL));
    public static final Supplier<DropExperienceBlock> PENDORITE_ORE = registerCubeAllBlockItem("pendorite_ore", (properties) -> new DropExperienceBlock(UniformInt.of(3, 7), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).mapColor(MapColor.METAL));
    public static final Supplier<Block> RAW_PENDORITE_BLOCK = registerBasicBlockWithItem("raw_pendorite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).mapColor(MapColor.METAL));
    public static final Supplier<Block> CUT_PENDORITE = registerBasicBlockWithItem("cut_pendorite", BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER).mapColor(MapColor.METAL));
    public static final Supplier<StairBlock> CUT_PENDORITE_STAIRS = registerBlockItem("cut_pendorite_stairs", (properties) -> new StairBlock(CUT_PENDORITE.get().defaultBlockState(), properties), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS));
    public static final Supplier<SlabBlock> CUT_PENDORITE_SLAB = registerBlockItem("cut_pendorite_slab", SlabBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB));
    public static final Supplier<Block> CHISELED_PENDORITE = registerBasicBlockWithItem("chiseled_pendorite", BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_COPPER).mapColor(MapColor.METAL));
    public static final Supplier<WaterloggedTransparentBlock> PENDORITE_GRATE = registerBlockItem("pendorite_grate", WaterloggedTransparentBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE));
	public static final Supplier<DoorBlock> PENDORITE_DOOR = registerBlockItem("pendorite_door", (properties) -> new DoorBlock(NetherDescentBlockSetTypes.PENDORITE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR));
	public static final Supplier<TrapDoorBlock> PENDORITE_TRAPDOOR = registerBlockItem("pendorite_trapdoor", (properties) -> new TrapDoorBlock(NetherDescentBlockSetTypes.PENDORITE, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR));
    public static final Supplier<LanternBlock> PENDORITE_LANTERN = registerBlockItem("pendorite_lantern", LanternBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN));
    public static final Supplier<TorchBlock> PENDORITE_TORCH = registerBlock("pendorite_torch", (properties) -> new NDTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH));
    public static final Supplier<WallTorchBlock> PENDORITE_WALL_TORCH = register("pendorite_wall_torch", (properties) -> new NDWallTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH));
    public static final Supplier<ChainBlock> PENDORITE_CHAIN = registerBlockItem("pendorite_chain", ChainBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_CHAIN));
    public static final Supplier<IronBarsBlock> PENDORITE_BARS = registerBlockItem("pendorite_bars", IronBarsBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS));
	public static final Supplier<NetherDescentCampfireBlock> PENDORITE_CAMPFIRE = registerBlockItem("pendorite_campfire", (properties) -> new NetherDescentCampfireBlock(false, 2, properties), BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE));
    public static final Supplier<NDRodBlock> PENDORITE_FIRE_ROD = registerBlockItem("pendorite_fire_rod", NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));

    public static final Supplier<NDRodBlock> SOUL_FIRE_ROD = registerBlockItem("soul_fire_rod", NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));
    public static final Supplier<NDRodBlock> BLAZE_FIRE_ROD = registerBlockItem("blaze_fire_rod", NDRodBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD));

    public static final Supplier<NetherDescentFeatureDoublePlantBlock> TALL_CRIMSON_FUNGI = registerBlockItem("tall_crimson_fungi", (properties) -> new NetherDescentFeatureDoublePlantBlock(properties, null), BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS));

	public static final Supplier<HornetNestBlock> HORNET_NEST = registerBlock("hornet_nest", HornetNestBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST));

    public static final Supplier<Block> BARTERING_TABLE = registerBlockItem("bartering_table", Block::new, BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE));

    public static Supplier<Block> registerBasicBlockWithItem(String key, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key, Block::new, properties);
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(String key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> holder = registerBlockItem(key, block, properties);
        if (PlatformHandler.PLATFORM_HANDLER.isDatagen()) cubeAllBlocks.add(holder);
        return holder;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> holder = registerBlock(key, block, properties);
        Supplier<Item> item = NetherDescentItems.register(key, properties1 -> new BlockItem(holder.get(), properties1), new Item.Properties().useBlockDescriptionPrefix());
        BLOCK_ITEMS.add(item);
        return holder;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
        Supplier<B> holder = registerBlock(key, block, properties);
        Supplier<Item> item = NetherDescentItems.register(key, properties1 -> new BlockItem(holder.get(), properties1), itemProperties);
        BLOCK_ITEMS.add(item);
        return holder;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        Supplier<B> blockSupplier = register(id, block, properties);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        Supplier<B> blockSupplier = register(id, block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> register(String id, Function<BlockBehaviour.Properties, B> block, BlockBehaviour.Properties properties) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, () -> block.apply(properties.setId(key(id))));
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, block);
    }

    private static ResourceKey<Block> key(String id) {
        return NetherDescent.key(Registries.BLOCK, id);
    }

    public static void blocks() {
        NetherDescent.LOGGER.info("Registering Nether Descent Blocks");
    }
}
