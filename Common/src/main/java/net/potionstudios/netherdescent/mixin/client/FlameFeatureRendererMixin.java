package net.potionstudios.netherdescent.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.AtlasManager;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.Identifier;
import net.potionstudios.netherdescent.tags.NetherDescentEntityTypeTags;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlameFeatureRenderer.class)
public abstract class FlameFeatureRendererMixin {

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/AtlasManager;get(Lnet/minecraft/client/resources/model/Material;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
	private TextureAtlasSprite wrapSprite0(AtlasManager instance, Material material, Operation<TextureAtlasSprite> original, PoseStack.Pose pose, MultiBufferSource bufferSource, EntityRenderState renderState, Quaternionf rotation, AtlasManager atlasManager) {
		if (renderState.entityType.is(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME))
			return original.call(instance, new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.withDefaultNamespace("block/soul_fire_0")));
		return original.call(instance, material);
	}

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/AtlasManager;get(Lnet/minecraft/client/resources/model/Material;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
	private TextureAtlasSprite wrapSprite1(AtlasManager instance, Material material, Operation<TextureAtlasSprite> original, PoseStack.Pose pose, MultiBufferSource bufferSource, EntityRenderState renderState, Quaternionf rotation, AtlasManager atlasManager) {
		if (renderState.entityType.is(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME))
			return original.call(instance, new Material(TextureAtlas.LOCATION_BLOCKS, Identifier.withDefaultNamespace("block/soul_fire_1")));
		return original.call(instance, material);
	}
}
