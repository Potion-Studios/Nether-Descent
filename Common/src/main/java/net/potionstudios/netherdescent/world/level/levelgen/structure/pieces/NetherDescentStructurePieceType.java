package net.potionstudios.netherdescent.world.level.levelgen.structure.pieces;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.levelgen.structure.structures.BlueNetherFortressPieces;

import java.util.Locale;
import java.util.function.Supplier;

public interface NetherDescentStructurePieceType {

	Supplier<StructurePieceType> NETHER_FORTRESS_BRIDGE_CROSSING = setPieceId(BlueNetherFortressPieces.BridgeCrossing::new, "NeBCr");
	Supplier<StructurePieceType> NETHER_FORTRESS_BRIDGE_END_FILLER = setPieceId(BlueNetherFortressPieces.BridgeEndFiller::new, "NeBEF");
	Supplier<StructurePieceType> NETHER_FORTRESS_BRIDGE_STRAIGHT = setPieceId(BlueNetherFortressPieces.BridgeStraight::new, "NeBS");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_CORRIDOR_STAIRS = setPieceId(BlueNetherFortressPieces.CastleCorridorStairsPiece::new, "NeCCS");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_CORRIDOR_T_BALCONY = setPieceId(BlueNetherFortressPieces.CastleCorridorTBalconyPiece::new, "NeCTB");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_ENTRANCE = setPieceId(BlueNetherFortressPieces.CastleEntrance::new, "NeCE");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_CROSSING = setPieceId(BlueNetherFortressPieces.CastleSmallCorridorCrossingPiece::new, "NeSCSC");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_LEFT_TURN = setPieceId(BlueNetherFortressPieces.CastleSmallCorridorLeftTurnPiece::new, "NeSCLT");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR = setPieceId(BlueNetherFortressPieces.CastleSmallCorridorPiece::new, "NeSC");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_SMALL_CORRIDOR_RIGHT_TURN = setPieceId(BlueNetherFortressPieces.CastleSmallCorridorRightTurnPiece::new, "NeSCRT");
	Supplier<StructurePieceType> NETHER_FORTRESS_CASTLE_STALK_ROOM = setPieceId(BlueNetherFortressPieces.CastleStalkRoom::new, "NeCSR");
	Supplier<StructurePieceType> NETHER_FORTRESS_MONSTER_THRONE = setPieceId(BlueNetherFortressPieces.MonsterThrone::new, "NeMT");
	Supplier<StructurePieceType> NETHER_FORTRESS_ROOM_CROSSING = setPieceId(BlueNetherFortressPieces.RoomCrossing::new, "NeRC");
	Supplier<StructurePieceType> NETHER_FORTRESS_STAIRS_ROOM = setPieceId(BlueNetherFortressPieces.StairsRoom::new, "NeSR");
	Supplier<StructurePieceType> NETHER_FORTRESS_START = setPieceId(BlueNetherFortressPieces.StartPiece::new, "NeStart");

	private static Supplier<StructurePieceType> setFullContextPieceId(String id, Supplier<StructurePieceType> structureTypeSupplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.STRUCTURE_PIECE, id.toLowerCase(Locale.ROOT), structureTypeSupplier);
	}

	private static Supplier<StructurePieceType> setPieceId(StructurePieceType.ContextlessType type, String key) {
		return setFullContextPieceId(key, () -> type);
	}

	static void structurePieceTypes() {
		NetherDescent.LOGGER.info("Registering Nether Descent Structure Pieces");
	}
}
