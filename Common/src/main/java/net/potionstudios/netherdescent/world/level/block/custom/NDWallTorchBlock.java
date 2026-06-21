package net.potionstudios.netherdescent.world.level.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class NDWallTorchBlock extends WallTorchBlock {
    private final Supplier<SimpleParticleType> particleSupplier;
    public NDWallTorchBlock(Supplier<SimpleParticleType> flameParticle, Properties properties) {
        super(properties, ParticleTypes.FLAME);
        this.particleSupplier = flameParticle;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        Direction direction = state.getValue(FACING);
        double d = (double)pos.getX() + (double)0.5F;
        double e = (double)pos.getY() + 0.7;
        double f = (double)pos.getZ() + (double)0.5F;
        Direction direction2 = direction.getOpposite();
        level.addParticle(ParticleTypes.SMOKE, d + 0.27 * (double)direction2.getStepX(), e + 0.22, f + 0.27 * (double)direction2.getStepZ(), 0.0F, 0.0F, 0.0F);
        level.addParticle(this.particleSupplier.get(), d + 0.27 * (double)direction2.getStepX(), e + 0.22, f + 0.27 * (double)direction2.getStepZ(), 0.0F, 0.0F, 0.0F);
    }
}
