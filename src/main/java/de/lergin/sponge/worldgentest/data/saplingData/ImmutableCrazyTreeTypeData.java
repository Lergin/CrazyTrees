package de.lergin.sponge.worldgentest.data.saplingData;

import com.google.common.collect.ComparisonChain;
import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleEnumData;
import org.spongepowered.api.data.value.BaseValue;


import java.util.Optional;

public class ImmutableCrazyTreeTypeData extends AbstractImmutableSingleEnumData<CrazyTreeType, ImmutableCrazyTreeTypeData, CrazyTreeTypeData> {
    public ImmutableCrazyTreeTypeData(CrazyTreeType value) {
        super(value, CrazyTreeType.OAK, SaplingKeys.CRAZY_TREE_TYPE);
    }

    @Override
    public <E> Optional<ImmutableCrazyTreeTypeData> with(Key<? extends BaseValue<E>> key, E e) {
        return Optional.empty();
    }

    @Override
    public CrazyTreeTypeData asMutable() {
        return new CrazyTreeTypeData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public int compareTo(ImmutableCrazyTreeTypeData o) {
        return ComparisonChain.start()
                .compare(o.getValue(), this.getValue())
                .result();
    }
}
