package net.potionstudios.netherdescent.neoforge.datagen.generators;

import net.minecraft.advancements.*;
import net.minecraft.advancements.predicates.BlockPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.predicates.LocationPredicate;
import net.minecraft.advancements.predicates.MinMaxBounds;
import net.minecraft.advancements.predicates.entity.EntityFlagsPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.advancements.triggers.*;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.potionstudios.netherdescent.NetherDescent;
import net.potionstudios.netherdescent.advancements.critereon.FungalBulbsBlockTrigger;
import net.potionstudios.netherdescent.advancements.critereon.PlaceFlowerNearHornetTrigger;
import net.potionstudios.netherdescent.advancements.critereon.WailingTrigger;
import net.potionstudios.netherdescent.data.worldgen.NetherDescentStructures;
import net.potionstudios.netherdescent.world.entity.NetherDescentEntityTypes;
import net.potionstudios.netherdescent.world.item.NetherDescentItems;
import net.potionstudios.netherdescent.world.level.block.NetherDescentBlocks;
import net.potionstudios.netherdescent.world.level.levelgen.biome.NetherDescentBiomes;

import java.util.List;
import java.util.Optional;

public class AdvancementGenerator extends AdvancementProvider {
    public AdvancementGenerator() {
        super(List.of(NetherDescentAdvancements::new));
    }

    private static class NetherDescentAdvancements extends AdvancementSubProvider {
        protected NetherDescentAdvancements(BootstrapContext<Advancement> output) {
            super(output);
        }

        @Override
        public void generate() {
            HolderGetter<EntityType<?>> entityTypeHolderGetter = output.lookup(Registries.ENTITY_TYPE);
            HolderGetter<Item> itemHolderGetter = output.lookup(Registries.ITEM);
            HolderGetter<Block> blockHolderGetter = output.lookup(Registries.BLOCK);
            HolderGetter<Biome> biomeHolderGetter = output.lookup(Registries.BIOME);
            AdvancementHolder root = Advancement.Builder.advancement()
                    .addCriterion("tick", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inDimension((Level.NETHER))))
                    .rootDisplay(
                            NetherDescentBlocks.EMBUR_LILY.get().asItem(),
                            translateAble("title.root"),
                            translateAble("description.root"),
                            NetherDescent.id("textures/block/blue_netherrack.png"),
                            AdvancementType.TASK,
                            false,
                            false,
                            false
                    )
                    .save(output, NetherDescent.id("root"));

            AdvancementHolder arisian_undergrowth = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, List.of(NetherDescentBiomes.ARISIAN_UNDERGROWTH))
                    .parent(root)
                    .display(
                            NetherDescentBlocks.ARISIAN_MOSS_BLOCK.get().asItem(),
                            translateAble("arisian_undergrowth.title"),
                            translateAble("arisian_undergrowth.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("arisian_undergrowth/root"));

            Advancement.Builder.advancement()
                    .parent(arisian_undergrowth)
                    .addCriterion("step_on_thorn_sprout", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.THORN_SPROUT.get()))))
                    .display(
                            NetherDescentBlocks.THORN_SPROUT.get().asItem(),
                            translateAble("step_on_thorn_sprout.title"),
                            translateAble("step_on_thorn_sprout.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("arisian_undergrowth/step_on_thorn_sprout"));

            Advancement.Builder.advancement()
                    .parent(arisian_undergrowth)
                    .requirements(AdvancementRequirements.Strategy.OR)
                    .addCriterion("step_on_arisian_blossom", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity().steppingOn(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.ARISIAN_LEAVES.get())))))
                    .addCriterion("step_on_arisian_leaves", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.ARISIAN_BLOSSOM.get()))))
                    .display(
                            NetherDescentBlocks.ARISIAN_LEAVES.get().asItem(),
                            translateAble("step_on_arisian_leaves_blossom.title"),
                            translateAble("step_on_arisian_leaves_blossom.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("arisian_undergrowth/step_on_arisian_leaves_blossom"));

            AdvancementHolder crimson_gardens = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, List.of(NetherDescentBiomes.CRIMSON_GARDENS))
                    .parent(root)
                    .display(
                            NetherDescentBlocks.CRIMSON_BLACKSTONE_NYLIUM.get().asItem(),
                            translateAble("crimson_gardens.title"),
                            translateAble("crimson_gardens.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("crimson_gardens/root"));

            Advancement.Builder.advancement()
                    .addCriterion("projectile_hit_fungal_bulbs", FungalBulbsBlockTrigger.TriggerInstance.fungalBulbsHit(Optional.empty()))
                    .parent(crimson_gardens)
                    .display(
                            NetherDescentBlocks.FUNGAL_BULBS.get().asItem(),
                            translateAble("fungal_bulbs.title"),
                            translateAble("fungal_bulbs.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("crimson_gardens/fungal_bulbs"));

            AdvancementHolder project_pendorite = Advancement.Builder.advancement()
                    .addCriterion("has_raw_pendorite", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.RAW_PENDORITE.get()))
                    .parent(crimson_gardens)
                    .display(
                            NetherDescentItems.RAW_PENDORITE.get(),
                            translateAble("raw_pendorite.title"),
                            translateAble("raw_pendorite.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("crimson_gardens/raw_pendorite"));

            Advancement.Builder.advancement()
                    .addCriterion("has_pendorite_wolf_armor", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.PENDORITE_WOLF_ARMOR.get()))
                    .parent(project_pendorite)
                    .display(
                            NetherDescentItems.PENDORITE_WOLF_ARMOR.get(),
                            translateAble("pendorite_wolf_armor.title"),
                            translateAble("pendorite_wolf_armor.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("crimson_gardens/pendorite_wolf_armor"));

            Advancement.Builder.advancement()
                    .addCriterion("has_pendorite_horse_armor", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.PENDORITE_HORSE_ARMOR.get()))
                    .parent(project_pendorite)
                    .display(
                            NetherDescentItems.PENDORITE_HORSE_ARMOR.get(),
                            translateAble("pendorite_horse_armor.title"),
                            translateAble("pendorite_horse_armor.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("crimson_gardens/pendorite_horse_armor"));

            AdvancementHolder pendorite_fire_rod = Advancement.Builder.advancement()
                    .addCriterion("has_pendorite_fire_rod", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentBlocks.PENDORITE_FIRE_ROD.get()))
                    .parent(project_pendorite)
                    .display(
                            NetherDescentBlocks.PENDORITE_FIRE_ROD.get().asItem(),
                            translateAble("pendorite_fire_rod.title"),
                            translateAble("pendorite_fire_rod.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("crimson_gardens/pendorite_fire_rod"));

            Advancement.Builder.advancement()
                    .addCriterion("summon_pendorite_blaze", SummonedEntityTrigger.TriggerInstance.summonedEntity(EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypeHolderGetter, NetherDescentEntityTypes.PENDORITE_BLAZE.get()))))
                    .parent(pendorite_fire_rod)
                    .display(
                            NetherDescentItems.PENDORITE_FIRE_CHARGE.get(),
                            translateAble("summon_pendorite_blaze.title"),
                            translateAble("summon_pendorite_blaze.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("crimson_gardens/summon_pendorite_blaze"));

            AdvancementHolder embur_bog = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, List.of(NetherDescentBiomes.EMBUR_BOG))
                    .parent(root)
                    .display(
                            NetherDescentBlocks.EMBUR_NYLIUM.get().asItem(),
                            translateAble("embur_bog.title"),
                            translateAble("embur_bog.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/root"));

            AdvancementHolder killHornet = Advancement.Builder.advancement()
                    .addCriterion("kill_hornet", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypeHolderGetter, NetherDescentEntityTypes.HORNET.get())))
                    .parent(embur_bog)
                    .display(
                            NetherDescentItems.HORNET_NEST.get(),
                            translateAble("kill_hornet.title"),
                            translateAble("kill_hornet.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/kill_hornet"));

            Advancement.Builder.advancement()
                    .addCriterion("place_flower_near_hornet", PlaceFlowerNearHornetTrigger.TriggerInstance.create())
                    .parent(killHornet)
                    .display(
                            NetherDescentBlocks.EMBUR_LILY.get().asItem(),
                            translateAble("place_flower_near_hornet.title"),
                            translateAble("place_flower_near_hornet.description"),
                            AdvancementType.CHALLENGE, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/place_flower_near_hornet"));

            AdvancementHolder runOn = Advancement.Builder.advancement()
                    .addCriterion("sprint_on_embur_gel", PlayerTrigger.TriggerInstance.located(EntityPredicate.Builder.entity()
                            .steppingOn(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.EMBUR_GEL_BLOCK.get())))
                            .flags(EntityFlagsPredicate.Builder.flags().setSprinting(true))
                    ))
                    .parent(embur_bog)
                    .display(
                            NetherDescentBlocks.EMBUR_GEL_BLOCK.get().asItem(),
                            translateAble("sprint_on_embur_gel.title"),
                            translateAble("sprint_on_embur_gel.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("embur_bog/sprint_on_embur_gel"));

            Advancement.Builder.advancement()
                    .addCriterion("boat_ride_on_embur_gel",
                            PlayerTrigger.TriggerInstance.located(
                                    EntityPredicate.Builder.entity()
                                            .vehicle(
                                                    EntityPredicate.Builder.entity()
                                                            .of(entityTypeHolderGetter, TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("c", "boats")))
                                                            .steppingOn(
                                                                    LocationPredicate.Builder.location()
                                                                            .setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.EMBUR_GEL_BLOCK.get()))
                                                            )
                                            )
                            )

                    )
                    .parent(runOn)
                    .display(
                            Items.CHERRY_BOAT,
                            translateAble("boat_ride_on_embur_gel.title"),
                            translateAble("boat_ride_on_embur_gel.description"),
                            AdvancementType.CHALLENGE, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/boat_ride_on_embur_gel"));


            AdvancementHolder blueFortress = Advancement.Builder.advancement()
                    .addCriterion("blue_fortress", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(output.lookup(Registries.STRUCTURE).getOrThrow(NetherDescentStructures.BLUE_FORTRESS))))
                    .parent(embur_bog)
                    .display(
                            NetherDescentBlocks.BLUE_NETHER_BRICKS.getBase().asItem(),
                            translateAble("find_blue_fortress.title"),
                            translateAble("find_blue_fortress.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/find_blue_fortress"));

            AdvancementHolder soulBlazeRod = Advancement.Builder.advancement()
                    .addCriterion("obtain_soul_blaze_rod", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.SOUL_BLAZE_ROD.get()))
                    .parent(blueFortress)
                    .display(
                            NetherDescentItems.SOUL_BLAZE_ROD.get(),
                            translateAble("obtain_soul_blaze_rod.title"),
                            translateAble("obtain_soul_blaze_rod.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/obtain_soul_blaze_rod"));

            Advancement.Builder.advancement()
                    .addCriterion("obtain_soul_fire_rod", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentBlocks.SOUL_FIRE_ROD.get()))
                    .parent(soulBlazeRod)
                    .display(
                            NetherDescentBlocks.SOUL_FIRE_ROD.get().asItem(),
                            translateAble("obtain_soul_fire_rod.title"),
                            translateAble("obtain_soul_fire_rod.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("embur_bog/obtain_soul_fire_rod"));

            AdvancementHolder sythian_torrids = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, List.of(NetherDescentBiomes.SYTHIAN_TORRIDS))
                    .parent(root)
                    .display(
                            NetherDescentBlocks.SYTHIAN_NYLIUM.get().asItem(),
                            translateAble("sythian_torrids.title"),
                            translateAble("sythian_torrids.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("sythian_torrids/root"));

            AdvancementHolder renewableBusiness = Advancement.Builder.advancement()
                    .parent(sythian_torrids)
                    .addCriterion("smelted_sythian_stalk", RecipeCraftedTrigger.TriggerInstance.craftedItem(HolderSet.direct(this.output.lookup(Registries.RECIPE).getOrThrow(NetherDescent.key(Registries.RECIPE, "gold_nugget_from_smelting")))))
                    .display(
                            NetherDescentBlocks.SYTHIAN_STALK.getItem(),
                            translateAble("smelted_sythian_stalk.title"),
                            translateAble("smelted_sythian_stalk.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("sythian_torrids/smelted_sythian_stalk"));

            Advancement.Builder.advancement()
                    .parent(renewableBusiness)
                    .addCriterion("place_stalk_on_farmland", ItemUsedOnLocationTrigger.TriggerInstance.itemUsedOnBlock(LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(blockHolderGetter, NetherDescentBlocks.SYTHIAN_FARMLAND.get())), ItemPredicate.Builder.item().of(itemHolderGetter, NetherDescentBlocks.SYTHIAN_STALK.getItem())))
                    .display(
                            NetherDescentBlocks.SYTHIAN_FARMLAND.get().asItem(),
                            translateAble("place_stalk_on_farmland.title"),
                            translateAble("place_stalk_on_farmland.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("sythian_torrids/place_stalk_on_farmland"));

            Advancement.Builder.advancement()
                    .parent(sythian_torrids)
                    .addCriterion("obtain_sythian_scaffolding", InventoryChangeTrigger.TriggerInstance.hasItems(NetherDescentItems.SYTHIAN_SCAFFOLDING.get()))
                    .display(
                            NetherDescentItems.SYTHIAN_SCAFFOLDING.get(),
                            translateAble("obtain_sythian_scaffolding.title"),
                            translateAble("obtain_sythian_scaffolding.description"),
                            AdvancementType.TASK, false, true, false
                    )
                    .save(output, NetherDescent.id("sythian_torrids/obtain_sythian_scaffolding"));

            AdvancementHolder wailing_garth = VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, List.of(NetherDescentBiomes.WAILING_GARTH))
                    .parent(root)
                    .display(
                            NetherDescentBlocks.WAILING_NYLIUM.get().asItem(),
                            translateAble("wailing_garth.title"),
                            translateAble("wailing_garth.description"),
                            AdvancementType.TASK, false, true, false
                    ).save(output, NetherDescent.id("wailing_garth/root"));

            Advancement.Builder.advancement()
                    .addCriterion("wailing_bulb_blossom", WailingTrigger.TriggerInstance.interactedWithBlock(blockHolderGetter, NetherDescentBlocks.WAILING_BULB_BLOSSOM.get()))
                    .parent(wailing_garth)
                    .display(
                            NetherDescentBlocks.WAILING_BULB_BLOSSOM.get().asItem(),
                            translateAble("step_on_wailing_bulb_blossom.title"),
                            translateAble("step_on_wailing_bulb_blossom.description"),
                            AdvancementType.CHALLENGE, true, true, false
                    ).save(output, NetherDescent.id("wailing_garth/step_on_wailing_bulb_blossom"));

            AdvancementHolder elevator = Advancement.Builder.advancement()
                    .addCriterion("wailing_gills", WailingTrigger.TriggerInstance.interactedWithBlock(blockHolderGetter, NetherDescentBlocks.WAILING_GILLS.get()))
                    .parent(wailing_garth)
                    .display(
                            NetherDescentBlocks.WAILING_GILLS.get().asItem(),
                            translateAble("float_from_wailing_gills.title"),
                            translateAble("float_from_wailing_gills.description"),
                            AdvancementType.CHALLENGE, true, true, false
                    ).save(output, NetherDescent.id("wailing_garth/float_from_wailing_gills"));

            Advancement.Builder.advancement()
                    .parent(elevator)
                    .addCriterion("float_cow_from_wailing_gills", WailingTrigger.TriggerInstance.interactedWithPoweredBlockAndEntity(blockHolderGetter, NetherDescentBlocks.WAILING_GILLS.get(), MinMaxBounds.Ints.atLeast(1), EntityPredicate.Builder.entity().of(entityTypeHolderGetter, EntityTypes.COW)))
                    .display(
                            Items.LEAD,
                            translateAble("float_cow_from_wailing_gills.title"),
                            translateAble("float_cow_from_wailing_gills.description"),
                            AdvancementType.CHALLENGE, true, true, false
                    ).save(output, NetherDescent.id("wailing_garth/float_cow_from_wailing_gills"));

            Advancement.Builder.advancement()
                    .addCriterion("kill_soul_ghast", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(entityTypeHolderGetter, NetherDescentEntityTypes.SOUL_GHAST.get())))
                    .parent(wailing_garth)
                    .display(
                            NetherDescentItems.SOUL_FIRE_CHARGE.get(),
                            translateAble("kill_soul_ghast.title"),
                            translateAble("kill_soul_ghast.description"),
                            AdvancementType.CHALLENGE, true, true, false
                    ).save(output, NetherDescent.id("wailing_garth/kill_soul_ghast"));

            VanillaAdventureAdvancements.addBiomes(Advancement.Builder.advancement(), biomeHolderGetter, NetherDescentBiomes.BIOME_FACTORIES.keySet().stream().sorted().toList())
                    .parent(root)
                    .requirements(AdvancementRequirements.Strategy.AND)
                    .display(
                            NetherDescentItems.EMBUR_GEL_BALL.get(),
                            translateAble("final_descent.title"),
                            translateAble("final_descent.description"),
                            AdvancementType.CHALLENGE, true, true, false
                    ).rewards(AdvancementRewards.Builder.experience(1000))
                    .save(output, NetherDescent.id("final_descent"));
        }

        private static MutableComponent translateAble(String key) {
            return Component.translatable("advancements." + NetherDescent.MOD_ID + "." + key);
        }
    }
}
