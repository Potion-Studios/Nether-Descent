package net.potionstudios.netherdescent.world.level.block;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
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
import net.potionstudios.netherdescent.data.worldgen.features.NetherDescentTreeFeatures;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.custom.*;
import net.potionstudios.netherdescent.world.level.block.plants.*;
import net.potionstudios.netherdescent.world.level.block.set.NetherDescentBlockSet;
import net.potionstudios.netherdescent.world.level.block.state.properties.NetherDescentBlockSetTypes;
import net.potionstudios.netherdescent.world.level.block.wood.ArisianLeavesBlock;
import net.potionstudios.netherdescent.world.level.block.wood.NetherDescentWoodSet;

import java.util.ArrayList;
import java.util.function.Supplier;

public class NetherDescentBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();
    public static final ArrayList<Supplier<? extends Item>> BLOCK_ITEMS = new ArrayList<>();

    public static final ArrayList<Supplier<? extends Block>> cubeAllBlocks = new ArrayList<>();

    public static final Supplier<Block> BLUE_NETHERRACK = registerBlockItem("blue_netherrack", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_GOLD_ORE = registerCubeAllBlockItem("blue_nether_gold_ore", () -> new DropExperienceBlock(UniformInt.of(0, 1), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_GOLD_ORE)));
    public static final Supplier<DropExperienceBlock> BLUE_NETHER_QUARTZ_ORE = registerCubeAllBlockItem("blue_nether_quartz_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_QUARTZ_ORE)));
    public static final NetherDescentBlockSet BLUE_NETHER_BRICKS = new NetherDescentBlockSet("blue_nether_bricks", "blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<FenceBlock> BLUE_NETHER_BRICK_FENCE = registerBlockItem("blue_nether_brick_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICK_FENCE)));
	public static final NetherDescentBlockSet MOSSY_BLUE_NETHER_BRICKS = new NetherDescentBlockSet("mossy_blue_nether_bricks", "mossy_blue_nether_brick", BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS));
	public static final Supplier<Block> CHISELED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("chiseled_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_NETHER_BRICKS));
	public static final Supplier<Block> CRACKED_BLUE_NETHER_BRICKS = registerBasicBlockWithItem("cracked_blue_nether_bricks", BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_NETHER_BRICKS));

    public static final Supplier<NetherDescentNyliumBlock> WAILING_NYLIUM = registerBlockItem("wailing_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_PURPLE), Blocks.SOUL_SAND, null));
    public static final Supplier<NDGrowingPlantHeadBlock> WAILING_VINES = registerBlockItem("wailing_vines", () -> new NDGrowingPlantHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).lightLevel((state) -> 10), () -> NetherDescentBlocks.WAILING_VINES_PLANT.get()));
    public static final Supplier<NDGrowingPlantBodyBlock> WAILING_VINES_PLANT = registerBlock("wailing_vines_plant", () -> new NDGrowingPlantBodyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).lightLevel((state) -> 10), NetherDescentBlocks.WAILING_VINES));
    public static final Supplier<Block> WAILING_GRASS = registerBlockItem("wailing_grass", () -> new RootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS)));
	public static final Supplier<WailingGillsBlock> WAILING_GILLS = registerCubeAllBlockItem("wailing_gills", () -> new WailingGillsBlock(BlockBehaviour.Properties.of().sound(SoundType.SCULK).lightLevel((blockState) -> 14).isRedstoneConductor((blockState, blockGetter, blockPos) -> true)));
	public static final Supplier<WailingBulbBlossomBlock> WAILING_BULB_BLOSSOM = registerBlockItem("wailing_bulb_blossom", () -> new WailingBulbBlossomBlock(BlockBehaviour.Properties.of().noOcclusion().lightLevel((state) -> state.getValue(WailingBulbBlossomBlock.ACTIVE) ? 14 : 8)));
    public static final Supplier<Block> WAILING_WART_BLOCK = registerBasicBlockWithItem("wailing_wart_block", BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_PURPLE));
    public static final NetherDescentWoodSet WAILING = new NetherDescentWoodSet("wailing", MapColor.COLOR_PURPLE, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, WAILING_NYLIUM, NetherDescentTreeFeatures.WAILING_FUNGI_TREES);

    public static final Supplier<NetherDescentNyliumBlock> EMBUR_NYLIUM = registerBlockItem("embur_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_ORANGE), BLUE_NETHERRACK, NetherDescent.key(Registries.CONFIGURED_FEATURE, "embur_bog_vegetation_bonemeal")));
    public static final Supplier<Block> EMBUR_GEL_BLOCK = registerBlockItem("embur_gel_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SLIME_BLOCK).speedFactor(1.2F)));
    public static final Supplier<NDGrowingPlantHeadBlock> EMBUR_GEL_VINES = registerBlockItem("embur_gel_vines", () -> new NDGrowingPlantHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollission().speedFactor(1.2F).strength(0.2F).dynamicShape(), () -> NetherDescentBlocks.EMBUR_GEL_VINES_PLANT.get()));
    public static final Supplier<NDGrowingPlantBodyBlock> EMBUR_GEL_VINES_PLANT = registerBlock("embur_gel_vines_plant", () -> new NDGrowingPlantBodyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_ORANGE).noCollission().speedFactor(1.2F).strength(0.2F).dynamicShape(), NetherDescentBlocks.EMBUR_GEL_VINES));
    public static final Supplier<NetherSproutsBlock> EMBUR_SPROUTS = registerBlockItem("embur_sprouts", () -> new NetherSproutsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<EmburLilyBlock> EMBUR_LILY = registerBlock("embur_lily", () -> new EmburLilyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_ORANGE)));
    public static final PottedBlock EMBUR_ROOTS = new PottedBlock("embur_roots", registerBlockItem("embur_roots", () -> new EmburRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE))));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_EMBUR_ROOTS = registerBlockItem("tall_embur_roots", () -> new NetherDescentDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<EmburCaveMossBlock> EMBUR_CAVE_MOSS = registerBlockItem("embur_cave_moss", () -> new EmburCaveMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLOW_LICHEN).lightLevel(EmburCaveMossBlock.emission(6)).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<BonemealableFeaturePlacerBlock> EMBUR_MOSS_BLOCK = registerCubeAllBlockItem("embur_moss_block", () -> new BonemealableFeaturePlacerBlock(null, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<MossyCarpetBlock> EMBUR_MOSS_CARPET = registerBlockItem("embur_moss_carpet", () -> new MossyCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_ORANGE)));
    public static final Supplier<HangingMossBlock> EMBUR_HANGING_MOSS = registerBlockItem("embur_hanging_moss", () -> new HangingMossBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_ORANGE).noCollission().strength(0.2F).dynamicShape()));
    public static final NetherDescentWoodSet EMBUR = new NetherDescentWoodSet("embur", MapColor.COLOR_BROWN, NetherDescentWoodSet.LogStem.PEDU, NetherDescentWoodSet.GrowerItem.WART, EMBUR_NYLIUM, NetherDescentTreeFeatures.EMBUR_WARTS);

    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_SPROUTS = registerBlockItem("tall_arisian_sprouts", () -> new HangingDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final PottedBlock ARISIAN_SPROUTS = new PottedBlock("arisian_sprouts", registerBlockItem("arisian_sprouts", () -> new BonemealAbleHangingBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_LIGHT_BLUE), TALL_ARISIAN_SPROUTS, Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0), Block.box(3.0, 4.0, 3.0, 13.0, 16.0, 13.0))));
    public static final Supplier<BonemealableFeaturePlacerBlock> ARISIAN_MOSS_BLOCK = registerBlockItem("arisian_moss_block", () -> new BonemealableFeaturePlacerBlock(null, BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<HangingMossyCarpetBlock> ARISIAN_MOSS_CARPET = registerBlockItem("arisian_moss_carpet", () -> new HangingMossyCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<NDGrowingPlantHeadBlock> ARISIAN_TANGLE_ROOTS = registerBlockItem("arisian_tangle_roots", () -> new NDGrowingPlantHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES), () -> NetherDescentBlocks.ARISIAN_TANGLE_ROOTS_PLANT.get()));
    public static final Supplier<NDGrowingPlantBodyBlock> ARISIAN_TANGLE_ROOTS_PLANT = registerBlock("arisian_tangle_roots_plant", () -> new NDGrowingPlantBodyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES), ARISIAN_TANGLE_ROOTS));
    public static final PottedBlock ARISIAN_BLOSSOM = new PottedBlock("arisian_blossom", registerBlockItem("arisian_blossom", () -> new ArisianBlossomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.COLOR_LIGHT_BLUE).lightLevel((state) -> state.getValue(ArisianBlossomBlock.LIT) ? 14 : 0))));
    public static final Supplier<BranchBlock> ARISIAN_BRANCH = registerBlockItem("arisian_branch", () -> new BranchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<HangingDoublePlantBlock> TALL_ARISIAN_DANDELIONS = registerBlockItem("tall_arisian_dandelions", () -> new HangingDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(state -> 12).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final Supplier<HangingNDBushBlock> ARISIAN_DANDELIONS = registerBlockItem("arisian_dandelions", () -> new BonemealAbleHangingBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).lightLevel(state -> 12), TALL_ARISIAN_DANDELIONS, Block.box(4.0, 0.0, 4.0, 12.0, 14.0, 12.0), Block.box(4.0, 2.0, 4.0, 12.0, 16.0, 12.0)));
    public static final NetherDescentWoodSet ARISIAN = new NetherDescentWoodSet("arisian", MapColor.COLOR_LIGHT_BLUE, NetherDescentWoodSet.LogStem.LOG, NetherDescentWoodSet.GrowerItem.SAPLING, ARISIAN_MOSS_BLOCK, null);
    public static final Supplier<ArisianLeavesBlock> ARISIAN_LEAVES = registerBlockItem("arisian_leaves", () -> new ArisianLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).lightLevel((state) -> state.getValue(ArisianLeavesBlock.LIT) ? 14 : 0)));

    public static final Supplier<ThornSproutBlock> THORN_SPROUT = registerBlockItem("thorn_sprout", () -> new ThornSproutBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE).mapColor(MapColor.COLOR_BROWN).offsetType(BlockBehaviour.OffsetType.NONE).sound(SoundType.FUNGUS)));

    public static final Supplier<NetherDescentNyliumBlock> SYTHIAN_NYLIUM = registerBlockItem("sythian_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_YELLOW), Blocks.NETHERRACK, NetherDescent.key(Registries.CONFIGURED_FEATURE, "sythian_forest_vegetation_bonemeal")));
    public static final PottedBlock SYTHIAN_SPROUTS = new PottedBlock("sythian_sprouts",registerBlockItem("sythian_sprouts", () -> new NetherDescentBush(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_SPROUTS).mapColor(MapColor.COLOR_YELLOW), Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0))));
    public static final PottedBlock SYTHIAN_ROOTS = new PottedBlock("sythian_roots",registerBlockItem("sythian_roots", () -> new SythianRootsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_YELLOW))));
    public static final Supplier<Block> SYTHIAN_SOIL = registerBasicBlockWithItem("sythian_soil", BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT));
    public static final Supplier<SythianFarmBlock> SYTHIAN_FARMLAND = registerBlockItem("sythian_farmland", () -> new SythianFarmBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND), SYTHIAN_SOIL));
    public static final Supplier<NDGrowingPlantHeadBlock> HANGING_SYTHIAN_ROOTS = registerBlockItem("hanging_sythian_roots", () -> new NDGrowingPlantHeadBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES).mapColor(MapColor.COLOR_YELLOW).noCollission().strength(0.2F).dynamicShape(), () -> NetherDescentBlocks.HANGING_SYTHIAN_ROOTS_PLANT.get()));
    public static final Supplier<NDGrowingPlantBodyBlock> HANGING_SYTHIAN_ROOTS_PLANT = registerBlock("hanging_sythian_roots_plant", () -> new NDGrowingPlantBodyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TWISTING_VINES_PLANT).mapColor(MapColor.COLOR_YELLOW).noCollission().strength(0.2F).dynamicShape(), NetherDescentBlocks.HANGING_SYTHIAN_ROOTS));
    public static final Supplier<Block> SYTHIAN_WART_BLOCK = registerBasicBlockWithItem("sythian_wart_block", BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_WART_BLOCK).mapColor(MapColor.COLOR_YELLOW));
    public static final Supplier<Block> SYTHIAN_SHOOT = registerBlock("sythian_shoot", () -> new SythianShootBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO_SAPLING).lightLevel((state) -> 5)));
    public static final PottedBlock SYTHIAN_STALK = new PottedBlock("sythian_stalk", registerBlockItem("sythian_stalk", () -> new SythianStalkBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BAMBOO).lightLevel((state) -> state.getValue(SythianStalkBlock.LEAVES) != BambooLeaves.NONE ? 10 : 5))));
    public static final NetherDescentWoodSet SYTHIAN = new NetherDescentWoodSet("sythian", MapColor.COLOR_YELLOW, NetherDescentWoodSet.LogStem.STEM, NetherDescentWoodSet.GrowerItem.FUNGUS, SYTHIAN_NYLIUM, NetherDescentTreeFeatures.SYTHIAN_FUNGI_TREES);
    public static final Supplier<ScaffoldingBlock> SYTHIAN_SCAFFOLDING = registerBlock("sythian_scaffolding", () -> new SythianScaffoldingBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SCAFFOLDING).mapColor(MapColor.COLOR_YELLOW).lightLevel((blockState) -> 14)));

    public static final Supplier<NyliumBlock> CRIMSON_BLACKSTONE_NYLIUM = registerBlockItem("crimson_blackstone_nylium", () -> new NetherDescentNyliumBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM).mapColor(MapColor.COLOR_RED), Blocks.BLACKSTONE, NetherFeatures.CRIMSON_FOREST_VEGETATION_BONEMEAL));
    public static final Supplier<NetherDescentDoublePlantBlock> TALL_CRIMSON_ROOTS = registerBlockItem("tall_crimson_roots", () -> new NetherDescentDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_ROOTS).mapColor(MapColor.COLOR_RED)));
    public static final Supplier<CrimsonBerryBushBlock> CRIMSON_BERRY_BUSH = registerBlock("crimson_berry_bush", () -> new CrimsonBerryBushBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SWEET_BERRY_BUSH).mapColor(MapColor.COLOR_RED)));
    public static final Supplier<CrimsonCarpetBlock> CRIMSON_CARPET = registerBlockItem("crimson_carpet", () -> new CrimsonCarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_CARPET)));

    public static final Supplier<FungalBulbsBlock> FUNGAL_BULBS = registerBlockItem("fungal_bulbs", () -> new FungalBulbsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SHROOMLIGHT).lightLevel((state) -> 13)));

    public static final Supplier<Block> PENDORITE_BLOCK = registerBasicBlockWithItem("pendorite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL));
    public static final Supplier<DropExperienceBlock> PENDORITE_ORE = registerCubeAllBlockItem("pendorite_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_ORE).mapColor(MapColor.METAL)));
    public static final Supplier<Block> RAW_PENDORITE_BLOCK = registerBasicBlockWithItem("raw_pendorite_block", BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_COPPER_BLOCK).mapColor(MapColor.METAL));
    public static final Supplier<Block> CUT_PENDORITE = registerBasicBlockWithItem("cut_pendorite", BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER).mapColor(MapColor.METAL));
    public static final Supplier<StairBlock> CUT_PENDORITE_STAIRS = registerBlockItem("cut_pendorite_stairs", () -> new StairBlock(CUT_PENDORITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_STAIRS)));
    public static final Supplier<SlabBlock> CUT_PENDORITE_SLAB = registerBlockItem("cut_pendorite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CUT_COPPER_SLAB)));
    public static final Supplier<Block> CHISELED_PENDORITE = registerBasicBlockWithItem("chiseled_pendorite", BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_COPPER).mapColor(MapColor.METAL));
    public static final Supplier<WaterloggedTransparentBlock> PENDORITE_GRATE = registerBlockItem("pendorite_grate", () -> new WaterloggedTransparentBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE)));
	public static final Supplier<DoorBlock> PENDORITE_DOOR = registerBlockItem("pendorite_door", () -> new DoorBlock(NetherDescentBlockSetTypes.PENDORITE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_DOOR)));
	public static final Supplier<TrapDoorBlock> PENDORITE_TRAPDOOR = registerBlockItem("pendorite_trapdoor", () -> new TrapDoorBlock(NetherDescentBlockSetTypes.PENDORITE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_TRAPDOOR)));
    public static final Supplier<LanternBlock> PENDORITE_LANTERN = registerBlockItem("pendorite_lantern", () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN)));
    public static final Supplier<TorchBlock> PENDORITE_TORCH = registerBlock("pendorite_torch", () -> new NDTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));
    public static final Supplier<WallTorchBlock> PENDORITE_WALL_TORCH = register("pendorite_wall_torch", () -> new NDWallTorchBlock(NetherDescentParticles.PENDORITE_FIRE_FLAME, BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)));
    public static final Supplier<ChainBlock> PENDORITE_CHAIN = registerBlockItem("pendorite_chain", () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));
    public static final Supplier<IronBarsBlock> PENDORITE_BARS = registerBlockItem("pendorite_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS)));
	public static final Supplier<NetherDescentCampfireBlock> PENDORITE_CAMPFIRE = registerBlockItem("pendorite_campfire", () -> new NetherDescentCampfireBlock(false, 2, BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_CAMPFIRE)));
    public static final Supplier<NDRodBlock> PENDORITE_FIRE_ROD = registerBlockItem("pendorite_fire_rod", () -> new NDRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD)));

    public static final Supplier<NDRodBlock> SOUL_FIRE_ROD = registerBlockItem("soul_fire_rod", () -> new NDRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD)));
    public static final Supplier<NDRodBlock> BLAZE_FIRE_ROD = registerBlockItem("blaze_fire_rod", () -> new NDRodBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.END_ROD)));

    public static final Supplier<NetherDescentFeatureDoublePlantBlock> TALL_CRIMSON_FUNGI = registerBlockItem("tall_crimson_fungi", () -> new NetherDescentFeatureDoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS), null));

	public static final Supplier<HornetNestBlock> HORNET_NEST = registerBlock("hornet_nest", () -> new HornetNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEE_NEST)));

    public static final Supplier<Block> BARTERING_TABLE = registerBlockItem("bartering_table", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SMITHING_TABLE)));

    public static Supplier<Block> registerBasicBlockWithItem(String key, BlockBehaviour.Properties properties) {
        return registerCubeAllBlockItem(key, () -> new Block(properties));
    }

    public static <B extends Block> Supplier<B> registerCubeAllBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlockItem(key, blockSupplier);
        cubeAllBlocks.add(block);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlockItem(String key, Supplier<B> blockSupplier) {
        Supplier<B> block = registerBlock(key, blockSupplier);
        Supplier<Item> item = NetherDescentItems.register(key, () -> new BlockItem(block.get(), new Item.Properties()));
        BLOCK_ITEMS.add(item);
        return block;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block) {
        Supplier<B> blockSupplier = register(id, block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block) {
        return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void blocks() {
        NetherDescent.LOGGER.info("Registering Nether Descent Blocks");
    }
}
