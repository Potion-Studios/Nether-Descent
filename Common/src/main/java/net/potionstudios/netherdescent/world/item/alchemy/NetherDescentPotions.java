package net.potionstudios.netherdescent.world.item.alchemy;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentPotions {

	public static final Supplier<Holder.Reference<Potion>> LEVITATION = register("levitation", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 1800)));
	public static final Supplier<Holder.Reference<Potion>> STRONG_LEVITATION = register("strong_levitation", () -> new Potion(new MobEffectInstance(MobEffects.LEVITATION, 4800)));

	private static Supplier<Holder.Reference<Potion>> register(String name, Supplier<Potion> potion) {
		return PlatformHandler.PLATFORM_HANDLER.registerForHolder(BuiltInRegistries.POTION, name, potion);
	}

	public static void potions() {
		NetherDescent.LOGGER.info("Registering Nether Descent Potions");
	}
}
