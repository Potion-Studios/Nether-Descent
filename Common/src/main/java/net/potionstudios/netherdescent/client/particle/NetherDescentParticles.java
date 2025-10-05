package net.potionstudios.netherdescent.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

/**
 * Particles for Nether Descent.
 * @see SimpleParticleType
 * @author Joseph T. McQuigg
 */
public class NetherDescentParticles {

    private static Supplier<SimpleParticleType> register(String id) {
        return PlatformHandler.PLATFORM_HANDLER.registerCreateParticle(id);
    }
}
