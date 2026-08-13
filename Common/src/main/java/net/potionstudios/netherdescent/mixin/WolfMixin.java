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

    @WrapOperation(method = "hasArmor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    private boolean hasArmor(ItemStack stack, Item item, Operation<Boolean> original) {
        return original.call(stack, item) || stack.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get());
    }

    @WrapOperation(method = "actuallyHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getDefaultInstance()Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack actuallyHurt(Item instance, Operation<ItemStack> original) {
        ItemStack armor = ((Wolf)(Object)this).getBodyArmorItem();
        if (armor.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()))
            return armor.getItem().getDefaultInstance();
        return original.call(instance);
    }
}
