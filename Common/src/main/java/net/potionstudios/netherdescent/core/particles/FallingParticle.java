package net.potionstudios.netherdescent.core.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jspecify.annotations.NonNull;

public class FallingParticle extends SingleQuadParticle {
	FallingParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, TextureAtlasSprite sprite) {
		super(level, x, y, z, xSpeed, ySpeed, zSpeed, sprite);
		this.lifetime = (int)((double)64.0F / (Math.random() * 0.8 + 0.2));
		this.gravity = 0.06F;
		this.setSize(0.01F, 0.01F);
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

	@Override
	protected @NonNull Layer getLayer() {
		return Layer.TRANSLUCENT;
	}

	public record Provider(SpriteSet spriteSet) implements ParticleProvider<SimpleParticleType> {
		@Override
		public @NonNull Particle createParticle(@NonNull SimpleParticleType particleType, @NonNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, @NonNull RandomSource random) {
			return new FallingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet.get(level.getRandom()));
		}
	}
}
