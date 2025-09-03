package net.potionstudios.netherdescent.world.level.block.wood;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
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


    private BlockFamily family = null;

    /**
     * Creates a new wood set
     *
     * @param blockSetType       The wood type
     * @param mapColor           The map color
     */
    public NetherDescentWoodSet(BlockSetType blockSetType, MapColor mapColor, LogStem logstem, GrowerItem growerItem) {
        this.woodType = PlatformHandler.PLATFORM_HANDLER.createWoodType(blockSetType.name(), blockSetType);
        this.name = blockSetType.name().replace(NetherDescent.MOD_ID + ":", "");
        this.logstemEnum = logstem;
        this.growerItemEnum = growerItem;
        this.logstem = NetherDescentBlocks.registerBlockItem(name + "_" + logstem.getName(), () -> (RotatedPillarBlock) Blocks.netherStem(mapColor));
        this.wood = NetherDescentBlocks.registerBlockItem(name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.strippedLogStem = NetherDescentBlocks.registerBlockItem("stripped_" + name + "_" + logstem.getName(), () -> (RotatedPillarBlock) Blocks.netherStem(mapColor));
        this.strippedWood = NetherDescentBlocks.registerBlockItem("stripped_" + name + "_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.planks = NetherDescentBlocks.registerBlockItem(name + "_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.stairs = NetherDescentBlocks.registerBlockItem(name + "_stairs", () -> new StairBlock(planks.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(planks.get())));
        this.slab = NetherDescentBlocks.registerBlockItem(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava()));
        this.fence = NetherDescentBlocks.registerBlockItem(name + "_fence", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava().sound(SoundType.WOOD)));
        this.fenceGate = NetherDescentBlocks.registerBlockItem(name + "_fence_gate", () -> new FenceGateBlock(woodType, BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).ignitedByLava()));
        this.door = NetherDescentBlocks.registerBlockItem(name + "_door", () -> new DoorBlock(woodType.setType(), BlockBehaviour.Properties.of().mapColor(planks.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY)));
        this.trapdoor = NetherDescentBlocks.registerBlockItem(name + "_trapdoor", () -> new TrapDoorBlock(woodType.setType(), BlockBehaviour.Properties.of().mapColor(mapColor).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(Blocks::never).ignitedByLava()));
        this.pressurePlate = NetherDescentBlocks.registerBlockItem(name + "_pressure_plate", () -> new PressurePlateBlock(woodType.setType(), BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY)));
        this.button = NetherDescentBlocks.registerBlockItem(name + "_button", () -> (ButtonBlock) Blocks.woodenButton(woodType.setType()));
        Supplier<FungusBlock> fungus = NetherDescentBlocks.registerBlockItem(name + "_" + growerItem.getName(), () -> new FungusBlock(null, null, BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_FUNGUS).mapColor(mapColor)));
        this.growerItem = new PottedBlock(fungus, NetherDescentBlocks.register("potted_" + name + "_" + growerItem.getName(), PlatformHandler.PLATFORM_HANDLER.createPottedBlock(fungus)));
        NetherDescentBlocks.BLOCKS.add(this.growerItem.pottedBlock());
        this.bookshelf = NetherDescentBlocks.registerBlockItem(name + "_bookshelf", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BOOKSHELF).mapColor(mapColor)));
        this.craftingTable = NetherDescentBlocks.registerBlockItem(name + "_crafting_table", () -> new NetherDescentCraftingTable(BlockBehaviour.Properties.ofFullCopy(Blocks.CRAFTING_TABLE).mapColor(mapColor)));
        this.sign = NetherDescentBlocks.register(name + "_sign", () ->  new NetherDescentStandingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
        this.wallSign = NetherDescentBlocks.register(name + "_wall_sign", () -> new NetherDescentWallSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(sign.get()).ignitedByLava()));
        this.signItem = NetherDescentItems.register(name + "_sign", () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
        NetherDescentItems.ITEMS.add(signItem);
        this.hangingSign = NetherDescentBlocks.register(name + "_hanging_sign", () -> new NetherDescentCeilingHangingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(this.logstem.get().defaultMapColor()).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).ignitedByLava()));
        this.wallHangingSign = NetherDescentBlocks.register(name + "_wall_hanging_sign", () -> new NetherDescentWallHangingSignBlock(woodType, BlockBehaviour.Properties.of().mapColor(mapColor).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(1.0F).dropsLike(hangingSign.get()).ignitedByLava()));
        this.hangingSignItem = NetherDescentItems.register(name + "_hanging_sign", () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), new Item.Properties().stacksTo(16)));
        NetherDescentItems.ITEMS.add(hangingSignItem);
        this.logBlockTag = TagKey.create(Registries.BLOCK, NetherDescent.id(name + "_" + logstem.getName() + "s"));
        this.logItemTag = TagKey.create(Registries.ITEM, NetherDescent.id(name + "_" + logstem.getName() + "s"));
        woodSets.add(this);
    }

    public NetherDescentWoodSet(BlockSetType blockSetType, MapColor mapColor) {
        this(blockSetType, mapColor, LogStem.STEM, GrowerItem.FUNGUS);
    }

    public NetherDescentWoodSet(String name, MapColor mapColor) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor);
    }

    public NetherDescentWoodSet(String name, MapColor mapColor, LogStem logStem, GrowerItem growerItem) {
        this(BlockSetType.register(new BlockSetType(name)), mapColor, logStem, growerItem);
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

    public enum LogStem {
        LOG("log"),
        STEM("stem"),
        PEDU("pedu");

        private final String name;

        LogStem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum GrowerItem {
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
