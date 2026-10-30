package net.potionstudios.netherdescent.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.potionstudios.netherdescent.world.entity.monster.SoulBlaze;
import net.potionstudios.netherdescent.world.entity.projectile.LargeSoulFireball;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

	@Unique
	private Entity netherdescent$currentEntity;

	@Inject(method = "render", at = @At("HEAD"))
	private void netherdescent$captureEntity(Entity entity, double xOffset, double yOffset, double zOffset, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
		this.netherdescent$currentEntity = entity;
	}

	@Inject(method = "render", at = @At("RETURN"))
	private void netherdescent$clearEntity(Entity entity, double xOffset, double yOffset, double zOffset, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
		this.netherdescent$currentEntity = null;
	}

	@Unique
	private boolean netherdescent$isSoulFireEntity() {
		return netherdescent$currentEntity instanceof SmallSoulFireball
				|| netherdescent$currentEntity instanceof LargeSoulFireball
				|| netherdescent$currentEntity instanceof SoulBlaze;
	}

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
	private TextureAtlasSprite wrapSprite0(Material originalMaterial, Operation<TextureAtlasSprite> original) {
		if (netherdescent$isSoulFireEntity())
			return original.call(new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_0")));
		return original.call(originalMaterial);
	}

	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
	private TextureAtlasSprite wrapSprite1(Material originalMaterial, Operation<TextureAtlasSprite> original) {
		if (netherdescent$isSoulFireEntity())
			return original.call(new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_1")));
		return original.call(originalMaterial);
	}
}
