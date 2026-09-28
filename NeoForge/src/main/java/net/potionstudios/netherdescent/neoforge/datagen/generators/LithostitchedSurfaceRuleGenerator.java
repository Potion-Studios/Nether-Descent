package net.potionstudios.netherdescent.neoforge.datagen.generators;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LithostitchedSurfaceRuleGenerator implements DataProvider {
	private final PackOutput output;
	private final CompletableFuture<HolderLookup.Provider> lookup;

	public LithostitchedSurfaceRuleGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		this.output = output;
		this.lookup = lookupProvider;
	}

	@Override
	public @NonNull CompletableFuture<?> run(@NonNull CachedOutput output) {
		JsonObject encodedRule = null;
		try {
			encodedRule = SurfaceRules.RuleSource.CODEC
					.encodeStart(JsonOps.INSTANCE, NetherDescentSurfaceRules.makeRules(lookup.get().lookupOrThrow(Registries.BIOME)))
					.getOrThrow()
					.getAsJsonObject();
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}

		JsonObject modifier = new JsonObject();
		modifier.addProperty("type", "lithostitched:add_surface_rule");
		modifier.addProperty("priority", 1000);
		var levels = new com.google.gson.JsonArray();
		levels.add("minecraft:the_nether");
		modifier.add("levels", levels);
		modifier.add("surface_rule", encodedRule);

		Path path = this.output.getOutputFolder()
				.resolve("data/netherdescent/lithostitched/worldgen_modifier/nether_surface_rules.json");

		return DataProvider.saveStable(output, modifier, path);
	}

	@Override
	public @NonNull String getName() {
		return "Nether Descent Lithostitched Surface Rules";
	}
}
