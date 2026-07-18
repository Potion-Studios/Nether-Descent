package net.potionstudios.netherdescent.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.client.model.HornetModel;
import net.potionstudios.netherdescent.client.model.geom.NetherDescentModelLayers;
import net.potionstudios.netherdescent.world.entity.animal.Hornet;
import org.jspecify.annotations.NonNull;

public class HornetRenderer extends MobRenderer<Hornet, BeeRenderState, HornetModel> {
    private static final Identifier ANGRY_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry.png");
    private static final Identifier ANGRY_POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_angry_pollen.png");
    private static final Identifier HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet.png");
    private static final Identifier POLLEN_HORNET_TEXTURE = NetherDescent.id("textures/entity/hornet/hornet_pollen.png");

    public HornetRenderer(EntityRendererProvider.Context context) {
        super(context, new HornetModel(context.bakeLayer(NetherDescentModelLayers.HORNET)), 0.4F);
    }

    @Override
    public @NonNull BeeRenderState createRenderState() {
        return new BeeRenderState();
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull BeeRenderState state) {
        if (state.isAngry) return state.hasNectar ? ANGRY_POLLEN_HORNET_TEXTURE : ANGRY_HORNET_TEXTURE;
        return state.hasNectar ? POLLEN_HORNET_TEXTURE : HORNET_TEXTURE;
    }

    @Override
    public void extractRenderState(@NonNull Hornet entity, @NonNull BeeRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.rollAmount = entity.getRollAmount(partialTicks);
        state.hasStinger = !entity.hasStung();
        state.isOnGround = entity.onGround() && entity.getDeltaMovement().lengthSqr() < 1.0E-7;
        state.isAngry = entity.isAngry();
        state.hasNectar = entity.hasNectar();
    }
}
