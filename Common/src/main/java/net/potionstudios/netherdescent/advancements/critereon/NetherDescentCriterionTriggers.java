package net.potionstudios.netherdescent.advancements.critereon;

import net.minecraft.advancements.CriteriaTriggers;
import net.potionstudios.netherdescent.NetherDescent;

public class NetherDescentCriterionTriggers {

	public static final WailingTrigger WAILING_INTERACTION = CriteriaTriggers.register(new WailingTrigger());
	public static final FungalBulbsBlockTrigger FUNGAL_BULBS_BLOCK_HIT = CriteriaTriggers.register(new FungalBulbsBlockTrigger());
	public static final PlaceFlowerNearHornetTrigger PLACE_FLOWER_NEAR_HORNET = CriteriaTriggers.register(new PlaceFlowerNearHornetTrigger());

	public static void criterionTriggers() {
		NetherDescent.LOGGER.info("Registering Wayfinder Criterion Triggers");
	}
}
