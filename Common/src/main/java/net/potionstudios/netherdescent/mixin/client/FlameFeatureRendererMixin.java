package net.potionstudios.netherdescent.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.sprite.AtlasManager;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.potionstudios.netherdescent.tags.NetherDescentEntityTypeTags;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlameFeatureRenderer.class)
public abstract class FlameFeatureRendererMixin {

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
	private TextureAtlasSprite wrapSprite0(AtlasManager instance, SpriteId sprite, Operation<TextureAtlasSprite> original, PoseStack.Pose pose, MultiBufferSource bufferSource, EntityRenderState state, Quaternionf rotation, AtlasManager atlasManager) {
		if (state.entityType.builtInRegistryHolder().is(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME))
			return original.call(instance, Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_0"));
		return original.call(instance, sprite);
	}

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/sprite/AtlasManager;get(Lnet/minecraft/client/resources/model/sprite/SpriteId;)Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
	private TextureAtlasSprite wrapSprite1(AtlasManager instance, SpriteId sprite, Operation<TextureAtlasSprite> original, PoseStack.Pose pose, MultiBufferSource bufferSource, EntityRenderState state, Quaternionf rotation, AtlasManager atlasManager) {
		if (state.entityType.builtInRegistryHolder().is(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME))
			return original.call(instance, Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_1"));
		return original.call(instance, sprite);
	}
}
