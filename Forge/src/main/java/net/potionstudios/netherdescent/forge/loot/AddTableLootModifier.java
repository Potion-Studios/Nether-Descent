package net.potionstudios.netherdescent.forge.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class AddTableLootModifier extends LootModifier {
    @ApiStatus.Internal
    public static final MapCodec<AddTableLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(AddTableLootModifier::table)).apply(instance, AddTableLootModifier::new));

    private final ResourceKey<LootTable> table;

    public AddTableLootModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> table) {
        super(conditionsIn);
        this.table = table;
    }

    public ResourceKey<LootTable> table() {
        return this.table;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(LootTable arg, ObjectArrayList<ItemStack> objectArrayList, LootContext context) {
        context.getResolver().lookupOrThrow(Registries.LOOT_TABLE).get(this.table).ifPresent(extraTable ->
                extraTable.value().getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), objectArrayList::add)));
        return objectArrayList;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return LootModifiersRegister.ADD_TABLE_LOOT_MODIFIER_TYPE.get();
    }
}
