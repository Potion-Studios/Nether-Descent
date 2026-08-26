//package net.potionstudios.netherdescent.mixin.client;
//
//import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
//import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
//import com.mojang.blaze3d.vertex.PoseStack;
//import net.minecraft.client.renderer.MultiBufferSource;
//import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
//import net.minecraft.client.renderer.entity.state.EntityRenderState;
//import net.minecraft.client.renderer.texture.TextureAtlas;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.client.resources.model.Material;
//import net.minecraft.resources.ResourceLocation;
//import net.potionstudios.netherdescent.world.entity.monster.SoulBlaze;
//import net.potionstudios.netherdescent.world.entity.projectile.LargeSoulFireball;
//import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;
//import org.joml.Quaternionf;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//
//@Mixin(EntityRenderDispatcher.class)
//public abstract class EntityRenderDispatcherMixin {
//
//	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 0))
//	private TextureAtlasSprite wrapSprite0(Material originalMaterial, Operation<TextureAtlasSprite> original, PoseStack poseStack, MultiBufferSource bufferSource, EntityRenderState renderState, Quaternionf quaternion) {
//		if (renderState instanceof SmallSoulFireball || entity instanceof LargeSoulFireball || entity instanceof SoulBlaze)
//			return original.call(new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_0")));
//		return original.call(originalMaterial);
//	}
//
//	@WrapOperation(method = "renderFlame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/Material;sprite()Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", ordinal = 1))
//	private TextureAtlasSprite wrapSprite1(Material originalMaterial, Operation<TextureAtlasSprite> original, PoseStack poseStack, MultiBufferSource bufferSource, EntityRenderState renderState, Quaternionf quaternion) {
//		if (entity instanceof SmallSoulFireball || entity instanceof LargeSoulFireball || entity instanceof SoulBlaze)
//			return original.call(new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.withDefaultNamespace("block/soul_fire_1")));
//		return original.call(originalMaterial);
//	}
//}
