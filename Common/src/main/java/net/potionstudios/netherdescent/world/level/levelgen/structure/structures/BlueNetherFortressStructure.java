package net.potionstudios.netherdescent.world.level.levelgen.structure.structures;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.potionstudios.netherdescent.world.level.levelgen.structure.NetherDescentStructureType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class BlueNetherFortressStructure extends Structure{

	public static final MapCodec<BlueNetherFortressStructure> CODEC = simpleCodec(BlueNetherFortressStructure::new);

	public BlueNetherFortressStructure(Structure.StructureSettings settings) {
		super(settings);
	}

	@Override
	public @NotNull Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		ChunkPos chunkPos = context.chunkPos();
		BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 64, chunkPos.getMinBlockZ());
		return Optional.of(new Structure.GenerationStub(blockPos, structurePiecesBuilder -> generatePieces(structurePiecesBuilder, context)));
	}

	private static void generatePieces(StructurePiecesBuilder builder, Structure.GenerationContext context) {
		BlueNetherFortressPieces.StartPiece startPiece = new BlueNetherFortressPieces.StartPiece(
				context.random(), context.chunkPos().getBlockX(2), context.chunkPos().getBlockZ(2)
		);
		builder.addPiece(startPiece);
		startPiece.addChildren(startPiece, builder, context.random());
		List<StructurePiece> list = startPiece.pendingChildren;

		while (!list.isEmpty()) {
			int i = context.random().nextInt(list.size());
			StructurePiece structurePiece = list.remove(i);
			structurePiece.addChildren(startPiece, builder, context.random());
		}

		builder.moveInsideHeights(context.random(), 48, 70);
	}

	@Override
	public @NotNull StructureType<?> type() {
		return NetherDescentStructureType.BLUE_FORTRESS.get();
	}
}
