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
		add(NetherDescentSounds.MUSIC_BIOME_EMBUR_BOG.get().value(), definition().with(sound(NetherDescent.id("music/game/nether/embur_bog/haunting_adrenaline")).stream().volume(0.3)));
		add(NetherDescentSounds.AMBIENT_EMBUR_BOG_LOOP.get().value(), definition().with(sound(NetherDescent.id("ambient/nether/embur_bog/ambience")).stream().volume(0.1)));
		add(NetherDescentSounds.AMBIENT_EMBUR_BOG_ADDITIONS.get().value(), definition().with(
				sound(NetherDescent.id("ambient/nether/embur_bog/addition1")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition2")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition3")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition4")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition5")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition6")).volume(0.1),
				sound(NetherDescent.id("ambient/nether/embur_bog/addition7")).volume(0.1)
		));
	}

	private String subtitle(String subtitle) {
		return "subtitles." + subtitle;
	}
}
