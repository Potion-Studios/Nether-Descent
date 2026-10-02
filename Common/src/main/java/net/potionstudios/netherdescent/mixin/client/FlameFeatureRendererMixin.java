package net.potionstudios.netherdescent.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.feature.FeatureFrameContext;
import net.minecraft.client.renderer.feature.FlameFeatureRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.potionstudios.netherdescent.tags.NetherDescentEntityTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(FlameFeatureRenderer.class)
public abstract class FlameFeatureRendererMixin {
    @WrapOperation(method = "buildGroup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/feature/FlameFeatureRenderer;prepare(Lnet/minecraft/client/renderer/feature/FlameFeatureRenderer$Submit;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"))
    private void netherdescent$soulFireSprites(FlameFeatureRenderer instance, FlameFeatureRenderer.Submit submit, VertexConsumer buffer, TextureAtlasSprite fire1, TextureAtlasSprite fire2, Operation<Void> original, FeatureFrameContext context, List<FlameFeatureRenderer.Submit> submits) {
        if (submit.entityRenderState().entityType.builtInRegistryHolder().is(NetherDescentEntityTypeTags.SOUL_FIRE_FLAME)) {
            fire1 = context.atlasManager().get(Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_0"));
            fire2 = context.atlasManager().get(Sheets.BLOCKS_MAPPER.defaultNamespaceApply("soul_fire_1"));
        }
        original.call(instance, submit, buffer, fire1, fire2);
    }
}