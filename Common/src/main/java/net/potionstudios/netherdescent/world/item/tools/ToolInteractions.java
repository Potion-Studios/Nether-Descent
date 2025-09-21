package net.potionstudios.netherdescent.world.item.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ToolInteractions {

    public static void registerTillables(BiConsumer<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> consumer) {
        consumer.accept(NetherDescentBlocks.SYTHIAN_SOIL.get(), Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(NetherDescentBlocks.SYTHIAN_FARMLAND.get().defaultBlockState())));
    }
}
