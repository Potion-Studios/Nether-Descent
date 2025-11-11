package net.potionstudios.netherdescent.core.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class FallingParticle extends TextureSheetParticle {
	FallingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.lifetime = (int)((double)64.0F / (Math.random() * 0.8 + 0.2));
		this.gravity = 0.06F;
		this.setSize(0.01F, 0.01F);
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd = this.yd - this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= 0.98F;
                this.yd *= 0.98F;
                this.zd *= 0.98F;
            }
        }
	}

    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }
    }

    protected void postMoveUpdate() {
        if (this.onGround)
            this.remove();
    }

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			FallingParticle particle = new FallingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.setSprite(this.spriteSet.get(level.getRandom()));
			return particle;
		}
	}
}
