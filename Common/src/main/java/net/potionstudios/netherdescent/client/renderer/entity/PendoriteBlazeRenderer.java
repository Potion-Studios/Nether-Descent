package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.BlazeRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.Blaze;
import net.potionstudios.netherdescent.NetherDescent;
import org.jetbrains.annotations.NotNull;

public class PendoriteBlazeRenderer extends BlazeRenderer {
    private static final Identifier PENDORITE_BLAZE_LOCATION = NetherDescent.id("textures/entity/pendorite_blaze.png");

    public PendoriteBlazeRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull Blaze entity) {
        return PENDORITE_BLAZE_LOCATION;
    }
}
