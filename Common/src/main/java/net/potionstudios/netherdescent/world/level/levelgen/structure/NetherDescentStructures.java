package net.potionstudios.netherdescent.world.level.levelgen.structure;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
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

	public static final ResourceKey<Structure> CHAINS = register("chains", context ->
			createJigsaw(
					structure(context.lookup(Registries.BIOME).getOrThrow(NetherDescentBiomeTags.StructureHasTags.HAS_CHAINS), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE),
					context.lookup(Registries.TEMPLATE_POOL).getOrThrow(NetherDescentTemplatePools.CHAINS_START),
					20,
					ConstantHeight.of(VerticalAnchor.belowTop(17))
			)
	);

	private static ResourceKey<Structure> register(String id, StructureFactory factory) {
		ResourceKey<Structure> structureSetResourceKey = NetherDescent.key(Registries.STRUCTURE, id);
		STRUCTURE_FACTORIES.put(structureSetResourceKey, factory);
		return structureSetResourceKey;
	}

	private static Structure.StructureSettings structure(HolderSet<Biome> tag, TerrainAdjustment adj) {
		return new Structure.StructureSettings(tag, Map.of(), GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
	}

	private static Structure.StructureSettings structure(HolderSet<Biome> tag, Map<MobCategory, StructureSpawnOverride> spawnOverrides, TerrainAdjustment adj) {
		return new Structure.StructureSettings(tag, spawnOverrides, GenerationStep.Decoration.SURFACE_STRUCTURES, adj);
	}

	private static Structure.StructureSettings structure(HolderSet<Biome> tag, GenerationStep.Decoration decoration, TerrainAdjustment adj) {
		return new Structure.StructureSettings(tag, Map.of(), decoration, adj);
	}

	private static JigsawStructure createJigsaw(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth,
	                                            HeightProvider startHeight, Heightmap.Types projectStartToHeightmap) {
		return new JigsawStructure(settings, startPool, maxDepth, startHeight, false, projectStartToHeightmap);
	}

	private static JigsawStructure createJigsaw(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, int maxDepth, HeightProvider startHeight) {
		return new JigsawStructure(settings, startPool, maxDepth, startHeight, false);
	}

	@FunctionalInterface
	public interface StructureFactory {
		Structure generate(BootstrapContext<Structure> structureFactoryBootstapContext);
	}
}
