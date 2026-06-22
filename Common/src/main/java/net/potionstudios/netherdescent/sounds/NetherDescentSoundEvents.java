package net.potionstudios.netherdescent.sounds;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;

import java.util.function.Supplier;

public class NetherDescentSoundEvents {

	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_ARISIAN_UNDERGROWTH_LOOP = registerSoundEventHolder("ambient.arisian_undergrowth.loop");
	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_ARISIAN_UNDERGROWTH_ADDITIONS = registerSoundEventHolder("ambient.arisian_undergrowth.additions");
	public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_ARISIAN_UNDERGROWTH = registerSoundEventHolder("music.nether.arisian_undergrowth");

	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_EMBUR_BOG_LOOP = registerSoundEventHolder("ambient.embur_bog.loop");
	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_EMBUR_BOG_ADDITIONS = registerSoundEventHolder("ambient.embur_bog.additions");
	public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_EMBUR_BOG = registerSoundEventHolder("music.nether.embur_bog");

	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_CRIMSON_GARDENS_LOOP = registerSoundEventHolder("ambient.crimson_gardens.loop");
	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_CRIMSON_GARDENS_ADDITIONS = registerSoundEventHolder("ambient.crimson_gardens.additions");
	public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_CRIMSON_GARDENS = registerSoundEventHolder("music.nether.crimson_gardens");

	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_SYTHIAN_TORRIDS_LOOP = registerSoundEventHolder("ambient.sythian_torrids.loop");
	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_SYTHIAN_TORRIDS_ADDITIONS = registerSoundEventHolder("ambient.sythian_torrids.additions");
	public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_SYTHIAN_TORRIDS = registerSoundEventHolder("music.nether.sythian_torrids");

	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_WAILING_GARTH_LOOP = registerSoundEventHolder("ambient.wailing_garth.loop");
	public static final Supplier<Holder.Reference<SoundEvent>> AMBIENT_WAILING_GARTH_ADDITIONS = registerSoundEventHolder("ambient.wailing_garth.additions");
	public static final Supplier<Holder.Reference<SoundEvent>> MUSIC_BIOME_WAILING_GARTH = registerSoundEventHolder("music.nether.wailing_garth");


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
		NetherDescent.LOGGER.info("Registering Nether Descent Sound Events");
	}
}
