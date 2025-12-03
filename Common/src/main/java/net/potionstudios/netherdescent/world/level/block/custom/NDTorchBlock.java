package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDTorchBlock extends TorchBlock {
    private final Supplier<SimpleParticleType> simpleParticleTypeSupplier;
    public NDTorchBlock(Supplier<SimpleParticleType> flameParticle, Properties properties) {
        super(ParticleTypes.FLAME, properties);
        this.simpleParticleTypeSupplier = flameParticle;
    }

    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        double d = (double)pos.getX() + (double)0.5F;
        double e = (double)pos.getY() + 0.7;
        double f = (double)pos.getZ() + (double)0.5F;
        level.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0F, 0.0F, 0.0F);
        level.addParticle(this.simpleParticleTypeSupplier.get(), d, e, f, 0.0F, 0.0F, 0.0F);
    }
}
