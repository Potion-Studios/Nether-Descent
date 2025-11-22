package net.potionstudios.netherdescent.config.configs;

import net.potionstudios.netherdescent.config.ConfigLoader;

public class MobSpawnConfig {
    public static SpawnConfig INSTANCE = ConfigLoader.loadConfig(MobSpawnConfig.class, "mob_spawn").spawn;

    public SpawnConfig spawn = new SpawnConfig();

    public static class SpawnConfig {
        public boolean soul_blaze = true;
        public boolean pendorite_blaze = true;
        public boolean hornet = true;
    }

    public static void reload() {
        INSTANCE = ConfigLoader.loadConfig(MobSpawnConfig.class, "mob_spawn").spawn;
    }
}
