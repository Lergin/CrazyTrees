package de.lergin.sponge.crazytrees.data.itemDrop;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class ImmutableItemDropData extends AbstractImmutableSingleData<ItemDrop, ImmutableItemDropData, ItemDropData> {

    protected ImmutableItemDropData(ItemDrop value) {
        super(value, CrazyTreeKeys.ITEM_DROP);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(CrazyTreeKeys.ITEM_DROP, getValue()).asImmutable();
    }

    @Override
    public <E> Optional<ImmutableItemDropData> with(Key<? extends BaseValue<E>> key, E e) {
        if(this.supports(key)) {
            return Optional.of(asMutable().set(key, e).asImmutable());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public ItemDropData asMutable() {
        return new ItemDropData(this.getValue());
    }

    @Override
    public int compareTo(ImmutableItemDropData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }
}
