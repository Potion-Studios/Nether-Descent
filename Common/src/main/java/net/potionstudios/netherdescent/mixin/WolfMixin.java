package net.potionstudios.netherdescent.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Wolf.class)
public abstract class WolfMixin {

    @Inject(method = "hasArmor", at = @At("RETURN"), cancellable = true)
    public void hasArmor(CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = ((Wolf)(Object)this).getBodyArmorItem();
        cir.setReturnValue(stack.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()));
    }

    @WrapOperation(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getDefaultInstance()Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack actuallyHurt(Item instance, Operation<ItemStack> original) {
        ItemStack armor = ((Wolf)(Object)this).getBodyArmorItem();
        if (armor.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get())) {
            return armor.getItem().getDefaultInstance();
        }
        return original.call(instance);
    }
}
