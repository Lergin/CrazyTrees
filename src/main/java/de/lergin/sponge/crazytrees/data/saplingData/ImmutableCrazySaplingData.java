package de.lergin.sponge.crazytrees.data.saplingData;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class ImmutableCrazySaplingData extends AbstractImmutableSingleData<CrazyTree, ImmutableCrazySaplingData, CrazySaplingData> {

    protected ImmutableCrazySaplingData(CrazyTree value) {
        super(value, CrazyTreeKeys.CRAZY_TREE);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(CrazyTreeKeys.CRAZY_TREE, getValue()).asImmutable();
    }

    @Override
    public <E> Optional<ImmutableCrazySaplingData> with(Key<? extends BaseValue<E>> key, E e) {
        if(this.supports(key)) {
            return Optional.of(asMutable().set(key, e).asImmutable());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public CrazySaplingData asMutable() {
        return new CrazySaplingData(this.getValue());
    }

    @Override
    public int compareTo(ImmutableCrazySaplingData o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }
}
