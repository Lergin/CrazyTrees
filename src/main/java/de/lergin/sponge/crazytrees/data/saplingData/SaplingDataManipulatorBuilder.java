package de.lergin.sponge.crazytrees.data.saplingData;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Optional;

public class SaplingDataManipulatorBuilder implements DataManipulatorBuilder<SaplingData, ImmutableSaplingData> {

    @Override
    public SaplingData create() {
        return new SaplingData();
    }

    @Override
    public Optional<SaplingData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(SaplingData.class).orElse(new SaplingData()));
    }

    @Override
    public Optional<SaplingData> build(DataView container) throws InvalidDataException {
        // todo: check Queries.CONTENT_VERSION
        if (container.contains(SaplingKeys.CRAZY_TREE_LEAVE.getQuery(), SaplingKeys.CRAZY_TREE_LOG.getQuery())) {
            final BlockState leave = BlockState.builder().build((DataView) container.get(SaplingKeys.CRAZY_TREE_LEAVE.getQuery()).get()).get();
            final BlockState log = BlockState.builder().build((DataView) container.get(SaplingKeys.CRAZY_TREE_LOG.getQuery()).get()).get();
            return Optional.of(new SaplingData(leave, log));
        }
        return Optional.empty();
    }
}