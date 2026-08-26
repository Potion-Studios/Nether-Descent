package net.potionstudios.netherdescent.event;

import com.terraformersmc.biolith.impl.Biolith;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.CommonColors;
import net.potionstudios.netherdescent.PlatformHandler;
import terrablender.core.TerraBlender;

public class ServerEventsHandler {
	public static void onPlayerJoin(ServerPlayer serverPlayer) {
		if (!PlatformHandler.PLATFORM_HANDLER.isModLoaded(TerraBlender.MOD_ID) && !PlatformHandler.PLATFORM_HANDLER.isModLoaded(Biolith.MOD_ID))
			if (serverPlayer.getServer().isSingleplayer() || serverPlayer.hasPermissions(2))
				serverPlayer.sendSystemMessage(Component.literal("TerraBlender or Biolith are not loaded, Nether Descent's biomes will not be added to the Nether!").withColor(CommonColors.RED));
	}
}
