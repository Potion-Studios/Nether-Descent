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
import net.potionstudios.netherdescent.advancements.critereon.WailingTrigger;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
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

        AdvancementHolder arisian_undergrowth = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.ARISIAN_UNDERGROWTH))
                .parent(root)
                .display(
                        NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get(),
                        translateAble("arisian_undergrowth.title"),
                        translateAble("arisian_undergrowth.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/arisian_undergrowth"), existingFileHelper);


        AdvancementHolder crimson_gardens = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.CRIMSON_GARDENS))
                .parent(root)
                .display(
                        NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get(),
                        translateAble("crimson_gardens.title"),
                        translateAble("crimson_gardens.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens"), existingFileHelper);

        AdvancementHolder embur_bog = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.EMBUR_BOG))
                .parent(root)
                .display(
                        NetherDescentBlocks.EMBUR_NYLIUM.get(),
                        translateAble("embur_bog.title"),
                        translateAble("embur_bog.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/embur_bog"), existingFileHelper);

        AdvancementHolder sythian_torrids = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.SYTHIAN_TORRIDS))
                .parent(root)
                .display(
                        NetherDescentBlocks.SYTHIAN_NYLIUM.get(),
                        translateAble("sythian_torrids.title"),
                        translateAble("sythian_torrids.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/sythian_torrids"), existingFileHelper);

        AdvancementHolder wailing_garth = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.WAILING_GARTH))
                .parent(root)
                .display(
                        NetherDescentBlocks.WAILING_NYLIUM.get(),
                        translateAble("wailing_garth.title"),
                        translateAble("wailing_garth.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/wailing_garth"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("wailing_bulb_blossom", WailingTrigger.TriggerInstance.interactedWithBlock(NetherDescentBlocks.WAILING_BULB_BLOSSOM.get()))
                .parent(wailing_garth)
                .display(
                        NetherDescentBlocks.WAILING_BULB_BLOSSOM.get(),
                        translateAble("step_on_wailing_bulb_blossom.title"),
                        translateAble("step_on_wailing_bulb_blossom.description"),
                        null,
                        AdvancementType.CHALLENGE, true, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/wailing_garth/step_on_wailing_bulb_blossom"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("wailing_gills", WailingTrigger.TriggerInstance.interactedWithBlock(NetherDescentBlocks.WAILING_GILLS.get()))
                .parent(wailing_garth)
                .display(
                        NetherDescentBlocks.WAILING_GILLS.get(),
                        translateAble("float_from_wailing_gills.title"),
                        translateAble("float_from_wailing_gills.description"),
                        null,
                        AdvancementType.CHALLENGE, true, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/wailing_garth/float_from_wailing_gills"), existingFileHelper);

        VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList())
                .parent(root)
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
