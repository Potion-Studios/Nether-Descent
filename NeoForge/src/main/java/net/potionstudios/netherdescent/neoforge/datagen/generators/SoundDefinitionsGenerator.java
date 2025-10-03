package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.potionstudios.netherdescent.NetherDescent;

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

	}

	private String subtitle(String subtitle) {
		return "subtitles." + subtitle;
	}
}
