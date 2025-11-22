package net.potionstudios.netherdescent.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.potionstudios.netherdescent.NetherDescent;

import java.util.function.Consumer;

public class NetherDescentCommands {
    public static void register(Consumer<LiteralArgumentBuilder<CommandSourceStack>> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> base = LiteralArgumentBuilder.literal(NetherDescent.MOD_ID);
        LiteralArgumentBuilder<CommandSourceStack> nd = LiteralArgumentBuilder.literal("nd");
        base.then(ReloadCommand.register());
        nd.then(ReloadCommand.register());
        dispatcher.accept(base);
        dispatcher.accept(nd);
    }
}
