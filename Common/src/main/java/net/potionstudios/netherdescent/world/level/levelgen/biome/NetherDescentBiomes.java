package net.potionstudios.netherdescent.world.level.levelgen.biome;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NetherDescentBiomes {

	public static final Map<ResourceKey<Biome>, BiomeFactory> BIOME_FACTORIES = new Reference2ObjectOpenHashMap<>();
	public static final Multimap<TagKey<Biome>, ResourceKey<Biome>> BIOMES_BY_TAG = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);

	public static final ResourceKey<Biome> EMBUR_BOG = createBiome("embur_bog", NetherDescentBiomeBuilder::emburBog);
	public static final ResourceKey<Biome> SYTHIAN_TORRIDS = createBiome("sythian_torrids", NetherDescentBiomeBuilder::sythianTorrids);
	public static final ResourceKey<Biome> WAILING_GARTH = createBiome("wailing_garth", NetherDescentBiomeBuilder::wailingGarth);

	@SafeVarargs
	private static ResourceKey<Biome> createBiome(String id, BiomeFactory biomeFactory, TagKey<Biome>... tags) {
		ResourceKey<Biome> biomeResourceKey = NetherDescent.key(Registries.BIOME, id);
		BIOME_FACTORIES.put(biomeResourceKey, biomeFactory);

		for (TagKey<Biome> tag : tags)
			BIOMES_BY_TAG.put(tag, biomeResourceKey);

		return biomeResourceKey;
	}


	@FunctionalInterface
	public interface BiomeFactory {
		Biome generate(HolderGetter<PlacedFeature> placedFeatureHolderGetter, HolderGetter<ConfiguredWorldCarver<?>> worldCarverHolderGetter);
	}
}
