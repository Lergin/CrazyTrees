package de.lergin.sponge.crazytrees.data.itemDrop;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.ImmutableList;
import de.lergin.sponge.crazytrees.data.DataQueries;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.MemoryDataView;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.persistence.DataBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Malte on 25.01.2016.
 */
public class ItemDropBuilder implements DataBuilder<ItemDrop> {
    @Override
    public Optional<ItemDrop> build(DataView dataView) throws InvalidDataException {
        if (dataView.contains(DataQueries.ITEM_STACKS)) {
            ImmutableList<ItemStack> itemStackImmutableList =
                    (ImmutableList<ItemStack>) dataView.get(DataQueries.ITEM_STACKS).get();

            ArrayList<ItemStack> itemStacks =
                    itemStackImmutableList.stream().collect(Collectors.toCollection(ArrayList::new));

            ItemDrop itemDrop = new ItemDrop(
                   itemStacks
            );
            return Optional.of(itemDrop);
        }
        return Optional.empty();
    }
}
