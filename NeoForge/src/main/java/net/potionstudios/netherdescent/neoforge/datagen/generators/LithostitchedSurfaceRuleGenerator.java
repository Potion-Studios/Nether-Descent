package net.potionstudios.netherdescent.neoforge.datagen.generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import org.jspecify.annotations.NonNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class LithostitchedSurfaceRuleGenerator implements DataProvider {
	private final PackOutput output;
	private final CompletableFuture<HolderLookup.Provider> lookup;

	public LithostitchedSurfaceRuleGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		this.output = output;
		this.lookup = lookupProvider;
	}

	@Override
	public @NonNull CompletableFuture<?> run(@NonNull CachedOutput output) {
		return this.lookup.thenCompose(provider -> {
			RegistryOps<JsonElement> ops = provider.createSerializationContext(JsonOps.INSTANCE);

			JsonObject encodedRule = SurfaceRules.RuleSource.CODEC
					.encodeStart(ops, NetherDescentSurfaceRules.makeRules(provider.lookupOrThrow(Registries.BIOME)))
					.getOrThrow()
					.getAsJsonObject();

			JsonObject modifier = new JsonObject();
			modifier.addProperty("type", "lithostitched:add_surface_rule");
			modifier.addProperty("priority", 1000);
			JsonArray levels = new JsonArray();
			levels.add("minecraft:the_nether");
			modifier.add("levels", levels);
			modifier.add("surface_rule", encodedRule);

			Path path = this.output.getOutputFolder().resolve("data/netherdescent/lithostitched/worldgen_modifier/nether_surface_rules.json");

			return DataProvider.saveStable(output, modifier, path);
		});
	}

	@Override
	public @NonNull String getName() {
		return "Nether Descent Lithostitched Surface Rules";
	}
}
