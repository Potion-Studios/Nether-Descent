package net.potionstudios.netherdescent.mixin;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin {
	@Inject(method = "canArmorAbsorb", at = @At("HEAD"), cancellable = true)
	private void canArmorAbsorb(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
		Wolf self = (Wolf)(Object) this;
		if (self.getBodyArmorItem().is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()) && !source.is(DamageTypeTags.BYPASSES_WOLF_ARMOR))
			cir.setReturnValue(true);
	}
}
