package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.sounds.NetherDescentSounds;

/**
 * Generates the Sounds.json file
 * @see SoundDefinitionsProvider
 * @author Joseph T. McQuigg
 */
public class SoundDefinitionsGenerator extends SoundDefinitionsProvider {
	/**
	 * Creates a new instance of this data provider.
	 *
	 * @param output The {@linkplain PackOutput} instance provided by the data generator.
	 * @param helper The existing file helper provided by the event you are initializing this provider in.
	 */
	public SoundDefinitionsGenerator(PackOutput output, ExistingFileHelper helper) {
		super(output, NetherDescent.MOD_ID, helper);
	}

	@Override
	public void registerSounds() {

		add(NetherDescentSounds.AMBIENT_ARISIAN_UNDERGROWTH_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/arisian_undergrowth/ambience")).stream().volume(0.4)));
		add(NetherDescentSounds.AMBIENT_ARISIAN_UNDERGROWTH_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition1")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition2")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition3")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition4")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition5")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition6")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition7")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition8")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/arisian_undergrowth/addition9")).volume(0.4)
		));

		add(NetherDescentSounds.MUSIC_BIOME_WAILING_GARTH.get().value(), definition().with(sound(NetherDescent.id("music/game/nether/wailing_garth/chimes_of_the_damned")).stream().volume(0.3)));
		add(NetherDescentSounds.AMBIENT_WAILING_GARTH_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/wailing_garth/ambience")).stream().volume(0.4)));
		add(NetherDescentSounds.AMBIENT_WAILING_GARTH_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition1")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition2")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition3")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition4")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition5")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition6")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/wailing_garth/addition7")).volume(0.4)
		));

		add(NetherDescentSounds.MUSIC_BIOME_SYTHIAN_TORRIDS.get().value(), definition().with(sound(NetherDescent.id("music/game/nether/sythian_torrids/glistening_depths")).stream().volume(0.3)));
		add(NetherDescentSounds.AMBIENT_SYTHIAN_TORRIDS_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/sythian_torrids/ambience")).stream().volume(0.2)));
		add(NetherDescentSounds.AMBIENT_SYTHIAN_TORRIDS_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition1")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition2")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition3")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition4")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition5")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition6")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition7")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition8")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/sythian_torrids/addition9")).volume(0.4)
		));

		add(NetherDescentSounds.MUSIC_BIOME_CRIMSON_GARDENS.get().value(), definition().with(sound(NetherDescent.id("music/game/nether/crimson_gardens/strings_of_fate")).stream().volume(0.3)));
		add(NetherDescentSounds.AMBIENT_CRIMSON_GARDENS_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/crimson_gardens/ambience")).stream().volume(0.2)));
		add(NetherDescentSounds.AMBIENT_CRIMSON_GARDENS_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition1")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition2")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition3")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition4")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition5")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition6")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition7")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/crimson_gardens/addition8")).volume(0.4)
				));

		add(NetherDescentSounds.MUSIC_BIOME_EMBUR_BOG.get().value(), definition().with(sound(NetherDescent.id("music/game/nether/embur_bog/haunting_adrenaline")).stream().volume(0.3)));
		add(NetherDescentSounds.AMBIENT_EMBUR_BOG_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/embur_bog/ambience")).stream().volume(0.15)));
		add(NetherDescentSounds.AMBIENT_EMBUR_BOG_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/embur_bog/addition1")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition2")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition3")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition4")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition5")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition6")).volume(0.4),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition7")).volume(0.4)
		));
	}

	private String subtitle(String subtitle) {
		return "subtitles." + subtitle;
	}
}
