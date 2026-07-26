package net.potionstudios.netherdescent.forge.loot;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.potionstudios.netherdescent.NetherDescent;

public class LootModifiersRegister {
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, NetherDescent.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_TABLE_LOOT_MODIFIER_TYPE =
            LOOT_MODIFIER_SERIALIZERS.register("add_table", () -> AddTableLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
