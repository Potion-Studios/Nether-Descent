package net.potionstudios.netherdescent.data.worldgen;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.List;
import java.util.Map;

public class NetherDescentStructureSets {
	public static final Map<ResourceKey<StructureSet>, StructureSetFactory> STRUCTURE_SET_FACTORIES = new Reference2ObjectOpenHashMap<>();

	private static final ResourceKey<StructureSet> NETHER_COMPLEXES = register(BuiltinStructureSets.NETHER_COMPLEXES, context ->
			new StructureSet(
					List.of(
							StructureSet.entry(context.getOrThrow(BuiltinStructures.FORTRESS)),
							StructureSet.entry(context.getOrThrow(NetherDescentStructures.BLUE_FORTRESS)),
							StructureSet.entry(context.getOrThrow(BuiltinStructures.BASTION_REMNANT), 3)
					),
					new RandomSpreadStructurePlacement(27, 4, RandomSpreadType.LINEAR, 30084232)
			));

	private static final ResourceKey<StructureSet> CHAINS = register("chains", context ->
			new StructureSet(
					List.of(
							StructureSet.entry(context.getOrThrow(NetherDescentStructures.CHAINS))
					),
					new RandomSpreadStructurePlacement(4, 2, RandomSpreadType.LINEAR, 755693023)
			));

	private static ResourceKey<StructureSet> register(String id, StructureSetFactory factory) {
		ResourceKey<StructureSet> structureSetResourceKey = NetherDescent.key(Registries.STRUCTURE_SET, id);
		STRUCTURE_SET_FACTORIES.put(structureSetResourceKey, factory);
		return structureSetResourceKey;
	}

	private static ResourceKey<StructureSet> register(ResourceKey<StructureSet> key, StructureSetFactory factory) {
		STRUCTURE_SET_FACTORIES.put(key, factory);
		return key;
	}

	@FunctionalInterface
	public interface StructureSetFactory {
		StructureSet generate(HolderGetter<Structure> placedFeatureHolderGetter);
	}
}
