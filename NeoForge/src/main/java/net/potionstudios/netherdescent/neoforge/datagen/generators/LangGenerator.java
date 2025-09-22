package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentCreativeTabs;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
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
		NetherDescentBlocks.BLOCKS.forEach(block -> add(block.get(), getBlockName(block)));
		NetherDescentItems.ITEMS.forEach(item -> add(item.get(), getItemName(item)));
		NetherDescentBiomes.BIOME_FACTORIES.forEach((key, factory) -> add("biome." + NetherDescent.MOD_ID + "." + key.location().getPath(), getBiomeName(key)));
        add(death("crimsonBerryBush"), "%1$s was poked to death by a crimson berry bush");
        add(death("crimsonBerryBush.player"), "%1$s was poked to death by a crimson berry bush while trying to escape %2$s");
	}

	private String getBlockName(Supplier<? extends Block> item) {
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
