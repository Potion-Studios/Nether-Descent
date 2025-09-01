package net.potionstudios.netherdescent.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.potionstudios.netherdescent.NetherDescent;

public interface NetherDescentStructureTags {

	TagKey<Structure> FORTRESSES = create("fortresses");

	private static TagKey<Structure> create(String name) {
		return TagKey.create(Registries.STRUCTURE, NetherDescent.id(name));
	}
}
