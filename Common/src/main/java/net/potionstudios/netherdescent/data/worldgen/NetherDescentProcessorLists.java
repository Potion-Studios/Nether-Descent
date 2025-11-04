package net.potionstudios.netherdescent.data.worldgen;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.Map;

public class NetherDescentProcessorLists {
	public static final Map<ResourceKey<StructureProcessorList>, StructureProcessorListFactory> STRUCTURE_PROCESSOR_LIST_FACTORIES = new Reference2ObjectOpenHashMap<>();

	public static final ResourceKey<StructureProcessorList> CHAINS = register("chains", structureProcessorListHolderGetter -> new StructureProcessorList(
			ImmutableList.of(
					createRuleProcessor(
							createAlwaysTrueRandomBlockMatchTest(Blocks.BLACKSTONE, .33F, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS),
							createAlwaysTrueRandomBlockMatchTest(Blocks.BLACKSTONE, .5F, Blocks.POLISHED_BLACKSTONE_BRICKS)
					)
			)
	));

	private static RuleProcessor createRuleProcessor(ProcessorRule... rules) {
		return new RuleProcessor(ImmutableList.copyOf(rules));
	}

	private static ProcessorRule createAlwaysTrueRandomBlockMatchTest(Block start, float chance, Block newBlock) {
		return createProcessorRule(createRandomBlockMatchTest(start, chance), AlwaysTrueTest.INSTANCE, newBlock.defaultBlockState());
	}

	private static RandomBlockMatchTest createRandomBlockMatchTest(Block block, float chance) {
		return new RandomBlockMatchTest(block, chance);
	}

	private static ProcessorRule createProcessorRule(RuleTest test, AlwaysTrueTest alwaysTrueTest, BlockState blockState) {
		return new ProcessorRule(test, alwaysTrueTest, blockState);
	}

	private static ResourceKey<StructureProcessorList> register(String id, StructureProcessorListFactory factory) {
		ResourceKey<StructureProcessorList> structureProcessorListResourceKey = NetherDescent.key(Registries.PROCESSOR_LIST, id);
		STRUCTURE_PROCESSOR_LIST_FACTORIES.put(structureProcessorListResourceKey, factory);
		return structureProcessorListResourceKey;
	}

	@FunctionalInterface
	public interface StructureProcessorListFactory  {
		StructureProcessorList generate(HolderGetter<StructureProcessorList> structureProcessorListHolderGetter);
	}
}
