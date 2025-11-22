package net.potionstudios.netherdescent.config.configs;

import net.potionstudios.netherdescent.config.ConfigLoader;

public class StructureConfig {
    public static StructureConfig INSTANCE = ConfigLoader.loadConfig(StructureConfig.class, "structures");

    public Config config = new Config();

    public static class Config {
        public boolean blue_fortress = true;
    }

    public static void reload() {
        INSTANCE = ConfigLoader.loadConfig(StructureConfig.class, "structures");
    }
}
