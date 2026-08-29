package net.potionstudios.netherdescent.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.structure.pieces.NetherDescentStructurePieceType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlueNetherFortressPieces {
	private static final int MAX_DEPTH = 30;
	private static final int LOWEST_Y_POSITION = 10;
	public static final int MAGIC_START_Y = 64;
	static final BlueNetherFortressPieces.PieceWeight[] BRIDGE_PIECE_WEIGHTS = new BlueNetherFortressPieces.PieceWeight[]{
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.BridgeStraight.class, 30, 0, true),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.BridgeCrossing.class, 10, 4),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.RoomCrossing.class, 10, 4),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.StairsRoom.class, 10, 3),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.MonsterThrone.class, 5, 2),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleEntrance.class, 5, 1)
	};
	static final BlueNetherFortressPieces.PieceWeight[] CASTLE_PIECE_WEIGHTS = new BlueNetherFortressPieces.PieceWeight[]{
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleSmallCorridorPiece.class, 25, 0, true),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece.class, 15, 5),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece.class, 5, 10),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.class, 5, 10),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleCorridorStairsPiece.class, 10, 3, true),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleCorridorTBalconyPiece.class, 7, 2),
			new BlueNetherFortressPieces.PieceWeight(BlueNetherFortressPieces.CastleStalkRoom.class, 5, 2)
	};

	static BlueNetherFortressPieces.NetherBridgePiece findAndCreateBridgePieceFactory(
			BlueNetherFortressPieces.PieceWeight weight, StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
	) {
		Class<? extends BlueNetherFortressPieces.NetherBridgePiece> class_ = weight.pieceClass;
		BlueNetherFortressPieces.NetherBridgePiece netherBridgePiece = null;
		if (class_ == BlueNetherFortressPieces.BridgeStraight.class) {
			netherBridgePiece = BlueNetherFortressPieces.BridgeStraight.createPiece(pieces, random, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.BridgeCrossing.class) {
			netherBridgePiece = BlueNetherFortressPieces.BridgeCrossing.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.RoomCrossing.class) {
			netherBridgePiece = BlueNetherFortressPieces.RoomCrossing.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.StairsRoom.class) {
			netherBridgePiece = BlueNetherFortressPieces.StairsRoom.createPiece(pieces, x, y, z, genDepth, orientation);
		} else if (class_ == BlueNetherFortressPieces.MonsterThrone.class) {
			netherBridgePiece = BlueNetherFortressPieces.MonsterThrone.createPiece(pieces, x, y, z, genDepth, orientation);
		} else if (class_ == BlueNetherFortressPieces.CastleEntrance.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleEntrance.createPiece(pieces, random, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleSmallCorridorPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleSmallCorridorPiece.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece.createPiece(pieces, random, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece.createPiece(pieces, random, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleCorridorStairsPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleCorridorStairsPiece.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleCorridorTBalconyPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleCorridorTBalconyPiece.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece.createPiece(pieces, x, y, z, orientation, genDepth);
		} else if (class_ == BlueNetherFortressPieces.CastleStalkRoom.class) {
			netherBridgePiece = BlueNetherFortressPieces.CastleStalkRoom.createPiece(pieces, x, y, z, orientation, genDepth);
		}

		return netherBridgePiece;
	}

	public static class BridgeCrossing extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 19;
		private static final int HEIGHT = 10;
		private static final int DEPTH = 19;

		public BridgeCrossing(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		protected BridgeCrossing(int x, int z, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING.get(), 0, StructurePiece.makeBoundingBox(x, 64, z, orientation, 19, 10, 19));
			this.setOrientation(orientation);
		}

		protected BridgeCrossing(StructurePieceType type, CompoundTag tag) {
			super(type, tag);
		}

		public BridgeCrossing(CompoundTag tag) {
			this(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_CROSSING.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 8, 3, false);
			this.generateChildLeft((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 3, 8, false);
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 3, 8, false);
		}

		public static BlueNetherFortressPieces.BridgeCrossing createPiece(StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -8, -3, 0, 19, 10, 19, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.BridgeCrossing(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 7, 3, 0, 11, 4, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 3, 7, 18, 4, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 0, 10, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 8, 18, 7, 10, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 7, 5, 0, 7, 5, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 7, 5, 11, 7, 5, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 0, 11, 5, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 11, 11, 5, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 7, 7, 5, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 7, 18, 5, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 11, 7, 5, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 11, 18, 5, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 7, 2, 0, 11, 2, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 7, 2, 13, 11, 2, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 7, 0, 0, 11, 1, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 7, 0, 15, 11, 1, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 7; i <= 11; i++) {
				for (int j = 0; j <= 2; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, 18 - j, box);
				}
			}

			this.generateBox(level, box, 0, 2, 7, 5, 2, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 13, 2, 7, 18, 2, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 0, 7, 3, 1, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 15, 0, 7, 18, 1, 11, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 2; i++) {
				for (int j = 7; j <= 11; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 18 - i, -1, j, box);
				}
			}
		}
	}

	public static class BridgeEndFiller extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 10;
		private static final int DEPTH = 8;
		private final int selfSeed;

		public BridgeEndFiller(int genDepth, RandomSource random, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER.get(), genDepth, box);
			this.setOrientation(orientation);
			this.selfSeed = random.nextInt();
		}

		public BridgeEndFiller(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_END_FILLER.get(), tag);
			this.selfSeed = tag.getIntOr("Seed", 0);
		}

		public static BlueNetherFortressPieces.BridgeEndFiller createPiece(
				StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, -3, 0, 5, 10, 8, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.BridgeEndFiller(genDepth, random, boundingBox, orientation)
					: null;
		}

		@Override
		protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putInt("Seed", this.selfSeed);
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			RandomSource randomSource = RandomSource.create(this.selfSeed);

			for (int i = 0; i <= 4; i++) {
				for (int j = 3; j <= 4; j++) {
					int k = randomSource.nextInt(8);
					this.generateBox(level, box, i, j, 0, i, j, k, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				}
			}

			int i = randomSource.nextInt(8);
			this.generateBox(level, box, 0, 5, 0, 0, 5, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			i = randomSource.nextInt(8);
			this.generateBox(level, box, 4, 5, 0, 4, 5, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int ix = 0; ix <= 4; ix++) {
				int j = randomSource.nextInt(5);
				this.generateBox(level, box, ix, 2, 0, ix, 2, j, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			}

			for (int ix = 0; ix <= 4; ix++) {
				for (int j = 0; j <= 1; j++) {
					int k = randomSource.nextInt(3);
					this.generateBox(level, box, ix, j, 0, ix, j, k, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				}
			}
		}
	}

	public static class BridgeStraight extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 10;
		private static final int DEPTH = 19;

		public BridgeStraight(int genDepth, RandomSource random, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public BridgeStraight(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_BRIDGE_STRAIGHT.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 1, 3, false);
		}

		public static BlueNetherFortressPieces.BridgeStraight createPiece(
				StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, -3, 0, 5, 10, 19, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.BridgeStraight(genDepth, random, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 3, 0, 4, 4, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 5, 0, 3, 7, 18, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 0, 0, 5, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 5, 0, 4, 5, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 4, 2, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 13, 4, 2, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 0, 0, 4, 1, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 0, 15, 4, 1, 18, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 2; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, 18 - j, box);
				}
			}

			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			BlockState blockState2 = blockState.setValue(FenceBlock.EAST, true);
			BlockState blockState3 = blockState.setValue(FenceBlock.WEST, true);
			this.generateBox(level, box, 0, 1, 1, 0, 4, 1, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 3, 4, 0, 4, 4, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 3, 14, 0, 4, 14, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 1, 17, 0, 4, 17, blockState2, blockState2, false);
			this.generateBox(level, box, 4, 1, 1, 4, 4, 1, blockState3, blockState3, false);
			this.generateBox(level, box, 4, 3, 4, 4, 4, 4, blockState3, blockState3, false);
			this.generateBox(level, box, 4, 3, 14, 4, 4, 14, blockState3, blockState3, false);
			this.generateBox(level, box, 4, 1, 17, 4, 4, 17, blockState3, blockState3, false);
		}
	}

	public static class CastleCorridorStairsPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 14;
		private static final int DEPTH = 10;

		public CastleCorridorStairsPiece(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleCorridorStairsPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 1, 0, true);
		}

		public static BlueNetherFortressPieces.CastleCorridorStairsPiece createPiece(
				StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, -7, 0, 5, 14, 10, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleCorridorStairsPiece(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICKS.getStairs().defaultBlockState().setValue(StairBlock.FACING, Direction.SOUTH);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);

			for (int i = 0; i <= 9; i++) {
				int j = Math.max(1, 7 - i);
				int k = Math.min(Math.max(j + 5, 14 - i), 13);
				int l = i;
				this.generateBox(level, box, 0, 0, i, 4, j, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				this.generateBox(level, box, 1, j + 1, i, 3, k - 1, i, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
				if (i <= 6) {
					this.placeBlock(level, blockState, 1, j + 1, i, box);
					this.placeBlock(level, blockState, 2, j + 1, i, box);
					this.placeBlock(level, blockState, 3, j + 1, i, box);
				}

				this.generateBox(level, box, 0, k, i, 4, k, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				this.generateBox(level, box, 0, j + 1, i, 0, k - 1, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				this.generateBox(level, box, 4, j + 1, i, 4, k - 1, i, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				if ((i & 1) == 0) {
					this.generateBox(level, box, 0, j + 2, i, 0, j + 3, i, blockState2, blockState2, false);
					this.generateBox(level, box, 4, j + 2, i, 4, j + 3, i, blockState2, blockState2, false);
				}

				for (int m = 0; m <= 4; m++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), m, -1, l, box);
				}
			}
		}
	}

	public static class CastleCorridorTBalconyPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 9;
		private static final int HEIGHT = 7;
		private static final int DEPTH = 9;

		public CastleCorridorTBalconyPiece(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleCorridorTBalconyPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			int i = 1;
			Direction direction = this.getOrientation();
			if (direction == Direction.WEST || direction == Direction.NORTH) {
				i = 5;
			}

			this.generateChildLeft((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, i, random.nextInt(8) > 0);
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, i, random.nextInt(8) > 0);
		}

		public static BlueNetherFortressPieces.CastleCorridorTBalconyPiece createPiece(
				StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -3, 0, 0, 9, 7, 9, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleCorridorTBalconyPiece(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			this.generateBox(level, box, 0, 0, 0, 8, 1, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 8, 5, 8, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 6, 0, 8, 6, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 2, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 2, 0, 8, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 0, 1, 4, 0, blockState2, blockState2, false);
			this.generateBox(level, box, 7, 3, 0, 7, 4, 0, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 2, 4, 8, 2, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 1, 4, 2, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 6, 1, 4, 7, 2, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 8, 7, 3, 8, blockState2, blockState2, false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true).setValue(FenceBlock.SOUTH, true), 0, 3, 8, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.SOUTH, true), 8, 3, 8, box);
			this.generateBox(level, box, 0, 3, 6, 0, 3, 7, blockState, blockState, false);
			this.generateBox(level, box, 8, 3, 6, 8, 3, 7, blockState, blockState, false);
			this.generateBox(level, box, 0, 3, 4, 0, 5, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 3, 4, 8, 5, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 5, 2, 5, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 3, 5, 7, 5, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 4, 5, 1, 5, 5, blockState2, blockState2, false);
			this.generateBox(level, box, 7, 4, 5, 7, 5, 5, blockState2, blockState2, false);

			for (int i = 0; i <= 5; i++) {
				for (int j = 0; j <= 8; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), j, -1, i, box);
				}
			}
		}
	}

	public static class CastleEntrance extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 13;
		private static final int HEIGHT = 14;
		private static final int DEPTH = 13;

		public CastleEntrance(int genDepth, RandomSource random, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleEntrance(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_ENTRANCE.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 5, 3, true);
		}

		public static BlueNetherFortressPieces.CastleEntrance createPiece(
				StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleEntrance(genDepth, random, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 3, 0, 12, 4, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 0, 1, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 0, 12, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 11, 4, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 11, 10, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 9, 11, 7, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 0, 4, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 0, 10, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 9, 0, 7, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 11, 2, 10, 12, 10, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 8, 0, 7, 8, 0, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);

			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(level, box, i, 10, 0, i, 11, 0, blockState, blockState, false);
				this.generateBox(level, box, i, 10, 12, i, 11, 12, blockState, blockState, false);
				this.generateBox(level, box, 0, 10, i, 0, 11, i, blockState2, blockState2, false);
				this.generateBox(level, box, 12, 10, i, 12, 11, i, blockState2, blockState2, false);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, 13, 0, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, 13, 12, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 0, 13, i, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 12, 13, i, box);
				if (i != 11) {
					this.placeBlock(level, blockState, i + 1, 13, 0, box);
					this.placeBlock(level, blockState, i + 1, 13, 12, box);
					this.placeBlock(level, blockState2, 0, 13, i + 1, box);
					this.placeBlock(level, blockState2, 12, 13, i + 1, box);
				}
			}

			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.EAST, true), 0, 13, 0, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.EAST, true), 0, 13, 12, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.WEST, true), 12, 13, 12, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.WEST, true), 12, 13, 0, box);

			for (int ix = 3; ix <= 9; ix += 2) {
				this.generateBox(level, box, 1, 7, ix, 1, 8, ix, blockState2.setValue(FenceBlock.WEST, true), blockState2.setValue(FenceBlock.WEST, true), false);
				this.generateBox(level, box, 11, 7, ix, 11, 8, ix, blockState2.setValue(FenceBlock.EAST, true), blockState2.setValue(FenceBlock.EAST, true), false);
			}

			this.generateBox(level, box, 4, 2, 0, 8, 2, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 4, 12, 2, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 0, 0, 8, 1, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 0, 9, 8, 1, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 0, 4, 3, 1, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 9, 0, 4, 12, 1, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int ix = 4; ix <= 8; ix++) {
				for (int j = 0; j <= 2; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), ix, -1, j, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), ix, -1, 12 - j, box);
				}
			}

			for (int ix = 0; ix <= 2; ix++) {
				for (int j = 4; j <= 8; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), ix, -1, j, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 12 - ix, -1, j, box);
				}
			}

			this.generateBox(level, box, 5, 5, 5, 7, 5, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 1, 6, 6, 4, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 6, 0, 6, box);
			this.placeBlock(level, Blocks.LAVA.defaultBlockState(), 6, 5, 6, box);
			BlockPos blockPos = this.getWorldPos(6, 5, 6);
			if (box.isInside(blockPos)) {
				level.scheduleTick(blockPos, Fluids.LAVA, 0);
			}
		}
	}

	public static class CastleSmallCorridorCrossingPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 7;
		private static final int DEPTH = 5;

		public CastleSmallCorridorCrossingPiece(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleSmallCorridorCrossingPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 1, 0, true);
			this.generateChildLeft((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 1, true);
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 1, true);
		}

		public static BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece createPiece(
				StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 4, 1, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 0, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 2, 0, 4, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 4, 0, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 2, 4, 4, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 6, 0, 4, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 4; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class CastleSmallCorridorLeftTurnPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 7;
		private static final int DEPTH = 5;
		private boolean isNeedingChest;

		public CastleSmallCorridorLeftTurnPiece(int genDepth, RandomSource random, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), genDepth, box);
			this.setOrientation(orientation);
			this.isNeedingChest = random.nextInt(3) == 0;
		}

		public CastleSmallCorridorLeftTurnPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN.get(), tag);
			this.isNeedingChest = tag.getBooleanOr("Chest", false);
		}

		@Override
		protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildLeft((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 1, true);
		}

		public static BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece createPiece(
				StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece(genDepth, random, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 4, 1, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.generateBox(level, box, 4, 2, 0, 4, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 3, 1, 4, 4, 1, blockState2, blockState2, false);
			this.generateBox(level, box, 4, 3, 3, 4, 4, 3, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 2, 0, 0, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 4, 3, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 4, 1, 4, 4, blockState, blockState, false);
			this.generateBox(level, box, 3, 3, 4, 3, 4, 4, blockState, blockState, false);
			if (this.isNeedingChest && box.isInside(this.getWorldPos(3, 2, 3))) {
				this.isNeedingChest = false;
				this.createChest(level, box, random, 3, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
			}

			this.generateBox(level, box, 0, 6, 0, 4, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 4; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class CastleSmallCorridorPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 7;
		private static final int DEPTH = 5;

		public CastleSmallCorridorPiece(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleSmallCorridorPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 1, 0, true);
		}

		public static BlueNetherFortressPieces.CastleSmallCorridorPiece createPiece(
				StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleSmallCorridorPiece(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 4, 1, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.generateBox(level, box, 0, 2, 0, 0, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 2, 0, 4, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 3, 1, 0, 4, 1, blockState, blockState, false);
			this.generateBox(level, box, 0, 3, 3, 0, 4, 3, blockState, blockState, false);
			this.generateBox(level, box, 4, 3, 1, 4, 4, 1, blockState, blockState, false);
			this.generateBox(level, box, 4, 3, 3, 4, 4, 3, blockState, blockState, false);
			this.generateBox(level, box, 0, 6, 0, 4, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 4; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class CastleSmallCorridorRightTurnPiece extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 5;
		private static final int HEIGHT = 7;
		private static final int DEPTH = 5;
		private boolean isNeedingChest;

		public CastleSmallCorridorRightTurnPiece(int genDepth, RandomSource random, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), genDepth, box);
			this.setOrientation(orientation);
			this.isNeedingChest = random.nextInt(3) == 0;
		}

		public CastleSmallCorridorRightTurnPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN.get(), tag);
			this.isNeedingChest = tag.getBooleanOr("Chest", false);
		}

		@Override
		protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putBoolean("Chest", this.isNeedingChest);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 1, true);
		}

		public static BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece createPiece(
				StructurePieceAccessor pieces, RandomSource random, int x, int y, int z, Direction orientation, int genDepth
		) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -1, 0, 0, 5, 7, 5, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece(genDepth, random, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 4, 1, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 4, 5, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.generateBox(level, box, 0, 2, 0, 0, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 3, 1, 0, 4, 1, blockState2, blockState2, false);
			this.generateBox(level, box, 0, 3, 3, 0, 4, 3, blockState2, blockState2, false);
			this.generateBox(level, box, 4, 2, 0, 4, 5, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 2, 4, 4, 5, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 4, 1, 4, 4, blockState, blockState, false);
			this.generateBox(level, box, 3, 3, 4, 3, 4, 4, blockState, blockState, false);
			if (this.isNeedingChest && box.isInside(this.getWorldPos(1, 2, 3))) {
				this.isNeedingChest = false;
				this.createChest(level, box, random, 1, 2, 3, BuiltInLootTables.NETHER_BRIDGE);
			}

			this.generateBox(level, box, 0, 6, 0, 4, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j <= 4; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class CastleStalkRoom extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 13;
		private static final int HEIGHT = 14;
		private static final int DEPTH = 13;

		public CastleStalkRoom(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public CastleStalkRoom(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_CASTLE_STALK_ROOM.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 5, 3, true);
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 5, 11, true);
		}

		public static BlueNetherFortressPieces.CastleStalkRoom createPiece(StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -5, -3, 0, 13, 14, 13, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.CastleStalkRoom(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 3, 0, 12, 4, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 0, 12, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 0, 1, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 11, 5, 0, 12, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 11, 4, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 11, 10, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 9, 11, 7, 12, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 0, 4, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 0, 10, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 9, 0, 7, 12, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 11, 2, 10, 12, 10, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			BlockState blockState3 = blockState2.setValue(FenceBlock.WEST, true);
			BlockState blockState4 = blockState2.setValue(FenceBlock.EAST, true);

			for (int i = 1; i <= 11; i += 2) {
				this.generateBox(level, box, i, 10, 0, i, 11, 0, blockState, blockState, false);
				this.generateBox(level, box, i, 10, 12, i, 11, 12, blockState, blockState, false);
				this.generateBox(level, box, 0, 10, i, 0, 11, i, blockState2, blockState2, false);
				this.generateBox(level, box, 12, 10, i, 12, 11, i, blockState2, blockState2, false);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, 13, 0, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, 13, 12, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 0, 13, i, box);
				this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 12, 13, i, box);
				if (i != 11) {
					this.placeBlock(level, blockState, i + 1, 13, 0, box);
					this.placeBlock(level, blockState, i + 1, 13, 12, box);
					this.placeBlock(level, blockState2, 0, 13, i + 1, box);
					this.placeBlock(level, blockState2, 12, 13, i + 1, box);
				}
			}

			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.EAST, true), 0, 13, 0, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.EAST, true), 0, 13, 12, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.SOUTH, true).setValue(FenceBlock.WEST, true), 12, 13, 12, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.WEST, true), 12, 13, 0, box);

			for (int ix = 3; ix <= 9; ix += 2) {
				this.generateBox(level, box, 1, 7, ix, 1, 8, ix, blockState3, blockState3, false);
				this.generateBox(level, box, 11, 7, ix, 11, 8, ix, blockState4, blockState4, false);
			}

			BlockState blockState5 = NetherDescentBlocks.BLUE_NETHER_BRICKS.getStairs().defaultBlockState().setValue(StairBlock.FACING, Direction.NORTH);

			for (int j = 0; j <= 6; j++) {
				int k = j + 4;

				for (int l = 5; l <= 7; l++) {
					this.placeBlock(level, blockState5, l, 5 + j, k, box);
				}

				if (k >= 5 && k <= 8) {
					this.generateBox(level, box, 5, 5, k, 7, j + 4, k, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				} else if (k >= 9 && k <= 10) {
					this.generateBox(level, box, 5, 8, k, 7, j + 4, k, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
				}

				if (j >= 1) {
					this.generateBox(level, box, 5, 6 + j, k, 7, 9 + j, k, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
				}
			}

			for (int j = 5; j <= 7; j++) {
				this.placeBlock(level, blockState5, j, 12, 11, box);
			}

			this.generateBox(level, box, 5, 6, 7, 5, 7, 7, blockState4, blockState4, false);
			this.generateBox(level, box, 7, 6, 7, 7, 7, 7, blockState3, blockState3, false);
			this.generateBox(level, box, 5, 13, 12, 7, 13, 12, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 2, 3, 5, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 9, 3, 5, 10, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 4, 2, 5, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 9, 5, 2, 10, 5, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 9, 5, 9, 10, 5, 10, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 10, 5, 4, 10, 5, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			BlockState blockState6 = blockState5.setValue(StairBlock.FACING, Direction.EAST);
			BlockState blockState7 = blockState5.setValue(StairBlock.FACING, Direction.WEST);
			this.placeBlock(level, blockState7, 4, 5, 2, box);
			this.placeBlock(level, blockState7, 4, 5, 3, box);
			this.placeBlock(level, blockState7, 4, 5, 9, box);
			this.placeBlock(level, blockState7, 4, 5, 10, box);
			this.placeBlock(level, blockState6, 8, 5, 2, box);
			this.placeBlock(level, blockState6, 8, 5, 3, box);
			this.placeBlock(level, blockState6, 8, 5, 9, box);
			this.placeBlock(level, blockState6, 8, 5, 10, box);
			this.generateBox(level, box, 3, 4, 4, 4, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
			this.generateBox(level, box, 8, 4, 4, 9, 4, 8, Blocks.SOUL_SAND.defaultBlockState(), Blocks.SOUL_SAND.defaultBlockState(), false);
			this.generateBox(level, box, 3, 5, 4, 4, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
			this.generateBox(level, box, 8, 5, 4, 9, 5, 8, Blocks.NETHER_WART.defaultBlockState(), Blocks.NETHER_WART.defaultBlockState(), false);
			this.generateBox(level, box, 4, 2, 0, 8, 2, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 4, 12, 2, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 0, 0, 8, 1, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 4, 0, 9, 8, 1, 12, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 0, 4, 3, 1, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 9, 0, 4, 12, 1, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);

			for (int l = 4; l <= 8; l++) {
				for (int m = 0; m <= 2; m++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), l, -1, m, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), l, -1, 12 - m, box);
				}
			}

			for (int l = 0; l <= 2; l++) {
				for (int m = 4; m <= 8; m++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), l, -1, m, box);
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 12 - l, -1, m, box);
				}
			}
		}
	}

	public static class MonsterThrone extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 7;
		private static final int HEIGHT = 8;
		private static final int DEPTH = 9;
		private boolean hasPlacedSpawner;

		public MonsterThrone(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_MONSTER_THRONE.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public MonsterThrone(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_MONSTER_THRONE.get(), tag);
			this.hasPlacedSpawner = tag.getBooleanOr("Mob", false);
		}

		@Override
		protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag tag) {
			super.addAdditionalSaveData(context, tag);
			tag.putBoolean("Mob", this.hasPlacedSpawner);
		}

		public static BlueNetherFortressPieces.MonsterThrone createPiece(StructurePieceAccessor pieces, int x, int y, int z, int genDepth, Direction orientation) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -2, 0, 0, 7, 8, 9, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.MonsterThrone(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 2, 0, 6, 7, 7, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 1, 0, 0, 5, 1, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 2, 1, 5, 2, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 3, 2, 5, 3, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 4, 3, 5, 4, 7, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 2, 0, 1, 4, 2, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 2, 0, 5, 4, 2, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 5, 2, 1, 5, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 5, 2, 5, 5, 3, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 3, 0, 5, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 5, 3, 6, 5, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 5, 8, 5, 5, 8, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true), 1, 6, 3, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true), 5, 6, 3, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true).setValue(FenceBlock.NORTH, true), 0, 6, 3, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.NORTH, true), 6, 6, 3, box);
			this.generateBox(level, box, 0, 6, 4, 0, 6, 7, blockState2, blockState2, false);
			this.generateBox(level, box, 6, 6, 4, 6, 6, 7, blockState2, blockState2, false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true).setValue(FenceBlock.SOUTH, true), 0, 6, 8, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.SOUTH, true), 6, 6, 8, box);
			this.generateBox(level, box, 1, 6, 8, 5, 6, 8, blockState, blockState, false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true), 1, 7, 8, box);
			this.generateBox(level, box, 2, 7, 8, 4, 7, 8, blockState, blockState, false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true), 5, 7, 8, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.EAST, true), 2, 8, 8, box);
			this.placeBlock(level, blockState, 3, 8, 8, box);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true), 4, 8, 8, box);
			if (!this.hasPlacedSpawner) {
				BlockPos blockPos = this.getWorldPos(3, 5, 5);
				if (box.isInside(blockPos)) {
					this.hasPlacedSpawner = true;
					level.setBlock(blockPos, Blocks.SPAWNER.defaultBlockState(), 2);
					if (level.getBlockEntity(blockPos) instanceof SpawnerBlockEntity spawnerBlockEntity) {
						spawnerBlockEntity.setEntityId(NetherDescentEntityType.SOUL_BLAZE.get(), random);
					}
				}
			}

			for (int i = 0; i <= 6; i++) {
				for (int j = 0; j <= 6; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	abstract static class NetherBridgePiece extends StructurePiece {
		protected NetherBridgePiece(StructurePieceType type, int genDepth, BoundingBox boundingBox) {
			super(type, genDepth, boundingBox);
		}

		public NetherBridgePiece(StructurePieceType type, CompoundTag tag) {
			super(type, tag);
		}

		@Override
		protected void addAdditionalSaveData(@NotNull StructurePieceSerializationContext context, @NotNull CompoundTag tag) {
		}

		private int updatePieceWeight(List<BlueNetherFortressPieces.PieceWeight> weights) {
			boolean bl = false;
			int i = 0;

			for (BlueNetherFortressPieces.PieceWeight pieceWeight : weights) {
				if (pieceWeight.maxPlaceCount > 0 && pieceWeight.placeCount < pieceWeight.maxPlaceCount) {
					bl = true;
				}

				i += pieceWeight.weight;
			}

			return bl ? i : -1;
		}

		private BlueNetherFortressPieces.NetherBridgePiece generatePiece(
				BlueNetherFortressPieces.StartPiece startPiece,
				List<BlueNetherFortressPieces.PieceWeight> weights,
				StructurePieceAccessor pieces,
				RandomSource random,
				int x,
				int y,
				int z,
				Direction orientation,
				int genDepth
		) {
			int i = this.updatePieceWeight(weights);
			boolean bl = i > 0 && genDepth <= 30;
			int j = 0;

			while (j < 5 && bl) {
				j++;
				int k = random.nextInt(i);

				for (BlueNetherFortressPieces.PieceWeight pieceWeight : weights) {
					k -= pieceWeight.weight;
					if (k < 0) {
						if (!pieceWeight.doPlace(genDepth) || pieceWeight == startPiece.previousPiece && !pieceWeight.allowInRow) {
							break;
						}

						BlueNetherFortressPieces.NetherBridgePiece netherBridgePiece = BlueNetherFortressPieces.findAndCreateBridgePieceFactory(
								pieceWeight, pieces, random, x, y, z, orientation, genDepth
						);
						if (netherBridgePiece != null) {
							pieceWeight.placeCount++;
							startPiece.previousPiece = pieceWeight;
							if (!pieceWeight.isValid()) {
								weights.remove(pieceWeight);
							}

							return netherBridgePiece;
						}
					}
				}
			}

			return BlueNetherFortressPieces.BridgeEndFiller.createPiece(pieces, random, x, y, z, orientation, genDepth);
		}

		private StructurePiece generateAndAddPiece(
				BlueNetherFortressPieces.StartPiece startPiece,
				StructurePieceAccessor pieces,
				RandomSource random,
				int x,
				int y,
				int z,
				@Nullable Direction orientation,
				int genDepth,
				boolean castlePiece
		) {
			if (Math.abs(x - startPiece.getBoundingBox().minX()) <= 112 && Math.abs(z - startPiece.getBoundingBox().minZ()) <= 112) {
				List<BlueNetherFortressPieces.PieceWeight> list = startPiece.availableBridgePieces;
				if (castlePiece) {
					list = startPiece.availableCastlePieces;
				}

				StructurePiece structurePiece = this.generatePiece(startPiece, list, pieces, random, x, y, z, orientation, genDepth + 1);
				if (structurePiece != null) {
					pieces.addPiece(structurePiece);
					startPiece.pendingChildren.add(structurePiece);
				}

				return structurePiece;
			} else {
				return BlueNetherFortressPieces.BridgeEndFiller.createPiece(pieces, random, x, y, z, orientation, genDepth);
			}
		}

		@Nullable
		protected StructurePiece generateChildForward(
				BlueNetherFortressPieces.StartPiece startPiece, StructurePieceAccessor pieces, RandomSource random, int offsetX, int offsetY, boolean castlePiece
		) {
			Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
					case NORTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() - 1,
								direction,
								this.getGenDepth(),
								castlePiece
						);
					case SOUTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.maxZ() + 1,
								direction,
								this.getGenDepth(),
								castlePiece
						);
					case WEST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() - 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								direction,
								this.getGenDepth(),
								castlePiece
						);
					case EAST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.maxX() + 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								direction,
								this.getGenDepth(),
								castlePiece
						);
				}
			}

			return null;
		}

		@Nullable
		protected StructurePiece generateChildLeft(
				BlueNetherFortressPieces.StartPiece startPiece, StructurePieceAccessor pieces, RandomSource random, int offsetY, int offsetX, boolean castlePiece
		) {
			Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
					case NORTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() - 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								Direction.WEST,
								this.getGenDepth(),
								castlePiece
						);
					case SOUTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() - 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								Direction.WEST,
								this.getGenDepth(),
								castlePiece
						);
					case WEST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() - 1,
								Direction.NORTH,
								this.getGenDepth(),
								castlePiece
						);
					case EAST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() - 1,
								Direction.NORTH,
								this.getGenDepth(),
								castlePiece
						);
				}
			}

			return null;
		}

		@Nullable
		protected StructurePiece generateChildRight(
				BlueNetherFortressPieces.StartPiece startPiece, StructurePieceAccessor pieces, RandomSource random, int offsetY, int offsetX, boolean castlePiece
		) {
			Direction direction = this.getOrientation();
			if (direction != null) {
				switch (direction) {
					case NORTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.maxX() + 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								Direction.EAST,
								this.getGenDepth(),
								castlePiece
						);
					case SOUTH:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.maxX() + 1,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.minZ() + offsetX,
								Direction.EAST,
								this.getGenDepth(),
								castlePiece
						);
					case WEST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.maxZ() + 1,
								Direction.SOUTH,
								this.getGenDepth(),
								castlePiece
						);
					case EAST:
						return this.generateAndAddPiece(
								startPiece,
								pieces,
								random,
								this.boundingBox.minX() + offsetX,
								this.boundingBox.minY() + offsetY,
								this.boundingBox.maxZ() + 1,
								Direction.SOUTH,
								this.getGenDepth(),
								castlePiece
						);
				}
			}

			return null;
		}

		protected static boolean isOkBox(BoundingBox box) {
			return box != null && box.minY() > 10;
		}
	}

	static class PieceWeight {
		public final Class<? extends BlueNetherFortressPieces.NetherBridgePiece> pieceClass;
		public final int weight;
		public int placeCount;
		public final int maxPlaceCount;
		public final boolean allowInRow;

		public PieceWeight(Class<? extends BlueNetherFortressPieces.NetherBridgePiece> pieceClass, int weight, int maxPlaceCount, boolean allowInRow) {
			this.pieceClass = pieceClass;
			this.weight = weight;
			this.maxPlaceCount = maxPlaceCount;
			this.allowInRow = allowInRow;
		}

		public PieceWeight(Class<? extends BlueNetherFortressPieces.NetherBridgePiece> pieceClass, int weight, int maxPlaceCount) {
			this(pieceClass, weight, maxPlaceCount, false);
		}

		public boolean doPlace(int genDepth) {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}

		public boolean isValid() {
			return this.maxPlaceCount == 0 || this.placeCount < this.maxPlaceCount;
		}
	}

	public static class RoomCrossing extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 7;
		private static final int HEIGHT = 9;
		private static final int DEPTH = 7;

		public RoomCrossing(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_ROOM_CROSSING.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public RoomCrossing(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_ROOM_CROSSING.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildForward((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 2, 0, false);
			this.generateChildLeft((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 2, false);
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 0, 2, false);
		}

		public static BlueNetherFortressPieces.RoomCrossing createPiece(StructurePieceAccessor pieces, int x, int y, int z, Direction orientation, int genDepth) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -2, 0, 0, 7, 9, 7, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.RoomCrossing(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 6, 1, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 6, 7, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 1, 6, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 6, 1, 6, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 2, 0, 6, 6, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 2, 6, 6, 6, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 0, 6, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 5, 0, 6, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 2, 0, 6, 6, 1, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 2, 5, 6, 6, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.generateBox(level, box, 2, 6, 0, 4, 6, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 0, 4, 5, 0, blockState, blockState, false);
			this.generateBox(level, box, 2, 6, 6, 4, 6, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 6, 4, 5, 6, blockState, blockState, false);
			this.generateBox(level, box, 0, 6, 2, 0, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 5, 2, 0, 5, 4, blockState2, blockState2, false);
			this.generateBox(level, box, 6, 6, 2, 6, 6, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 5, 2, 6, 5, 4, blockState2, blockState2, false);

			for (int i = 0; i <= 6; i++) {
				for (int j = 0; j <= 6; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class StairsRoom extends BlueNetherFortressPieces.NetherBridgePiece {
		private static final int WIDTH = 7;
		private static final int HEIGHT = 11;
		private static final int DEPTH = 7;

		public StairsRoom(int genDepth, BoundingBox box, Direction orientation) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_STAIRS_ROOM.get(), genDepth, box);
			this.setOrientation(orientation);
		}

		public StairsRoom(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_STAIRS_ROOM.get(), tag);
		}

		@Override
		public void addChildren(@NotNull StructurePiece piece, @NotNull StructurePieceAccessor pieces, @NotNull RandomSource random) {
			this.generateChildRight((BlueNetherFortressPieces.StartPiece)piece, pieces, random, 6, 2, false);
		}

		public static BlueNetherFortressPieces.StairsRoom createPiece(StructurePieceAccessor pieces, int x, int y, int z, int genDepth, Direction orientation) {
			BoundingBox boundingBox = BoundingBox.orientBox(x, y, z, -2, 0, 0, 7, 11, 7, orientation);
			return isOkBox(boundingBox) && pieces.findCollisionPiece(boundingBox) == null
					? new BlueNetherFortressPieces.StairsRoom(genDepth, boundingBox, orientation)
					: null;
		}

		@Override
		public void postProcess(
				@NotNull WorldGenLevel level, @NotNull StructureManager structureManager, @NotNull ChunkGenerator generator, @NotNull RandomSource random, @NotNull BoundingBox box, @NotNull ChunkPos chunkPos, @NotNull BlockPos pos
		) {
			this.generateBox(level, box, 0, 0, 0, 6, 1, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 6, 10, 6, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 0, 1, 8, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 5, 2, 0, 6, 8, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 0, 2, 1, 0, 8, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 2, 1, 6, 8, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 2, 6, 5, 8, 6, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			BlockState blockState = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.WEST, true).setValue(FenceBlock.EAST, true);
			BlockState blockState2 = NetherDescentBlocks.BLUE_NETHER_BRICK_FENCE.get().defaultBlockState().setValue(FenceBlock.NORTH, true).setValue(FenceBlock.SOUTH, true);
			this.generateBox(level, box, 0, 3, 2, 0, 5, 4, blockState2, blockState2, false);
			this.generateBox(level, box, 6, 3, 2, 6, 5, 2, blockState2, blockState2, false);
			this.generateBox(level, box, 6, 3, 4, 6, 5, 4, blockState2, blockState2, false);
			this.placeBlock(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), 5, 2, 5, box);
			this.generateBox(level, box, 4, 2, 5, 4, 3, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 3, 2, 5, 3, 4, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 2, 5, 2, 5, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 2, 5, 1, 6, 5, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 1, 7, 1, 5, 7, 4, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 6, 8, 2, 6, 8, 4, Blocks.AIR.defaultBlockState(), Blocks.AIR.defaultBlockState(), false);
			this.generateBox(level, box, 2, 6, 0, 4, 8, 0, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), false);
			this.generateBox(level, box, 2, 5, 0, 4, 5, 0, blockState, blockState, false);

			for (int i = 0; i <= 6; i++) {
				for (int j = 0; j <= 6; j++) {
					this.fillColumnDown(level, NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().defaultBlockState(), i, -1, j, box);
				}
			}
		}
	}

	public static class StartPiece extends BlueNetherFortressPieces.BridgeCrossing {
		public BlueNetherFortressPieces.PieceWeight previousPiece;
		public List<BlueNetherFortressPieces.PieceWeight> availableBridgePieces;
		public List<BlueNetherFortressPieces.PieceWeight> availableCastlePieces;
		public final List<StructurePiece> pendingChildren = Lists.<StructurePiece>newArrayList();

		public StartPiece(RandomSource random, int x, int z) {
			super(x, z, getRandomHorizontalDirection(random));
			this.availableBridgePieces = Lists.newArrayList();

			for (BlueNetherFortressPieces.PieceWeight pieceWeight : BlueNetherFortressPieces.BRIDGE_PIECE_WEIGHTS) {
				pieceWeight.placeCount = 0;
				this.availableBridgePieces.add(pieceWeight);
			}

			this.availableCastlePieces = Lists.newArrayList();

			for (BlueNetherFortressPieces.PieceWeight pieceWeight : BlueNetherFortressPieces.CASTLE_PIECE_WEIGHTS) {
				pieceWeight.placeCount = 0;
				this.availableCastlePieces.add(pieceWeight);
			}
		}

		public StartPiece(CompoundTag tag) {
			super(NetherDescentStructurePieceType.NETHER_FORTRESS_START.get(), tag);
		}
	}
}
