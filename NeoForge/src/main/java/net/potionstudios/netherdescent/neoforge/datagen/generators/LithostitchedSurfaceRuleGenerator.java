package net.potionstudios.netherdescent.neoforge.datagen.generators;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentSurfaceRules;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class LithostitchedSurfaceRuleGenerator implements DataProvider {
	private final PackOutput output;

	public LithostitchedSurfaceRuleGenerator(PackOutput output) {
		this.output = output;
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		JsonObject encodedRule = SurfaceRules.RuleSource.CODEC
				.encodeStart(JsonOps.INSTANCE, NetherDescentSurfaceRules.makeRules())
				.getOrThrow()
				.getAsJsonObject();

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
	public @NotNull String getName() {
		return "Nether Descent Lithostitched Surface Rules";
	}
}
