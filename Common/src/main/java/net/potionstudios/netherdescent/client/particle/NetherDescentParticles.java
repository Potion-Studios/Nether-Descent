package net.potionstudios.netherdescent.client.particle;

import net.minecraft.core.particles.SimpleParticleType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

/**
 * Particles for Nether Descent.
 * @see SimpleParticleType
 * @author Joseph T. McQuigg
 */
public class NetherDescentParticles {

	public static final Supplier<SimpleParticleType> EMBUR_GEL_DRIP = register("embur_gel_drip");
	public static final Supplier<SimpleParticleType> GILL_LEVITATE = register("gill_levitate");
	public static final Supplier<SimpleParticleType> GILL_LEVITATE_POWERED = register("gill_levitate_powered");
	public static final Supplier<SimpleParticleType> SYTHIAN_LEAF = register("sythian_leaf");

    private static Supplier<SimpleParticleType> register(String id) {
        return PlatformHandler.PLATFORM_HANDLER.registerCreateParticle(id);
    }
}
