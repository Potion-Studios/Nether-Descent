package net.potionstudios.netherdescent.world.damagesource;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.Map;

/**
 * The damage types for Nether Descent.
 * @see DamageType
 * @author Joseph T. McQuigg
 */
public interface NetherDescentDamageTypes {
    Map<ResourceKey<DamageType>, DamageTypeFactory> DAMAGE_TYPE_FACTORIES = new Reference2ObjectOpenHashMap<>();

    ResourceKey<DamageType> CRIMSON_BERRY_BUSH = register("crimson_berry_bush", (damageTypeBootstapContext) -> new DamageType(NetherDescent.MOD_ID + ".crimsonBerryBush", DamageScaling.ALWAYS, 0.1F, DamageEffects.POKING));

    private static ResourceKey<DamageType> register(String id, DamageTypeFactory factory) {
        ResourceKey<DamageType> key = NetherDescent.key(Registries.DAMAGE_TYPE, id);
        DAMAGE_TYPE_FACTORIES.put(key, factory);
        return key;
    }

    @FunctionalInterface
    interface DamageTypeFactory {
        DamageType generate(BootstapContext<DamageType> damageTypeBootstapContext);
    }
}
