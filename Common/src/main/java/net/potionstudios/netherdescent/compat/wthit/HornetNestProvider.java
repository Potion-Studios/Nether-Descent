package net.potionstudios.netherdescent.compat.wthit;

import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.util.GsonHelper;
import org.jspecify.annotations.NonNull;

public enum HornetNestProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendBody(@NonNull ITooltip tooltip, IBlockAccessor accessor, @NonNull IPluginConfig config) {
        HornetNestDataProvider.OccupantsData occupants = accessor.getData().get(HornetNestDataProvider.OCCUPANTS);
        if (occupants != null && config.getBoolean(Identifier.withDefaultNamespace("bee.hive_occupants"))) {
            Object2IntLinkedOpenHashMap<String> names = new Object2IntLinkedOpenHashMap<>(occupants.occupants().size());

            for (HornetNestDataProvider.OccupantsData.Occupant occupant : occupants.occupants()) {
                Component component = null;
                if (occupant.customName() != null) {
                    component = ComponentSerialization.CODEC.parse(JsonOps.INSTANCE, GsonHelper.parse(occupant.customName())).result().orElse(null);
                }
                if (component == null) component = occupant.entityType().getDescription();

                String name = component.getString();
                names.put(name, names.getOrDefault(name, 0) + 1);
            }

            if (!names.isEmpty()) {
                MutableComponent component = Component.empty();

                for (Object2IntMap.Entry<String> entry : names.object2IntEntrySet()) {
                    if (!component.getSiblings().isEmpty()) component.append(CommonComponents.NEW_LINE);
                    String name = entry.getKey();
                    int count = entry.getIntValue();
                    if (count > 1) component.append(Component.literal(count + " " + name));
                    else component.append(Component.literal(name));
                }

                tooltip.setLine(Identifier.withDefaultNamespace("bee.hive_occupants"), component);
            }
        }
    }
}