package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.item.NetherDescentCreativeTabs;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.block.plants.NDGrowingPlantBodyBlock;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LangGenerator extends LanguageProvider {
	public LangGenerator(PackOutput output, String locale) {
		super(output, NetherDescent.MOD_ID, locale);
	}

	@Override
	protected void addTranslations() {
		add("itemGroup." + NetherDescentCreativeTabs.CREATIVE_TAB.location().toLanguageKey(), "Nether Descent");
		NetherDescentBlocks.BLOCKS.forEach(block -> addBlock(block, getBlockName(block)));
		NetherDescentItems.ITEMS.forEach(item -> addItem(item, getItemName(item)));
		NetherDescentBiomes.BIOME_FACTORIES.forEach((key, factory) -> add("biome." + NetherDescent.MOD_ID + "." + key.location().getPath(), getBiomeName(key)));
        add(NetherDescentEntityType.SOUL_BLAZE.get(), "Soul Blaze");
		add(NetherDescentEntityType.SOUL_FIREBALL.get(), "Soul Fireball");
        add(NetherDescentEntityType.SMALL_SOUL_FIREBALL.get(), "Small Soul Fireball");
		add(NetherDescentEntityType.PENDORITE_BLAZE.get(), "Pendorite Blaze");
        add(NetherDescentEntityType.HORNET.get(), "Hornet");
        add(NetherDescentEntityType.SOUL_GHAST.get(),  "Soul Ghast");
        add(death("crimsonBerryBush"), "%1$s was poked to death by a crimson berry bush");
        add(death("crimsonBerryBush.player"), "%1$s was poked to death by a crimson berry bush while trying to escape %2$s");
        add(advancement("title.root"), "Nether Descent");
        add(advancement("description.root"), "Launch a world with the Nether Descent");
        add(advancement("arisian_undergrowth.title"), "Arisian Undergrowth");
		add(advancement("arisian_undergrowth.description"), "Discover the Arisian Undergrowth biome");
		add(advancement("step_on_torn_sprout.title"), "Bridge to Somewhere?");
		add(advancement("step_on_torn_sprout.description"), "Step on a Thorn Sprout");
		add(advancement("step_on_arisian_leaves_blossom.title"), "So it Glows");
		add(advancement("step_on_arisian_leaves_blossom.description"), "Step on Arisian Leaves or Arisian Bloom");
		add(advancement("crimson_gardens.title"), "Crimson Gardens");
		add(advancement("crimson_gardens.description"), "Discover the Crimson Gardens biome");
		add(advancement("fungal_bulbs.title"), "Explosive Fun");
		add(advancement("fungal_bulbs.description"), "Shoot a projectile at Fungal Bulbs");
		add(advancement("raw_pendorite.title"), "Project Pendorite");
		add(advancement("raw_pendorite.description"), "Obtain Raw Pendorite");
		add(advancement("pendorite_wolf_armor.title"), "Flashy Protection");
		add(advancement("pendorite_wolf_armor.description"), "Obtain Pendorite Wolf Armor");
		add(advancement("pendorite_horse_armor.title"), "Stylish Steed");
		add(advancement("pendorite_horse_armor.description"), "Obtain Pendorite Horse Armor");
		add(advancement("pendorite_fire_rod.title"), "The Light is Coming..");
		add(advancement("pendorite_fire_rod.description"), "Obtain Pendorite Fire Rod");
		add(advancement("summon_pendorite_blaze.title"), "Extra Protection");
		add(advancement("summon_pendorite_blaze.description"), "Summon a Pendorite Blaze Golem");
		add(advancement("sythian_torrids.title"), "Sythian Torrids");
		add(advancement("sythian_torrids.description"), "Discover the Sythian Torrids biome");
		add(advancement("smelted_sythian_stalk.title"), "Renewable Business");
		add(advancement("smelted_sythian_stalk.description"), "Smelt Sythian Stalk in a Furnace");
		add(advancement("place_stalk_on_farmland.title"), "Money Moves");
		add(advancement("place_stalk_on_farmland.description"), "Place Sythian Stalk on Sythian Farmland ");
		add(advancement("obtain_sythian_scaffolding.title"), "Modern Construction");
		add(advancement("obtain_sythian_scaffolding.description"), "Obtain Sythian Scaffolding");
		add(advancement("wailing_garth.title"), "Wailing Garth");
		add(advancement("wailing_garth.description"), "Discover the Wailing Garth biome");
		add(advancement("step_on_wailing_bulb_blossom.title"), "Space Walking");
		add(advancement("step_on_wailing_bulb_blossom.description"), "Step on a Wailing Bulb Blossom");
		add(advancement("float_from_wailing_gills.title"), "Natural Elevators");
		add(advancement("float_from_wailing_gills.description"), "Be pulled off the ground by a Wailing Gill Block");
		add(advancement("float_cow_from_wailing_gillis.title"), "UF-WOAH");
		add(advancement("float_cow_from_wailing_gillis.description"), "Lead a Cow underneath a Powered Wailing Gill Block");
		add(advancement("kill_soul_ghast.title"), "Double-Whammy");
		add(advancement("kill_soul_ghast.description"), "Kill a Soul Ghast");
		add(advancement("embur_bog.title"), "Embur Bog");
		add(advancement("embur_bog.description"), "Discover the Embur Bog biome");
		add(advancement("sprinting_on_embur_gel.title"), "Zooooooom");
		add(advancement("sprinting_on_embur_gel.description"), "Sprint across Embur Gel Blocks");
		add(advancement("boat_ride_on_embur_gel.title"), "Grand Theft Auto");
		add(advancement("boat_ride_on_embur_gel.description"), "Ride over Embur Gel Blocks while in a Boat");
		add(advancement("kill_hornet.title"), "The Sting that Keeps on Stinging");
		add(advancement("kill_hornet.description"), "Kill a Hornet");
		add(advancement("place_flower_near_hornet.title"), "That's the Stuff..");
		add(advancement("place_flower_near_hornet.description"), "Place down any flower near Hornets");
		add(advancement("find_blue_fortress.title"), "A jolly Blue Fortress");
		add(advancement("find_blue_fortress.description"), "Discover a Blue Nether Fortress");
		add(advancement("obtain_soul_blaze_rod.title"), "I'm Blue Da-Ba-Bee");
		add(advancement("obtain_soul_blaze_rod.description"), "Obtain a Soul Blaze Rod");
		add(advancement("obtain_soul_fire_rod.title"), "Rocking the Rods");
		add(advancement("obtain_soul_fire_rod.description"), "Obtain a Soul Fire Rod");
        add(advancement("final_descent.title"), "Final Descent");
        add(advancement("final_descent.description"), "Explore all of the Nether Descent biomes");
        add("netherdescent.commands.reload.success", "Successfully reloaded all configs");
        add("netherdescent.commands.reload.spawn.success", "Successfully reloaded Mob Spawn config");
	}

    private static String advancement(String key) {
        return "advancements." + NetherDescent.MOD_ID + "." + key;
    }

	private String getBlockName(Supplier<? extends Block> item) {
        if (item.get() instanceof NDGrowingPlantBodyBlock block) return getBlockName(block::getHeadBlock);
		return getId((BuiltInRegistries.BLOCK.getKey(item.get()).getPath()));
	}

	private String getItemName(Supplier<? extends ItemLike> item) {
		return getId(BuiltInRegistries.ITEM.getKey(item.get().asItem()).getPath());
	}

	private String getBiomeName(ResourceKey<Biome> biome) {
		return getId(biome.location().getPath());
	}

    private static String death(String key) {
        return "death.attack." + NetherDescent.MOD_ID + "." + key;
    }

	@NotNull
	private String getId(String name) {
		name = name.substring(name.indexOf(":") + 1);  //Removes Mod Tag from front of name
		name = name.replace('_', ' ');
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		for (int i = 0; i < name.length(); i++)
			if (name.charAt(i) == ' ')
				name = name.substring(0, i + 1) + name.substring(i + 1, i + 2).toUpperCase() + name.substring(i + 2);
		return name;
	}
}
