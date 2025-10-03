package net.potionstudios.netherdescent.sounds;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentSounds {

	private static Supplier<SoundEvent> createFixedRangeEvent(String id, float range) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.SOUND_EVENT, id, () -> SoundEvent.createFixedRangeEvent(NetherDescent.id(id), range));
	}

	private static Supplier<SoundEvent> createVariableRangeEvent(String id) {
		return PlatformHandler.PLATFORM_HANDLER.register(BuiltInRegistries.SOUND_EVENT, id, () -> SoundEvent.createVariableRangeEvent(NetherDescent.id(id)));
	}

	private static Supplier<Holder.Reference<SoundEvent>> registerSoundEventHolder(String id) {
		return PlatformHandler.PLATFORM_HANDLER.registerForHolder(BuiltInRegistries.SOUND_EVENT, id, () -> SoundEvent.createVariableRangeEvent(NetherDescent.id(id)));
	}

	public static void sounds() {
		NetherDescent.LOGGER.info("Registering Nether Descent Sounds");
	}
}
