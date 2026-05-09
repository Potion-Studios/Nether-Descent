package net.potionstudios.netherdescent.world.item.custom;

import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.FireChargeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.potionstudios.netherdescent.world.entity.projectile.SmallSoulFireball;
import org.jetbrains.annotations.NotNull;

public class SoulFireChargeItem extends FireChargeItem {
    public SoulFireChargeItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Projectile asProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack, @NotNull Direction direction) {
        RandomSource randomSource = level.getRandom();
        double d = randomSource.triangle(direction.getStepX(), 0.11485000000000001);
        double e = randomSource.triangle(direction.getStepY(), 0.11485000000000001);
        double f = randomSource.triangle(direction.getStepZ(), 0.11485000000000001);
        Vec3 vec3 = new Vec3(d, e, f);
        SmallSoulFireball smallFireball = new SmallSoulFireball(pos.x(), pos.y(), pos.z(), vec3.normalize(), level);
        smallFireball.setItem(stack);
        return smallFireball;
    }
}
