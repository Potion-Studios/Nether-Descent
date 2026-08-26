package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.model.BeeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.resources.ResourceLocation;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import org.jetbrains.annotations.NotNull;

public class HornetRenderer extends MobRenderer<Hornet, BeeRenderState, BeeModel> {
    private static final ResourceLocation ANGRY_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry.png");
    private static final ResourceLocation ANGRY_POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry_pollen.png");
    private static final ResourceLocation HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet.png");
    private static final ResourceLocation POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_pollen.png");

    public HornetRenderer(EntityRendererProvider.Context context) {
        super(context, new HornetModel(context.bakeLayer(NetherDescentModelLayers.HORNET)), 0.4F);
    }

    @Override
    public @NotNull BeeRenderState createRenderState() {
        return new BeeRenderState();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull BeeRenderState renderState) {
        if (renderState.isAngry) return renderState.hasNectar ? ANGRY_POLLEN_HORNET_TEXTURE : ANGRY_HORNET_TEXTURE;
        return renderState.hasNectar ? POLLEN_HORNET_TEXTURE : HORNET_TEXTURE;
    }
}
