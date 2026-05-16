package net.potionstudios.netherdescent.event;

import com.terraformersmc.biolith.impl.Biolith;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.config.configs.DevConfig;
import terrablender.core.TerraBlender;

public class ServerEventsHandler {
	public static void onPlayerJoin(ServerPlayer serverPlayer) {
		if (!PlatformHandler.PLATFORM_HANDLER.isModLoaded(TerraBlender.MOD_ID) && !PlatformHandler.PLATFORM_HANDLER.isModLoaded(Biolith.MOD_ID))
			if (serverPlayer.getServer().isSingleplayer() || serverPlayer.hasPermissions(2))
				serverPlayer.sendSystemMessage(Component.literal("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the Nether!").withColor(CommonColors.RED));

		if (PlatformHandler.PLATFORM_HANDLER.isDevEnvironment())
			if (serverPlayer.level().dimension() != Level.NETHER) {
				DevConfig devConfig = DevConfig.getInstance(true);
				if (devConfig.startInNether()) {
					serverPlayer.setGameMode(GameType.SPECTATOR);
					serverPlayer.teleportTo(serverPlayer.getServer().getLevel(Level.NETHER), serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
				}
			}
	}
}
