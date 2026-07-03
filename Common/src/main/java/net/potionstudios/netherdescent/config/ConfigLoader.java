package net.potionstudios.netherdescent.config;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.netherdescent.PlatformHandler;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Makes or loads a config file
 * @see Gson
 * @author Joseph T. McQuigg
 */
public class ConfigLoader {
	/** The Gson instance for the Config Loader. */
	private static final Gson GSON = new GsonBuilder()
			.setPrettyPrinting().
			registerTypeHierarchyAdapter(ResourceLocation.class, new TypeAdapter<ResourceLocation>() {
				@Override
				public void write(JsonWriter out, ResourceLocation value) throws java.io.IOException {
					out.value(value == null ? null : value.toString());
				}

				@Override
				public ResourceLocation read(JsonReader in) throws java.io.IOException {
					return ResourceLocation.parse(in.nextString());
				}
			})
			.create();

	/**
	 * Loads or Creates a config file
	 *
	 * @param clazz      The class of the config file.
	 * @return The config file.
	 */
	public static <T> T loadConfig(@NotNull Class<T> clazz, String name) {
		try {
			Path configPath = PlatformHandler.PLATFORM_HANDLER.configPath().resolve(name + ".json");
			T defaultValue = clazz.getConstructor().newInstance();

			if (Files.notExists(configPath)) {
				Files.createDirectories(configPath.getParent());
				Files.writeString(configPath, GSON.toJson(defaultValue));
				return defaultValue;
			}

			JsonObject userJson;
			try (Reader reader = Files.newBufferedReader(configPath)) {
				JsonElement parsed = JsonParser.parseReader(reader);
				userJson = parsed.isJsonObject() ? parsed.getAsJsonObject() : new JsonObject();
			} catch (Exception e) {
				userJson = new JsonObject();
			}

			JsonObject defaultJson = GSON.toJsonTree(defaultValue).getAsJsonObject();

			merge(defaultJson, userJson);

			Files.writeString(configPath, GSON.toJson(defaultJson));

			return GSON.fromJson(defaultJson, clazz);
		} catch (Exception e) {
			throw new RuntimeException("Failed to load config " + name + ".", e);
		}
	}

	private static void merge(JsonObject defaultJson, JsonObject userJson) {
		if (defaultJson.has("comment") && defaultJson.has("value")) {
			if (userJson.has("value")) {
				JsonElement userVal = userJson.get("value");
				JsonElement defaultVal = defaultJson.get("value");

				if (defaultVal.isJsonObject() && userVal.isJsonObject()) {
					merge(defaultVal.getAsJsonObject(), userVal.getAsJsonObject());
				} else if (defaultVal.isJsonObject() == userVal.isJsonObject() &&
						defaultVal.isJsonArray() == userVal.isJsonArray() &&
						defaultVal.isJsonPrimitive() == userVal.isJsonPrimitive()) {
					defaultJson.add("value", userVal);
				}
			}
			return;
		}

		for (Map.Entry<String, JsonElement> entry : userJson.entrySet()) {
			String key = entry.getKey();
			JsonElement userValue = entry.getValue();

			if (defaultJson.has(key)) {
				JsonElement defaultElement = defaultJson.get(key);

				if (defaultElement.isJsonObject() != userValue.isJsonObject() ||
						defaultElement.isJsonArray() != userValue.isJsonArray() ||
						defaultElement.isJsonPrimitive() != userValue.isJsonPrimitive()) {
					continue;
				}

				if (defaultElement.isJsonObject())
					merge(defaultElement.getAsJsonObject(), userValue.getAsJsonObject());
				else defaultJson.add(key, userValue);
			}
		}
	}
}