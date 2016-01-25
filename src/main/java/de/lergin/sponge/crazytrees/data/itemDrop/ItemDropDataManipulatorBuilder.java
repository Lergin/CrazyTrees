package de.lergin.sponge.crazytrees.data.itemDrop;

import com.flowpowered.math.vector.Vector3d;
import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class ItemDropDataManipulatorBuilder implements DataManipulatorBuilder<ItemDropData, ImmutableItemDropData> {
    private ItemDrop itemDrop = new ItemDrop("", "world", Vector3d.FORWARD, ItemStack.of(ItemTypes.NONE, 1));

    @Override
    public ItemDropData create() {
        return new ItemDropData(itemDrop);
    }

    public ItemDropDataManipulatorBuilder setItemDrop(ItemDrop itemDrop) {
        this.itemDrop = itemDrop;
        return this;
    }

    @Override
    public Optional<ItemDropData> createFrom(DataHolder dataHolder) {
        return this.create().fill(dataHolder);
    }

    @Override
    public Optional<ItemDropData> build(DataView dataView) throws InvalidDataException {
        if (!dataView.contains(CrazyTreeKeys.ITEM_DROP.getQuery())) {
            return Optional.empty();
        }
        ItemDropData itemDropData = this.create();
        itemDropData = itemDropData.set(CrazyTreeKeys.ITEM_DROP, dataView.getSerializable(CrazyTreeKeys.ITEM_DROP.getQuery(), ItemDrop.class).get());
        return Optional.of(itemDropData);
    }
}
