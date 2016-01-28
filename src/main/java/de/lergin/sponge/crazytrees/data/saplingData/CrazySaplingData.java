package de.lergin.sponge.crazytrees.data.saplingData;

import com.google.common.base.Preconditions;
import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazySaplingData extends AbstractSingleData<CrazySapling, CrazySaplingData, ImmutableCrazySaplingData> {
    private final Key<Value<CrazySapling>> key = CrazyTreeKeys.CRAZY_SAPLING;

    protected CrazySaplingData(CrazySapling value) {
        super(value, CrazyTreeKeys.CRAZY_SAPLING);
    }


    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(key, getValue());
    }

    @Override
    public Optional<CrazySaplingData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        CrazySaplingData crazySaplingData = Preconditions.checkNotNull(mergeFunction).merge(copy(),
                dataHolder.get(CrazySaplingData.class).orElse(copy()));
        return Optional.of(set(key, crazySaplingData.get(key).get()));
    }

    @Override
    public Optional<CrazySaplingData> from(DataContainer dataContainer) {
        if (dataContainer.contains(key.getQuery())) {
            return Optional.of(set(key, dataContainer.getSerializable(key.getQuery(),
                    CrazySapling.class).orElse(getValue())));
        }
        return Optional.empty();
    }

    @Override
    public CrazySaplingData copy() {
        return new CrazySaplingData(this.getValue());
    }

    @Override
    public ImmutableCrazySaplingData asImmutable() {
        return new ImmutableCrazySaplingData(this.getValue());
    }

    @Override
    public int compareTo(CrazySaplingData crazySaplingData) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return super.toContainer().set(key, getValue());
    }
}
