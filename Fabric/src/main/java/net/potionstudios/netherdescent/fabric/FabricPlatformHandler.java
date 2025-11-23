package net.potionstudios.netherdescent.fabric;

import com.google.auto.service.AutoService;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.PlatformHandler;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Supplier;

@AutoService(PlatformHandler.class)
public final class FabricPlatformHandler implements PlatformHandler {
	@Override
	public Platform getPlatform() {
		return Platform.FABRIC;
	}

	@Override
	public Path configPath() {
		return FabricLoader.getInstance().getConfigDir().resolve(NetherDescent.MOD_ID);
	}

    private static final boolean fabricPermissionsApi = FabricLoader.getInstance().isModLoaded("fabric-permissions-api-v0");

    @Override
    public boolean hasPermission(@NotNull CommandSourceStack sourceStack, @NotNull String permission) {
        return PlatformHandler.super.hasPermission(sourceStack, permission) || (fabricPermissionsApi && Permissions.check(sourceStack, permission));
    }

	@Override
	public <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String key, Supplier<BlockEntityType.Builder<T>> builder) {
		ResourceLocation resourceLocation = NetherDescent.id(key);
		BlockEntityType<T> blockEntity = builder.get().build(Util.fetchChoiceType(References.BLOCK_ENTITY, resourceLocation.toString()));
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, resourceLocation, blockEntity);
		return () -> blockEntity;
	}

	@Override
	public WoodType createWoodType(String id, @NotNull BlockSetType setType) {
		return new WoodTypeBuilder().register(NetherDescent.id(id), setType);
	}

	@Override
	public Supplier<SimpleParticleType> registerCreateParticle(String name) {
		SimpleParticleType particleMaker = Registry.register(BuiltInRegistries.PARTICLE_TYPE, NetherDescent.id(name), FabricParticleTypes.simple());
		return () -> particleMaker;
	}

	@SafeVarargs
	@Override
	public final Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
		CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, NetherDescent.id(name), FabricItemGroup.builder()
				.title(Component.translatable("itemGroup." + NetherDescent.MOD_ID + "." + name))
				.icon(icon)
				.displayItems((entry, context) -> {
					for (ArrayList<Supplier<? extends Item>> item : items)
						item.forEach((item1) -> context.accept(item1.get()));
				})
				.build());
		return () -> tab;
	}

	@Override
	public Supplier<PoiType> registerPOIType(String id, Supplier<? extends Block> block, int maxTickets, int validRange) {
		PoiType poi = PointOfInterestHelper.register(NetherDescent.id(id), maxTickets, validRange, block.get());
		return () -> poi;
	}

	@Override
	public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
		T value1 = Registry.register(registry, NetherDescent.id(name), value.get());
		return () -> value1;
	}

	@Override
	public <T> Supplier<Holder.Reference<T>> registerForHolder(Registry<T> registry, String name, Supplier<T> value) {
		Holder.Reference<T> reference = Registry.registerForHolder(registry, NetherDescent.id(name), value.get());
		return () -> reference;
	}
}
