package net.potionstudios.netherdescent.world.level.block.state.properties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class NetherDescentBlockSetTypes {

	public static final BlockSetType PENDORITE = register(new BlockSetType("pendorite", true, SoundType.COPPER, SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));

	private static BlockSetType register(BlockSetType type) {
		return BlockSetType.register(type);
	}
}
