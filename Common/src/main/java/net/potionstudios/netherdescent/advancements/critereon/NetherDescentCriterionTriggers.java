package net.potionstudios.netherdescent.advancements.critereon;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentCriterionTriggers {

	public static final Supplier<WailingTrigger> WAILING_INTERACTION = register("wailing_interaction", WailingTrigger::new);

	private static <T extends CriterionTrigger<?>> Supplier<T> register(String id, Supplier<T> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.TRIGGER_TYPES, id, supplier);
	}

	public static void criterionTriggers() {
		NetherDescent.LOGGER.info("Registering Wayfinder Criterion Triggers");
	}
}
