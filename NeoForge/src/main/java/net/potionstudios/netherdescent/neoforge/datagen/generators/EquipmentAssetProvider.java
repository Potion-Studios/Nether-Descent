package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.equipment.NetherDescentEquipmentAssets;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class EquipmentAssetProvider implements DataProvider {
	private final PackOutput.PathProvider pathProvider;

	public EquipmentAssetProvider(PackOutput output) {
		this.pathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "equipment");
	}

	private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
		output.accept(NetherDescentEquipmentAssets.PENDORITE, horseWolf("pendorite"));
	}

	private static EquipmentClientInfo horseWolf(String name) {
		return EquipmentClientInfo.builder()
				.addLayers(EquipmentClientInfo.LayerType.HORSE_BODY, EquipmentClientInfo.Layer.leatherDyeable(NetherDescent.id(name), false))
				.addLayers(EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(NetherDescent.id(name), false))
				.addLayers(
						EquipmentClientInfo.LayerType.WOLF_BODY, EquipmentClientInfo.Layer.onlyIfDyed(NetherDescent.id(name + "_overlay"), true)
				)
				.build();
	}

	@Override
	public @NotNull CompletableFuture<?> run(@NotNull CachedOutput output) {
		Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> map = new HashMap<>();
		bootstrap((arg, arg2) -> {
			if (map.putIfAbsent(arg, arg2) != null) {
				throw new IllegalStateException("Tried to register equipment asset twice for id: " + arg);
			}
		});
		return DataProvider.saveAll(output, EquipmentClientInfo.CODEC, this.pathProvider::json, map);
	}

	@Override
	public @NotNull String getName() {
		return "Equipment Asset Definitions";
	}
}
