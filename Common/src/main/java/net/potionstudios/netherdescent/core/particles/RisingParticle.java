package net.potionstudios.netherdescent.core.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class RisingParticle extends SingleQuadParticle {
    RisingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites.first());

        this.gravity = 0;
        this.xd = 0;
        this.yd = 0.1;
        this.zd = 0;

        this.lifetime = 40 + level.random.nextInt(20);

        this.setSpriteFromAge(sprites);
        this.quadSize = 0.05F;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos = BlockPos.containing(x, y + yd, z);
        if (!this.level.getBlockState(pos).getCollisionShape(this.level, pos).isEmpty())
            this.remove();
    }

    @Override
    protected @NotNull SingleQuadParticle.Layer getLayer() {
        return SingleQuadParticle.Layer.TRANSLUCENT;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NonNull Particle createParticle(SimpleParticleType particleType, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
            return new RisingParticle(
                    level, x, y, z,
                    xSpeed, ySpeed, zSpeed,
                    spriteSet
            );
        }
    }
}