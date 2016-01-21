package de.lergin.sponge.worldgentest.data.saplingData;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
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
        //todo Queries.CONTENT_VERSION test
        if (container.contains(SaplingKeys.CRAZY_TREE_TYPE.getQuery(), SaplingKeys.CRAZY_TREE_LEAVE.getQuery(), SaplingKeys.CRAZY_TREE_LOG.getQuery())) {
            final CrazyTreeType treeType = (CrazyTreeType) container.get(SaplingKeys.CRAZY_TREE_TYPE.getQuery()).get();
            final BlockState leave = (BlockState) container.get(SaplingKeys.CRAZY_TREE_LEAVE.getQuery()).get();
            final BlockState log = (BlockState) container.get(SaplingKeys.CRAZY_TREE_LOG.getQuery()).get();
            return Optional.of(new SaplingData(treeType, leave, log));
        }
        return Optional.empty();
    }
}