package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import org.jetbrains.annotations.NotNull;

public class HornetRenderer extends MobRenderer<Hornet, HornetModel> {
    private static final Identifier ANGRY_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry.png");
    private static final Identifier ANGRY_POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry_pollen.png");
    private static final Identifier HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet.png");
    private static final Identifier POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_pollen.png");

    public HornetRenderer(EntityRendererProvider.Context context) {
        super(context, new HornetModel(context.bakeLayer(NetherDescentModelLayers.HORNET)), 0.4F);
    }

    @Override
    public @NotNull Identifier getTextureLocation(@NotNull Hornet entity) {
        if (entity.isAngry()) return entity.hasNectar() ? ANGRY_POLLEN_HORNET_TEXTURE : ANGRY_HORNET_TEXTURE;
        return entity.hasNectar() ? POLLEN_HORNET_TEXTURE : HORNET_TEXTURE;
    }
}
