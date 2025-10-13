package net.potionstudios.netherdescent.world.level.block.state.properties;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class NetherDescentBlockSetTypes {

	public static final BlockSetType PENDORITE = register(new BlockSetType("pendorite", true, true, false, BlockSetType.PressurePlateSensitivity.EVERYTHING, SoundType.COPPER, SoundEvents.COPPER_DOOR_CLOSE, SoundEvents.COPPER_DOOR_OPEN, SoundEvents.COPPER_TRAPDOOR_CLOSE, SoundEvents.COPPER_TRAPDOOR_OPEN, SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF, SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON, SoundEvents.STONE_BUTTON_CLICK_OFF, SoundEvents.STONE_BUTTON_CLICK_ON));

	private static BlockSetType register(BlockSetType type) {
		return BlockSetType.register(type);
	}
}
