package net.potionstudios.netherdescent.config.configs;

import net.potionstudios.netherdescent.config.ConfigLoader;

public record DevConfig(boolean startInNether) {


    public DevConfig() {
        this(true);
    }

    private static DevConfig INSTANCE = null;


    public static DevConfig getInstance(boolean reload) {
        if (INSTANCE == null || reload) {
            return INSTANCE = ConfigLoader.loadConfig(DevConfig.class, "dev");
        }
        return INSTANCE;
    }
}
