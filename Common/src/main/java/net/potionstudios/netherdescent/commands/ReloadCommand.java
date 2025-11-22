package net.potionstudios.netherdescent.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.potionstudios.netherdescent.PlatformHandler;
import net.potionstudios.netherdescent.config.configs.MobSpawnConfig;
import org.jetbrains.annotations.NotNull;

class ReloadCommand {
    static LiteralArgumentBuilder<CommandSourceStack> register() {
        LiteralArgumentBuilder<CommandSourceStack> reload = getCommandSourceStackLiteralArgumentBuilder();

        LiteralArgumentBuilder<CommandSourceStack> reloadSpawn = LiteralArgumentBuilder.literal("spawn");
        reloadSpawn.requires(commandSourceStack -> PlatformHandler.PLATFORM_HANDLER.hasPermission(commandSourceStack, "biomeswevegone.commands.reload"));
        reloadSpawn.executes(context -> {
            MobSpawnConfig.reload();
            context.getSource().sendSuccess(() -> Component.translatable("netherdescent.commands.reload.spawn.success").withStyle(ChatFormatting.GREEN), true);
            return 1;
        });

        return reload;
    }

    private static @NotNull LiteralArgumentBuilder<CommandSourceStack> getCommandSourceStackLiteralArgumentBuilder() {
        LiteralArgumentBuilder<CommandSourceStack> reload = LiteralArgumentBuilder.literal("reload");
        reload.requires(commandSourceStack -> PlatformHandler.PLATFORM_HANDLER.hasPermission(commandSourceStack, "biomeswevegone.commands.reload"));
        reload.executes(context -> {
            MobSpawnConfig.reload();
            context.getSource().sendSuccess(() -> Component.translatable("netherdescent.commands.reload.success").withStyle(ChatFormatting.GREEN), true);
            return 1;
        });
        return reload;
    }
}
