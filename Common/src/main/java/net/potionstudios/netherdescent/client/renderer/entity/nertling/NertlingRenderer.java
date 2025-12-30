package net.potionstudios.netherdescent.client.renderer.entity.nertling;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.potionstudios.netherdescent.world.entity.npc.Nertling;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class NertlingRenderer<T extends Nertling> extends GeoEntityRenderer<T> {
    public NertlingRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new NertlingModel<>());
    }

    @Override
    protected float getShadowRadius(@NotNull T entity) {
        return 0.9F;
    }
}
