package net.potionstudios.netherdescent.client.renderer.entity.nertling;

import net.minecraft.resources.ResourceLocation;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.entity.npc.Nertling;
import software.bernie.geckolib.model.GeoModel;

class NertlingModel<T extends Nertling> extends GeoModel<T> {
    @Override
    public ResourceLocation getModelResource(T animatable) {
        return NetherDescent.id("geo/nertling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return NetherDescent.id("textures/entity/nertling/" + animatable.getVariant().getName() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return NetherDescent.id("animations/nertling.animation.json");
    }
}
