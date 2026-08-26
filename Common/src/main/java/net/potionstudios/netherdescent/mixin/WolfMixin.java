package net.potionstudios.netherdescent.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Wolf.class)
public abstract class WolfMixin {
    @WrapOperation(method = "canArmorAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean allowCustomArmorAbsorption(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get());
    }
}
