package de.lergin.sponge.crazytrees.data.saplingData;


import com.google.common.base.Objects;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleEnumData;
import org.spongepowered.api.data.merge.MergeFunction;



import java.util.Optional;

public class CrazyTreeTypeData extends AbstractSingleEnumData<CrazyTreeType, CrazyTreeTypeData, ImmutableCrazyTreeTypeData> {

    public CrazyTreeTypeData(CrazyTreeType value) {
        super(value, SaplingKeys.CRAZY_TREE_TYPE, CrazyTreeType.OAK);
    }

    @Override
    public Optional<CrazyTreeTypeData> fill(DataHolder dataHolder, MergeFunction mergeFunction) {
        return Optional.empty();
    }

    @Override
    public Optional<CrazyTreeTypeData> from(DataContainer dataContainer) {
        if (!dataContainer.contains(SaplingKeys.CRAZY_TREE_TYPE.getQuery())) {
            return Optional.empty();
        }

        final CrazyTreeType treeType = (CrazyTreeType) dataContainer.get(SaplingKeys.CRAZY_TREE_TYPE.getQuery()).get();

        this.setValue(treeType);

        return Optional.of(this);
    }

    @Override
    public CrazyTreeTypeData copy() {
        return new CrazyTreeTypeData(this.getValue());
    }

    @Override
    public ImmutableCrazyTreeTypeData asImmutable() {
        return new ImmutableCrazyTreeTypeData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("treeType", this.getValue())
                .toString();
    }
}
