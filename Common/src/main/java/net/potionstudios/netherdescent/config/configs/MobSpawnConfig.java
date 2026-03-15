package net.potionstudios.netherdescent.config.configs;

import net.potionstudios.netherdescent.config.ConfigLoader;
import net.potionstudios.netherdescent.config.ConfigUtils;

public final class MobSpawnConfig {
    public static SpawnConfig INSTANCE = ConfigLoader.loadConfig(MobSpawnConfig.class, "mob_spawn").spawn;

    public SpawnConfig spawn = new SpawnConfig();

    public static class SpawnConfig {
        public boolean soul_blaze = true;
        public boolean pendorite_blaze = true;
        public ConfigUtils.CommentValue<Boolean> hornet = ConfigUtils.CommentValue.of("This will also disable the generation of Hornet Nests", true);
        public boolean soul_ghast = true;
    }

    public static void reload() {
        INSTANCE = ConfigLoader.loadConfig(MobSpawnConfig.class, "mob_spawn").spawn;
    }
}
