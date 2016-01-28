package de.lergin.sponge.crazytrees.data.saplingData;

import com.google.common.base.Preconditions;
import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
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
public class CrazySaplingData extends AbstractSingleData<CrazyTree, CrazySaplingData, ImmutableCrazySaplingData> {
    protected CrazySaplingData(CrazyTree value) {
        super(value, CrazyTreeKeys.CRAZY_TREE);
    }


    @Override
    protected Value<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory().createValue(CrazyTreeKeys.CRAZY_TREE, getValue());
    }

    @Override
    public Optional<CrazySaplingData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        CrazySaplingData crazySaplingData = Preconditions.checkNotNull(mergeFunction).merge(copy(),
                dataHolder.get(CrazySaplingData.class).orElse(copy()));
        return Optional.of(set(CrazyTreeKeys.CRAZY_TREE, crazySaplingData.get(CrazyTreeKeys.CRAZY_TREE).get()));
    }

    @Override
    public Optional<CrazySaplingData> from(DataContainer dataContainer) {
        if (dataContainer.contains(CrazyTreeKeys.CRAZY_TREE.getQuery())) {
            return Optional.of(set(CrazyTreeKeys.CRAZY_TREE, dataContainer.getSerializable(CrazyTreeKeys.CRAZY_TREE.getQuery(),
                    CrazyTree.class).orElse(getValue())));
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
        return super.toContainer().set(CrazyTreeKeys.CRAZY_TREE, getValue());
    }
}
