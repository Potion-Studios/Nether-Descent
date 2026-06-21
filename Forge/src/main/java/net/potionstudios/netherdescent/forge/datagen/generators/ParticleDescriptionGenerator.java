package net.potionstudios.netherdescent.forge.datagen.generators;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.core.particles.NetherDescentParticles;

public class ParticleDescriptionGenerator extends ParticleDescriptionProvider {
    /**
     * Creates an instance of the data provider.
     *
     * @param output     the expected root directory the data generator outputs to
     * @param fileHelper the helper used to validate a texture's existence
     */
    public ParticleDescriptionGenerator(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        spriteSet(NetherDescentParticles.EMBUR_GEL_DRIP.get(), NetherDescent.id("embur_gel_drip"), 2, false);
        spriteSet(NetherDescentParticles.GILL_LEVITATE.get(), NetherDescent.id("gill_levitate"), 6, false);
        spriteSet(NetherDescentParticles.GILL_LEVITATE_POWERED.get(), NetherDescent.id("gill_powered_levitate"), 6, false);
        sprite(NetherDescentParticles.SYTHIAN_LEAF.get(), NetherDescent.id("sythian_leaf"));
        sprite(NetherDescentParticles.PENDORITE_FIRE_FLAME.get(), NetherDescent.id("pendorite_fire_flame"));
        sprite(NetherDescentParticles.ARISIAN_LEAF.get(), NetherDescent.id("arisian_leaf"));
    }
}