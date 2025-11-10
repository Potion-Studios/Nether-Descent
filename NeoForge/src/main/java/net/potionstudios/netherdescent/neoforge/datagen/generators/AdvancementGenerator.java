package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider arg, @NotNull Consumer<AdvancementHolder> consumer, @NotNull ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .addCriterion("tick", PlayerTrigger.TriggerInstance.tick())
                .display(
		                NetherDescentBlocks.EMBUR_LILY.get(),
                        translateAble("title.root"),
                        translateAble("description.root"),
                        NetherDescent.id("textures/block/blue_netherrack.png"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/root"), existingFileHelper);

        AdvancementHolder adventureRoot = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList())
                .parent(root)
                .requirements(AdvancementRequirements.Strategy.OR)
                .display(
                        NetherDescentItems.PENDORITE_INGOT.get(),
                        translateAble("adventure.root.title"),
                        translateAble("adventure.root.description"),
                        null,
                        AdvancementType.TASK, false, false, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/adventure/root"), existingFileHelper);

        VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList())
                .parent(adventureRoot)
                .requirements(AdvancementRequirements.Strategy.AND)
                .display(
                        NetherDescentItems.EMBUR_GEL_BALL.get(),
                        translateAble("adventure.final_descent.title"),
                        translateAble("adventure.final_descent.description"),
                        null,
                        AdvancementType.CHALLENGE, true, true, false
                ).rewards(AdvancementRewards.Builder.experience(1000))
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/adventure/final_descent"), existingFileHelper);
    }

    private static MutableComponent translateAble(String key) {
        return Component.translatable( "advancements." + NetherDescent.MOD_ID +"." + key);
    }
}
