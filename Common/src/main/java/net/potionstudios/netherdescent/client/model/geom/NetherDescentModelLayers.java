package net.potionstudios.netherdescent.client.model.geom;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.potionstudios.netherdescent.NetherDescent;

public class NetherDescentModelLayers {

    public static final ModelLayerLocation HORNET = register("hornet");

    private static ModelLayerLocation register(String path) {
        return register(path, "main");
    }

    private static ModelLayerLocation register(String path, String model) {
        return new ModelLayerLocation(NetherDescent.id(path), model);
    }
}
