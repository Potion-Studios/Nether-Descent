package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import org.jspecify.annotations.NonNull;

public class PendoriteBlazeRenderer extends BlazeRenderer {
    private static final Identifier PENDORITE_BLAZE_LOCATION = NetherDescent.id("textures/entity/pendorite_blaze.png");

    public PendoriteBlazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull LivingEntityRenderState state) {
        return PENDORITE_BLAZE_LOCATION;
    }
}
