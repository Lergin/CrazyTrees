package de.lergin.sponge.crazytrees.data.saplingData;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazySaplingManipulatorBuilder implements DataManipulatorBuilder<CrazySaplingData, ImmutableCrazySaplingData> {
    private CrazyTree crazyTree = CrazyTreeType.OAK.get();

    private final Key<Value<CrazySapling>> key = CrazyTreeKeys.CRAZY_SAPLING;

    @Override
    public CrazySaplingData create() {
        return new CrazySaplingData(new CrazySapling(crazyTree));
    }

    public CrazySaplingManipulatorBuilder setTree(CrazyTree crazyTree) {
        this.crazyTree = crazyTree;
        return this;
    }

    @Override
    public Optional<CrazySaplingData> createFrom(DataHolder dataHolder) {
        return this.create().fill(dataHolder);
    }

    @Override
    public Optional<CrazySaplingData> build(DataView dataView) throws InvalidDataException {
        if (!dataView.contains(key.getQuery())) {
            return Optional.empty();
        }
        CrazySaplingData crazySaplingData = this.create();
        crazySaplingData = crazySaplingData.set(key, dataView.getSerializable(key.getQuery(), CrazySapling.class).get());
        return Optional.of(crazySaplingData);
    }
}
