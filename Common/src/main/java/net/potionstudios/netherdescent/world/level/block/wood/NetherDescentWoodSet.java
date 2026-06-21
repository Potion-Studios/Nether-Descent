package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.PottedBlock;
import net.potionstudios.netherdescent.world.level.block.wood.sign.NetherDescentCeilingHangingSignBlock;
import net.potionstudios.netherdescent.world.level.block.wood.sign.NetherDescentStandingSignBlock;
import net.potionstudios.netherdescent.world.level.block.wood.sign.NetherDescentWallHangingSignBlock;
import net.potionstudios.netherdescent.world.level.block.wood.sign.NetherDescentWallSignBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * Wood set for Nether Descent
 * @see NetherDescentBlocks
 * @author Joseph T. McQuigg
 */
public class NetherDescentWoodSet {

    private static final ArrayList<NetherDescentWoodSet> woodSets = new ArrayList<>();

    private final String name;
    private final WoodType woodType;
    private final LogStem logstemEnum;
    private final Supplier<RotatedPillarBlock> logstem;
    private final Supplier<RotatedPillarBlock> wood;
    private final Supplier<RotatedPillarBlock> strippedLogStem;
    private final Supplier<RotatedPillarBlock> strippedWood;
    private final Supplier<Block> planks;
    private final Supplier<StairBlock> stairs;
    private final Supplier<SlabBlock> slab;
    private final Supplier<FenceBlock> fence;
    private final Supplier<FenceGateBlock> fenceGate;
    private final Supplier<DoorBlock> door;
    private final Supplier<TrapDoorBlock> trapdoor;
    private final Supplier<PressurePlateBlock> pressurePlate;
    private final Supplier<ButtonBlock> button;
    private final GrowerItem growerItemEnum;
    private final PottedBlock growerItem;
    private final Supplier<Block> bookshelf;
    private final Supplier<CraftingTableBlock> craftingTable;
    private final Supplier<StandingSignBlock> sign;
    private final Supplier<WallSignBlock> wallSign;
    private final Supplier<SignItem> signItem;
    private final Supplier<CeilingHangingSignBlock> hangingSign;
    private final Supplier<WallHangingSignBlock> wallHangingSign;
    private final Supplier<HangingSignItem> hangingSignItem;
    private final TagKey<Block> logBlockTag;
    private final TagKey<Item> logItemTag;
    private final Supplier<? extends Block> growerRequiredBlock;


    private BlockFamily family = null;

    /**
     * Creates a new wood set
     *
     * @param blockSetType The wood type
     * @param mapColor     The map color
     * @param requiredBlock
     */
    public NetherDescentWoodSet(BlockSetType blockSetType, MapColor mapColor, LogStem logstem, GrowerItem growerItem, Supplier<? extends Block> requiredBlock, ResourceKey<ConfiguredFeature<?, ?>> feature, @Nullable ResourceKey<ConfiguredFeature<?, ?>> hangingFeature, boolean hanging) {
        this.growerRequiredBlock = requiredBlock;
        this.woodType = PlatformHandler.PLATFORM_HANDLER.createWoodType(blockSetType.name(), blockSetType);
        this.name = blockSetType.name().replace(NetherDescent.MOD_ID + ":", "");
        this.logstemEnum = logstem;
        this.growerItemEnum = growerItem;
        this.logstem = NetherDescentBlocks.registerBlockItem(name + "_" + logstem.getName(), () -> (RotatedPillarBlock) Blocks.netherStem(mapColor));
        this.wood = NetherDescentBlocks.registerBlockItem(name + "_" + logstem.getWoodName(), () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.NETHER_WOOD)));
        this.strippedLogStem = NetherDescentBlocks.registerBlockItem("stripped_" + name + "_" + logstem.getName(), () -> (RotatedPillarBlock) Blocks.netherStem(mapColor));
        this.strippedWood = NetherDescentBlocks.registerBlockItem("stripped_" + name + "_" + logstem.getWoodName(), () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.NETHER_WOOD)));
        this.planks = NetherDescentBlocks.registerBlockItem(name + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD)));
        this.stairs = NetherDescentBlocks.registerBlockItem(name + "_stairs", () -> new StairBlock(planks.get().defaultBlockState(), BlockBehaviour.Properties.copy(planks.get())));
        this.slab = NetherDescentBlocks.registerBlockItem(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD)));
        this.fence = NetherDescentBlocks.registerBlockItem(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.NETHER_WOOD)));
        this.fenceGate = NetherDescentBlocks.registerBlockItem(name + "_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F), woodType));
        this.door = NetherDescentBlocks.registerBlockItem(name + "_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().pushReaction(PushReaction.DESTROY), woodType.setType()));
        this.trapdoor = NetherDescentBlocks.registerBlockItem(name + "_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never), woodType.setType()));
        this.pressurePlate = NetherDescentBlocks.registerBlockItem(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), woodType.setType()));
        this.button = NetherDescentBlocks.registerBlockItem(name + "_button", () -> Blocks.woodenButton(woodType.setType()));
        Supplier<? extends BushBlock> fungus = NetherDescentBlocks.registerBlockItem(name + "_" + growerItem.getName(), hanging ? () -> new HangingFungusBlock(feature, hangingFeature, requiredBlock.get(), BlockBehaviour.Properties.copy(Blocks.CRIMSON_FUNGUS).mapColor(mapColor)): () -> new FungusBlock(BlockBehaviour.Properties.copy(Blocks.CRIMSON_FUNGUS).mapColor(mapColor), feature, requiredBlock.get()));
        this.growerItem = new PottedBlock(fungus, NetherDescentBlocks.register("potted_" + name + "_" + growerItem.getName(), PlatformHandler.PLATFORM_HANDLER.createPottedBlock(fungus)));
        NetherDescentBlocks.BLOCKS.add(this.growerItem.pottedBlock());
        this.bookshelf = NetherDescentBlocks.registerBlockItem(name + "_bookshelf", () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(1.5F).sound(SoundType.NETHER_WOOD)));
        this.craftingTable = NetherDescentBlocks.registerBlockItem(name + "_crafting_table", () -> new NetherDescentCraftingTable(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.5F).sound(SoundType.NETHER_WOOD)));
        this.sign = NetherDescentBlocks.register(name + "_sign", () ->  new NetherDescentStandingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
        this.wallSign = NetherDescentBlocks.register(name + "_wall_sign", () -> new NetherDescentWallSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(sign.get())));
        this.signItem = NetherDescentItems.register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
        NetherDescentItems.ITEMS.add(signItem);
        this.hangingSign = NetherDescentBlocks.register(name + "_hanging_sign", () -> new NetherDescentCeilingHangingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F)));
        this.wallHangingSign = NetherDescentBlocks.register(name + "_wall_hanging_sign", () -> new NetherDescentWallHangingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(hangingSign.get())));
        this.hangingSignItem = NetherDescentItems.register(name + "_hanging_sign", () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), new Item.Properties().stacksTo(16)));
        NetherDescentItems.ITEMS.add(hangingSignItem);
        this.logBlockTag = TagKey.create(Registries.BLOCK, NetherDescent.id(name + "_" + logstem.getName() + "s"));
        this.logItemTag = TagKey.create(Registries.ITEM, NetherDescent.id(name + "_" + logstem.getName() + "s"));
        woodSets.add(this);
    }

    public NetherDescentWoodSet(BlockSetType blockSetType, MapColor mapColor, Supplier<? extends Block> requiredBlock, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        this(blockSetType, mapColor, LogStem.STEM, GrowerItem.FUNGUS, requiredBlock, feature, null, false);
    }

    public NetherDescentWoodSet(String name, MapColor mapColor, Supplier<? extends Block> requiredBlock, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, requiredBlock, feature);
    }

    public NetherDescentWoodSet(String name, MapColor mapColor, LogStem logStem, GrowerItem growerItem, Supplier<? extends Block> requiredBlock, ResourceKey<ConfiguredFeature<?, ?>> feature) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, logStem, growerItem, requiredBlock, feature, null, false);
    }

    public NetherDescentWoodSet(String name, MapColor mapColor, LogStem logStem, GrowerItem growerItem, Supplier<? extends Block> requiredBlock, ResourceKey<ConfiguredFeature<?, ?>> feature, ResourceKey<ConfiguredFeature<?, ?>> hangingFeature, boolean hanging) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, logStem, growerItem, requiredBlock, feature, hangingFeature, hanging);
    }

    public String name() {
        return name;
    }

    public WoodType woodType() {
        return woodType;
    }

    public RotatedPillarBlock logstem() {
        return logstem.get();
    }

    public RotatedPillarBlock wood() {
        return wood.get();
    }

    public RotatedPillarBlock strippedLogStem() {
        return strippedLogStem.get();
    }

    public RotatedPillarBlock strippedWood() {
        return strippedWood.get();
    }

    public Block planks() {
        return planks.get();
    }

    public StairBlock stairs() {
        return stairs.get();
    }

    public SlabBlock slab() {
        return slab.get();
    }

    public FenceBlock fence() {
        return fence.get();
    }

    public FenceGateBlock fenceGate() {
        return fenceGate.get();
    }

    public DoorBlock door() {
        return door.get();
    }

    public TrapDoorBlock trapdoor() {
        return trapdoor.get();
    }

    public PressurePlateBlock pressurePlate() {
        return pressurePlate.get();
    }

    public ButtonBlock button() {
        return button.get();
    }

    public PottedBlock growerItem() {
        return growerItem;
    }

    public Block bookshelf() {
        return bookshelf.get();
    }

    public CraftingTableBlock craftingTable() {
        return craftingTable.get();
    }

    public StandingSignBlock sign() {
        return sign.get();
    }

    public WallSignBlock wallSign() {
        return wallSign.get();
    }
    public SignItem signItem() {
        return signItem.get();
    }

    public CeilingHangingSignBlock hangingSign() {
        return hangingSign.get();
    }

    public WallHangingSignBlock wallHangingSign() {
        return wallHangingSign.get();
    }

    public HangingSignItem hangingSignItem() {
        return hangingSignItem.get();
    }

    public TagKey<Block> logBlockTag() {
        return logBlockTag;
    }

    public TagKey<Item> logItemTag() {
        return logItemTag;
    }

    public void makeFamily() {
        this.family = BlockFamilies.familyBuilder(planks.get()).button(button.get()).fence(fence.get()).fenceGate(fenceGate.get()).pressurePlate(pressurePlate.get()).sign(sign.get(), wallSign.get()).slab(slab.get()).stairs(stairs.get()).door(door.get()).trapdoor(trapdoor.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
    }

    public BlockFamily family() {
        return family;
    }

    public static ArrayList<NetherDescentWoodSet> woodsets() {
        return woodSets;
    }

    public LogStem logStemEnum() {
        return logstemEnum;
    }

    public GrowerItem growerItemEnum() {
        return growerItemEnum;
    }

    public Supplier<? extends Block> growerRequiredBlock() {
        return growerRequiredBlock;
    }

    public enum LogStem {
        LOG("log", "wood"),
        STEM("stem", "hyphae"),
        PEDU("pedu", "hyphae");

        private final String logName;
        private final String woodName;

        LogStem(String logName, String woodName) {
            this.logName = logName;
            this.woodName = woodName;
        }

        public String getLogName() {
            return logName;
        }
        
        public String getWoodName() {
            return woodName;
        }

        public String getName() {
            return logName;
        }
    }

    public enum GrowerItem {
        SAPLING("sapling"),
        FUNGUS("fungus"),
        WART("wart");

        private final String name;

        GrowerItem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
