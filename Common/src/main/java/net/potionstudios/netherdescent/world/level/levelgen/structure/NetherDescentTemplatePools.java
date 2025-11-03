package net.potionstudios.netherdescent.world.level.levelgen.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class NetherDescentTemplatePools {

	public static final Map<ResourceKey<StructureTemplatePool>, TemplatePoolFactory> TEMPLATE_POOL_FACTORIES = new Reference2ObjectOpenHashMap<>();

	public static final ResourceKey<StructureTemplatePool> CHAINS_START = register("chains_start", templatePoolFactoryContext ->
		createTemplatePool(getEmptyPool(templatePoolFactoryContext),
				ImmutableList.of(
					Pair.of(StructurePoolElement.single(NetherDescentStructures.CHAIN_START_1.location().toString(), getEmptyProcessor(templatePoolFactoryContext)), 1),
					Pair.of(StructurePoolElement.single(NetherDescentStructures.CHAIN_START_2.location().toString(), getEmptyProcessor(templatePoolFactoryContext)), 1),
					Pair.of(StructurePoolElement.single(NetherDescentStructures.CHAIN_START_3.location().toString(), getEmptyProcessor(templatePoolFactoryContext)), 1),
					Pair.of(StructurePoolElement.single(NetherDescentStructures.CHAIN_START_4.location().toString(), getEmptyProcessor(templatePoolFactoryContext)), 1)),
				StructureTemplatePool.Projection.RIGID));

	private static StructureTemplatePool createTemplatePool(Holder<StructureTemplatePool> fallback, List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> rawTemplateFactories, StructureTemplatePool.Projection projection) {
		return new StructureTemplatePool(fallback, rawTemplateFactories, projection);
	}

	private static Holder.Reference<StructureTemplatePool> getEmptyPool(BootstrapContext<StructureTemplatePool> context) {
		return getPool(context, Pools.EMPTY);
	}

	private static Holder.Reference<StructureTemplatePool> getPool(BootstrapContext<StructureTemplatePool> context, ResourceKey<StructureTemplatePool> poolResourceKey) {
		return context.lookup(Registries.TEMPLATE_POOL).getOrThrow(poolResourceKey);
	}

	private static Holder.Reference<StructureProcessorList> getEmptyProcessor(BootstrapContext<StructureTemplatePool> context) {
		return getProcessor(context, ProcessorLists.EMPTY);
	}

	private static Holder.Reference<StructureProcessorList> getProcessor(BootstrapContext<StructureTemplatePool> context, ResourceKey<StructureProcessorList> processorList) {
		return context.lookup(Registries.PROCESSOR_LIST).getOrThrow(processorList);
	}

	private static ResourceKey<StructureTemplatePool> register(String id, TemplatePoolFactory factory) {
		ResourceKey<StructureTemplatePool> templatePoolResourceKey = NetherDescent.key(Registries.TEMPLATE_POOL, id);
		TEMPLATE_POOL_FACTORIES.put(templatePoolResourceKey, factory);
		return templatePoolResourceKey;
	}

	public static void templatePools() {
		NetherDescent.LOGGER.info("Registering Nether Descent Template Pools");
	}

	@FunctionalInterface
	public interface TemplatePoolFactory {
		StructureTemplatePool generate(BootstrapContext<StructureTemplatePool> templatePoolFactoryContext);
	}
}
