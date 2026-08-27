package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class SoulBlazeRenderer extends BlazeRenderer {
    private static final Identifier SOUL_BLAZE_LOCATION = NetherDescent.id("textures/entity/soul_blaze.png");

    public SoulBlazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull LivingEntityRenderState renderState) {
        return SOUL_BLAZE_LOCATION;
    }
}
