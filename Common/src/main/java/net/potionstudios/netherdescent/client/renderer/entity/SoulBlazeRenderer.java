package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Blaze;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class SoulBlazeRenderer extends BlazeRenderer {
    private static final ResourceLocation SOUL_BLAZE_LOCATION = NetherDescent.id("textures/entity/soul_blaze.png");

    public SoulBlazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Blaze entity) {
        return SOUL_BLAZE_LOCATION;
    }
}
