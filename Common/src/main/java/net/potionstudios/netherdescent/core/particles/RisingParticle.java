package net.potionstudios.netherdescent.core.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class RisingParticle extends TextureSheetParticle {
    RisingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

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
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new RisingParticle(
                    level, x, y, z,
                    xSpeed, ySpeed, zSpeed,
                    spriteSet
            );
        }
    }
}
