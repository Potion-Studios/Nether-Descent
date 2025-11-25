package net.potionstudios.netherdescent.world.entity.monster;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SoulGhast extends Ghast {
    public SoulGhast(EntityType<? extends Ghast> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 8;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 14.0F).add(Attributes.FOLLOW_RANGE, 100.0F);
    }
}
