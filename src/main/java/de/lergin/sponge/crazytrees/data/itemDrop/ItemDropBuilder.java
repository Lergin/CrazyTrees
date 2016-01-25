package de.lergin.sponge.crazytrees.data.itemDrop;

import com.flowpowered.math.vector.Vector3d;
import de.lergin.sponge.crazytrees.data.DataQueries;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataView;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.persistence.DataBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class ItemDropBuilder implements DataBuilder<ItemDrop> {
    @Override
    public Optional<ItemDrop> build(DataView dataView) throws InvalidDataException {
        if (dataView.contains(DataQueries.NAME, DataQueries.WORLD, DataQueries.X, DataQueries.Y, DataQueries.Z, DataQueries.GROUPS)) {
            ItemDrop itemDrop = new ItemDrop(
                    dataView.getString(DataQueries.NAME).get(),
                    dataView.getString(DataQueries.WORLD).get(),
                    new Vector3d(
                            dataView.getInt(DataQueries.X).get().intValue(),
                            dataView.getInt(DataQueries.Y).get().intValue(),
                            dataView.getInt(DataQueries.Z).get().intValue()
                    ),
                    ItemStack.builder().fromContainer((MemoryDataView) dataView.get(DataQueries.ITEM_STACK).get()).build()
            );
            itemDrop.setGroups(dataView.getStringList(DataQueries.GROUPS).get());
            return Optional.of(itemDrop);
        }
        return Optional.empty();
    }
}
