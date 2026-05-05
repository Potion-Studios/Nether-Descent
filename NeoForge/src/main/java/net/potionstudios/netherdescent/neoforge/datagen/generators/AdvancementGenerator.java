package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.advancements.critereon.FungalBulbsBlockTrigger;
import net.potionstudios.netherdescent.advancements.critereon.NetherDescentCriterionTriggers;
import net.potionstudios.netherdescent.advancements.critereon.WailingTrigger;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityType;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
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

        Advancement.Builder.advancement()
                .addCriterion("projectile_hit_fungal_bulbs", FungalBulbsBlockTrigger.TriggerInstance.fungalBulbsHit(Optional.empty()))
                .parent(crimson_gardens)
                .display(
                        NetherDescentBlocks.FUNGAL_BULBS.get(),
                        translateAble("fungal_bulbs.title"),
                        translateAble("fungal_bulbs.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/fungal_bulbs"), existingFileHelper);

        AdvancementHolder project_pendorite = Advancement.Builder.advancement()
                .addCriterion("has_raw_pendorite", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.RAW_PENDORITE.get()))
                .parent(crimson_gardens)
                .display(
                        NetherDescentItems.RAW_PENDORITE.get(),
                        translateAble("raw_pendorite.title"),
                        translateAble("raw_pendorite.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/pendorite_ingot"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("has_pendorite_wolf_armor", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()))
                .parent(project_pendorite)
                .display(
                        NetherDescentItems.PENDORITE_WOLF_ARMOR.get(),
                        translateAble("pendorite_wolf_armor.title"),
                        translateAble("pendorite_wolf_armor.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/project_pendorite/pendorite_wolf_armor"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("has_pendorite_horse_armor", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()))
                .parent(project_pendorite)
                .display(
                        NetherDescentItems.PENDORITE_HORSE_ARMOR.get(),
                        translateAble("pendorite_horse_armor.title"),
                        translateAble("pendorite_horse_armor.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/project_pendorite/pendorite_horse_armor"), existingFileHelper);

        AdvancementHolder pendorite_fire_rod = Advancement.Builder.advancement()
                .addCriterion("has_pendorite_fire_rod", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentBlocks.PENDORITE_FIRE_ROD.get()))
                .parent(project_pendorite)
                .display(
                        NetherDescentBlocks.PENDORITE_FIRE_ROD.get(),
                        translateAble("pendorite_fire_rod.title"),
                        translateAble("pendorite_fire_rod.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/project_pendorite/pendorite_fire_rod"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("summon_pendorite_blaze", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(NetherDescentEntityType.PENDORITE_BLAZE.get()))))
                .parent(pendorite_fire_rod)
                .display(
                        NetherDescentItems.PENDORITE_FIRE_CHARGE.get(),
                        translateAble("summon_pendorite_blaze.title"),
                        translateAble("summon_pendorite_blaze.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                )
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/crimson_gardens/project_pendorite/pendorite_fire_rod/summon_pendorite_blaze"), existingFileHelper);

        AdvancementHolder embur_bog = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, List.of(NetherDescentBiomes.EMBUR_BOG))
                .parent(root)
                .display(
                        NetherDescentBlocks.EMBUR_NYLIUM.get(),
                        translateAble("embur_bog.title"),
                        translateAble("embur_bog.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/embur_bog"), existingFileHelper);

        AdvancementHolder killHornet = Advancement.Builder.advancement()
                .addCriterion("kill_hornet", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(NetherDescentEntityType.HORNET.get())))
                .parent(embur_bog)
                .display(
                        NetherDescentBlocks.HORNET_NEST.get(),
                        translateAble("kill_hornet.title"),
                        translateAble("kill_hornet.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/embur_bog/kill_hornet"), existingFileHelper);

        Advancement.Builder.advancement()
                .addCriterion("blue_fortress", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(arg.lookupOrThrow(Registries.STRUCTURE).getOrThrow(NetherDescentStructures.BLUE_FORTRESS))))
                .parent(embur_bog)
                .display(
                        NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase(),
                        translateAble("find_blue_fortress.title"),
                        translateAble("find_blue_fortress.description"),
                        null,
                        AdvancementType.TASK, false, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/embur_bog/find_blue_fortress"), existingFileHelper);

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

        Advancement.Builder.advancement()
                .addCriterion("kill_soul_ghast", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(NetherDescentEntityType.SOUL_GHAST.get())))
                .parent(wailing_garth)
                .display(
                        NetherDescentItems.SOUL_FIRE_CHARGE.get(),
                        translateAble("kill_soul_ghast.title"),
                        translateAble("kill_soul_ghast.description"),
                        null,
                        AdvancementType.CHALLENGE, true, true, false
                ).save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/wailing_garth/kill_soul_ghast"), existingFileHelper);

        VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), arg, NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList())
                .parent(root)
                .requirements(AdvancementRequirements.Strategy.AND)
                .display(
                        NetherDescentItems.EMBUR_GEL_BALL.get(),
                        translateAble("final_descent.title"),
                        translateAble("final_descent.description"),
                        null,
                        AdvancementType.CHALLENGE, true, true, false
                ).rewards(AdvancementRewards.Builder.experience(1000))
                .save(consumer, NetherDescent.id(NetherDescent.MOD_ID + "/final_descent"), existingFileHelper);
    }

    private static MutableComponent translateAble(String key) {
        return Component.translatable( "advancements." + NetherDescent.MOD_ID +"." + key);
    }
}
