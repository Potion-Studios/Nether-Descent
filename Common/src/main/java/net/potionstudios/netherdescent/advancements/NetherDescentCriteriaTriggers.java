package net.potionstudios.netherdescent.advancements;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.advancements.criterion.FungalBulbsBlockTrigger;
import net.potionstudios.netherdescent.advancements.criterion.PlaceFlowerNearHornetTrigger;
import net.potionstudios.netherdescent.advancements.criterion.WailingTrigger;

import java.util.function.Supplier;

public class NetherDescentCriteriaTriggers {

	public static final Supplier<WailingTrigger> WAILING_INTERACTION = register("wailing_interaction", WailingTrigger::new);
	public static final Supplier<FungalBulbsBlockTrigger> FUNGAL_BULBS_BLOCK_HIT = register("fungal_bulbs_hit", FungalBulbsBlockTrigger::new);
	public static final Supplier<PlaceFlowerNearHornetTrigger> PLACE_FLOWER_NEAR_HORNET = register("place_flower_near_hornet", PlaceFlowerNearHornetTrigger::new);

	private static <T extends CriterionTrigger<?>> Supplier<T> register(String id, Supplier<T> supplier) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.TRIGGER_TYPES, id, supplier);
	}

	public static void criteriaTriggers() {
		NetherDescent.LOGGER.info("Registering Wayfinder Criteria Triggers");
	}
}
