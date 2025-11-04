package net.potionstudios.netherdescent.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NaturalSpawner.class)
public abstract class NaturalSpawnerMixin {

    @Inject(method = "isInNetherFortressBounds", at = @At("RETURN"), cancellable = true)
    private static void isInNetherFortressBounds(BlockPos pos, ServerLevel level, MobCategory category, StructureManager structureManager, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && category == MobCategory.MONSTER && level.getBlockState(pos.below()).is(NetherDescentBlocks.BLUE_NETHERRACK.get())) {
            Structure blueFortress = structureManager.registryAccess().registryOrThrow(Registries.STRUCTURE).get(NetherDescentStructures.BLUE_FORTRESS);
            if (blueFortress != null && structureManager.getStructureAt(pos, blueFortress).isValid())
                cir.setReturnValue(true);
        }
    }

}
