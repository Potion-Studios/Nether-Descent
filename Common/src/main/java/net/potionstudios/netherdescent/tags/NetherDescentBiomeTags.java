package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.potionstudios.netherdescent.NetherDescent;

public final class NetherDescentBiomeTags {

    /** Correlates to
     * @see BiomeTags#IS_NETHER
     * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#IN_NETHER,
     **/
    public static final TagKey<Biome> NETHER = create("nether");

	/** Correlates to
	 * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_HOT
	 **/
	public static final TagKey<Biome> HOT = create("climate/hot");

	/** Correlates to
	 * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#CLIMATE_DRY
	 */
	public static final TagKey<Biome> DRY = create("climate/dry");

	/** Correlates to
	 * @see net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags#NETHER_FORESTS
	 */
	public static final TagKey<Biome> FOREST = create("forest");

	public static class StructureHasTags {
		public static final TagKey<Biome> HAS_BLUE_FORTRESS = create("has_structure/blue_fortress");

		public static final TagKey<Biome> HAS_CHAINS = create("has_structure/chains");
	}

    private static TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, NetherDescent.id(name));
    }
}
