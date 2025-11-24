package net.potionstudios.netherdescent.compat.wthit;

import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import mcp.mobius.waila.api.IBlockAccessor;
import mcp.mobius.waila.api.IBlockComponentProvider;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.ITooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum HornetNestProvider implements IBlockComponentProvider {

    INSTANCE;

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        HornetNestDataProvider.OccupantsData occupants = accessor.getData().get(HornetNestDataProvider.OCCUPANTS);
        if (occupants != null && config.getBoolean(ResourceLocation.withDefaultNamespace("bee.hive_occupants"))) {
            var names = new Object2IntLinkedOpenHashMap<String>(occupants.occupants().size());

            for (var occupant : occupants.occupants()) {
                Component component = null;
                if (occupant.customName() != null) {
                    component = Component.Serializer.fromJson(occupant.customName(), accessor.getWorld().registryAccess());
                }
                if (component == null) component = occupant.entityType().getDescription();

                var name = component.getString();
                names.put(name, names.getOrDefault(name, 0) + 1);
            }

            if (!names.isEmpty()) {
                var component = Component.empty();

                for (var entry : names.object2IntEntrySet()) {
                    if (!component.getSiblings().isEmpty()) component.append(CommonComponents.NEW_LINE);
                    var name = entry.getKey();
                    var count = entry.getIntValue();
                    if (count > 1) component.append(Component.literal(count + " " + name));
                    else component.append(Component.literal(name));
                }

                tooltip.setLine(ResourceLocation.withDefaultNamespace("bee.hive_occupants"), component);
            }
        }
    }
}
