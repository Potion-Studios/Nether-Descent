package net.potionstudios.netherdescent.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.advancements.critereon.FungalBulbsBlockTrigger;
import net.potionstudios.netherdescent.advancements.critereon.PlaceFlowerNearHornetTrigger;
import net.potionstudios.netherdescent.advancements.critereon.WailingTrigger;

public class NetherDescentCriteriaTriggers {

	public static final WailingTrigger WAILING_INTERACTION = CriteriaTriggers.register(new WailingTrigger());
	public static final FungalBulbsBlockTrigger FUNGAL_BULBS_BLOCK_HIT = CriteriaTriggers.register(new FungalBulbsBlockTrigger());
	public static final PlaceFlowerNearHornetTrigger PLACE_FLOWER_NEAR_HORNET = CriteriaTriggers.register(new PlaceFlowerNearHornetTrigger());

	public static void criteriaTriggers() {
		NetherDescent.LOGGER.info("Registering Wayfinder Criteria Triggers");
	}
}
