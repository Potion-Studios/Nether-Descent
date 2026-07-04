package net.potionstudios.netherdescent.world.entity.animal;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.potionstudios.netherdescent.world.item.equipment.NetherDescentArmorMaterials;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import org.jetbrains.annotations.Nullable;

public class NetherDescentWolf {
    public static @Nullable InteractionResult onEntityInteract(Level level, Player player, Entity entity, ItemStack stack) {
        if (!level.isClientSide() && entity instanceof Wolf wolf && wolf.isOwnedBy(player)) {
            if (stack.is(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()) && wolf.isTame() && wolf.getBodyArmorItem().isEmpty() && !wolf.isBaby()) {
                wolf.setBodyArmorItem(stack.copyWithCount(1));
                stack.consume(1, player);
                return InteractionResult.SUCCESS;
            } else if (NetherDescentArmorMaterials.PENDORITE.get().value().repairIngredient().get().test(stack) && wolf.isInSittingPose() && wolf.hasArmor() && wolf.getBodyArmorItem().isDamaged()) {
                stack.shrink(1);
                wolf.playSound(SoundEvents.WOLF_ARMOR_REPAIR);
                ItemStack itemStack = wolf.getBodyArmorItem();
                int i = (int) (itemStack.getMaxDamage() * 0.125F);
                itemStack.setDamageValue(Math.max(0, itemStack.getDamageValue() - i));
                return InteractionResult.SUCCESS;
            }
        }
        return null;
    }
}
