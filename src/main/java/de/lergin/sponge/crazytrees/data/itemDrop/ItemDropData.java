package de.lergin.sponge.crazytrees.data.itemDrop;

import com.google.common.base.Preconditions;
import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class ItemDropData extends AbstractSingleData<ItemDrop, ItemDropData, ImmutableItemDropData> {
    protected ItemDropData(ItemDrop value) {
        super(value, CrazyTreeKeys.ITEM_DROP);
    }

    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(CrazyTreeKeys.ITEM_DROP, getValue());
    }

    @Override
    public Optional<ItemDropData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        ItemDropData itemDropData = Preconditions.checkNotNull(mergeFunction).merge(copy(),
                dataHolder.get(ItemDropData.class).orElse(copy()));
        return Optional.of(set(CrazyTreeKeys.ITEM_DROP, itemDropData.get(CrazyTreeKeys.ITEM_DROP).get()));
    }

    @Override
    public Optional<ItemDropData> from(DataContainer dataContainer) {
        if (dataContainer.contains(CrazyTreeKeys.ITEM_DROP.getQuery())) {
            return Optional.of(set(CrazyTreeKeys.ITEM_DROP, dataContainer.getSerializable(CrazyTreeKeys.ITEM_DROP.getQuery(),
                    ItemDrop.class).orElse(getValue())));
        }
        return Optional.empty();
    }

    @Override
    public ItemDropData copy() {
        return new ItemDropData(this.getValue());
    }

    @Override
    public ImmutableItemDropData asImmutable() {
        return new ImmutableItemDropData(this.getValue());
    }

    @Override
    public int compareTo(ItemDropData itemDropData) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(CrazyTreeKeys.ITEM_DROP, getValue());
    }
}
