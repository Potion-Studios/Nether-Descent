package net.potionstudios.netherdescent.client.particle.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class FallingParticle extends TextureSheetParticle {
	FallingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed);
		this.hasPhysics = true;
		this.gravity = 0.8F;
		this.setSize(0.02F, 0.02F);
	}

	@Override
	public @NotNull ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		super.tick();
		if (this.onGround)
			this.remove();
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public @NotNull Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
			FallingParticle particle = new FallingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
			particle.setColor(1.0F, 1.0F, 1.0F);
			particle.setSprite(this.spriteSet.get(level.getRandom()));
			return particle;
		}
	}
}
