package net.potionstudios.netherdescent.world.level.levelgen.structure;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.world.level.levelgen.structure.structures.BlueNetherFortressStructure;

import java.util.function.Supplier;

public interface NetherDescentStructureType<S extends Structure> {
	Supplier<StructureType<BlueNetherFortressStructure>> BLUE_FORTRESS = register("blue_fortress", () -> () -> BlueNetherFortressStructure.CODEC);

	private static <S extends Structure> Supplier<StructureType<S>> register(String id, Supplier<StructureType<S>> structureType) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.STRUCTURE_TYPE, id, structureType);
	}

	static void structureTypes() {
		NetherDescent.LOGGER.info("Registering Nether Descent Structure Types");
	}
}
