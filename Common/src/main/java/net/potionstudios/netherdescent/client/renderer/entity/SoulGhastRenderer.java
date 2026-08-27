package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.GhastRenderer;
import net.minecraft.client.renderer.entity.state.GhastRenderState;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class SoulGhastRenderer extends GhastRenderer {
	private static final Identifier SOUL_GHAST_LOCATION = NetherDescent.id("textures/entity/soul_ghast/soul_ghast.png");
	private static final Identifier SOUL_GHAST_SHOOTING_LOCATION = NetherDescent.id("textures/entity/soul_ghast/soul_ghast_shooting.png");

	public SoulGhastRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public @NotNull Identifier getTextureLocation(@NotNull GhastRenderState ghastRenderState) {
		return ghastRenderState.isCharging ? SOUL_GHAST_SHOOTING_LOCATION : SOUL_GHAST_LOCATION;
	}
}
