package net.potionstudios.netherdescent.world.level.levelgen.structure;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;
import net.potionstudios.netherdescent.world.level.levelgen.structure.structures.BlueNetherFortressStructure;

import java.util.Map;

public class NetherDescentStructures {
	public static final Map<ResourceKey<Structure>, StructureFactory> STRUCTURE_FACTORIES = new Reference2ObjectOpenHashMap<>();

	public static final ResourceKey<Structure> BLUE_FORTRESS = register("blue_fortress", context ->
			new BlueNetherFortressStructure(
					new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME).getOrThrow(NetherDescentBiomeTags.StructureHasTags.HAS_BLUE_FORTRESS))
							.spawnOverrides(
									Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, NetherFortressStructure.FORTRESS_ENEMIES))
							)
							.generationStep(GenerationStep.Decoration.UNDERGROUND_DECORATION)
							.build()
			)
	);

	private static ResourceKey<Structure> register(String id, StructureFactory factory) {
		ResourceKey<Structure> structureSetResourceKey = NetherDescent.key(Registries.STRUCTURE, id);
		STRUCTURE_FACTORIES.put(structureSetResourceKey, factory);
		return structureSetResourceKey;
	}


	@FunctionalInterface
	public interface StructureFactory {
		Structure generate(BootstrapContext<Structure> structureFactoryBootstapContext);
	}
}
