package net.potionstudios.netherdescent.world.level.levelgen.biome;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.tags.NetherDescentBiomeTags;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NetherDescentBiomes {

	public static final Map<ResourceKey<Biome>, BiomeDefinition> BIOME_FACTORIES = new Reference2ObjectOpenHashMap<>();
	public static final Multimap<TagKey<Biome>, ResourceKey<Biome>> BIOMES_BY_TAG = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

	public static final ResourceKey<Biome> ARISIAN_UNDERGROWTH = createBiome("arisian_undergrowth",
			NetherDescentBiomeBuilder::arisianUndergrowth,
			Climate.parameters(0.75F, 0.9F, 0F, -0.1F, 0.0F, 0.6F, 0F),
			BiomeTags.HAS_NETHER_FOSSIL, BiomeTags.HAS_BASTION_REMNANT, BiomeTags.HAS_NETHER_FORTRESS,
			NetherDescentBiomeTags.DRY, NetherDescentBiomeTags.HOT, NetherDescentBiomeTags.FOREST
	);
	public static final ResourceKey<Biome> CRIMSON_GARDENS = createBiome("crimson_gardens",
			NetherDescentBiomeBuilder::crimsonGardens,
			Climate.parameters(1.0F, 0.7F, 0F, 0.3F, 0.0F, -0.4F, 0F),
			BiomeTags.HAS_NETHER_FOSSIL, BiomeTags.HAS_BASTION_REMNANT, BiomeTags.HAS_NETHER_FORTRESS,
			NetherDescentBiomeTags.DRY, NetherDescentBiomeTags.HOT
	);
	public static final ResourceKey<Biome> EMBUR_BOG = createBiome("embur_bog",
			NetherDescentBiomeBuilder::emburBog,
			Climate.parameters(1.0F, 0.85F, 0F, -0.2F, 0.0F, -0.1F, 0F),
			NetherDescentBiomeTags.StructureHasTags.HAS_BLUE_FORTRESS,
			NetherDescentBiomeTags.DRY, NetherDescentBiomeTags.HOT
	);
	public static final ResourceKey<Biome> SYTHIAN_TORRIDS = createBiome("sythian_torrids",
			NetherDescentBiomeBuilder::sythianTorrids,
			Climate.parameters(1.0F, 0.6F, 0F, -0.4F, 0F, 0.8F, 0F),
			NetherDescentBiomeTags.DRY, NetherDescentBiomeTags.HOT, NetherDescentBiomeTags.FOREST
	);
	public static final ResourceKey<Biome> WAILING_GARTH = createBiome("wailing_garth",
			NetherDescentBiomeBuilder::wailingGarth,
			Climate.parameters(1.0F, 0.5F, 0F, 0.7F, 0F, -0.2F, 0F),
			NetherDescentBiomeTags.StructureHasTags.HAS_CHAINS, BiomeTags.HAS_NETHER_FOSSIL, BiomeTags.HAS_BASTION_REMNANT, BiomeTags.HAS_NETHER_FORTRESS,
			NetherDescentBiomeTags.DRY, NetherDescentBiomeTags.HOT
	);

	@SafeVarargs
	private static ResourceKey<Biome> createBiome(String id, BiomeFactory biomeFactory, Climate.ParameterPoint parameterPoint, TagKey<Biome>... tags) {
		ResourceKey<Biome> biomeResourceKey = NetherDescent.key(Registries.BIOME, id);
		BIOME_FACTORIES.put(biomeResourceKey, new BiomeDefinition(biomeFactory, parameterPoint));

		for (TagKey<Biome> tag : tags)
			BIOMES_BY_TAG.put(tag, biomeResourceKey);

		return biomeResourceKey;
	}

	public record BiomeDefinition(BiomeFactory factory, Climate.ParameterPoint parameterPoint) {}

	@FunctionalInterface
	public interface BiomeFactory {
		Biome generate(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> worldCarverHolderGetter);
	}
}
