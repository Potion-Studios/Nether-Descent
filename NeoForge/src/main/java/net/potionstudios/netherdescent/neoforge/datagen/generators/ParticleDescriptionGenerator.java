package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.client.particle.NetherDescentParticles;

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
//		sprite(NetherDescentParticles.GILL_LEVITATE.get(), NetherDescent.id("gill_levitate"));
//		sprite(NetherDescentParticles.GILL_LEVITATE_POWERED.get(), NetherDescent.id("gill_levitate_powered"));
//		sprite(NetherDescentParticles.SYTHIAN_LEAF.get(), NetherDescent.id("sythian_leaf"));
    }
}
