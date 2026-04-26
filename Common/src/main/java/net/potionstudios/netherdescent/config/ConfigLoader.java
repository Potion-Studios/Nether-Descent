package net.potionstudios.netherdescent.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Makes or loads a config file
 * @see Gson
 * @author Joseph T. McQuigg
 */
public class ConfigLoader {
	/** The Gson instance for the Config Loader. */
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * Loads or Creates a config file
	 *
	 * @param clazz      The class of the config file.
	 * @return The config file.
	 */
	public static <T> T loadConfig(@NotNull Class<T> clazz, String name) {
		Path configPath = PlatformHandler.PLATFORM_HANDLER.configPath().resolve(name + ".json");

		try {
			if (Files.notExists(configPath.getParent()))
				Files.createDirectories(configPath.getParent());

			T value = clazz.getConstructor().newInstance();

			if (Files.exists(configPath))
				value = GSON.fromJson(Files.newBufferedReader(configPath), clazz);

			Files.writeString(configPath, GSON.toJson(value));
			return value;
		} catch (Exception e) {
			try {
				T value = clazz.getConstructor().newInstance();
				Files.writeString(configPath, GSON.toJson(value));
				NetherDescent.LOGGER.warn("Failed to load config '{}'. It has been regenerated and all settings have been reset to their default values.", name, e);
				return value;
			} catch (Exception regenerationException) {
				throw new RuntimeException("Failed to regenerate config.", regenerationException);
			}
		}
	}

	public static <T> void saveConfig(@NotNull T config, String name) {
		try {
			Path path = PlatformHandler.PLATFORM_HANDLER.configPath().resolve(name + ".json");
			if (Files.notExists(path.getParent()))
				Files.createDirectories(path.getParent());

			Files.writeString(path, GSON.toJson(config));
		} catch (Exception e) {
			throw new RuntimeException("Failed to save biome config", e);
		}
	}
}