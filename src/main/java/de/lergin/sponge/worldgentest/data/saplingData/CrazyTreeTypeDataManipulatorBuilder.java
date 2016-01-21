package de.lergin.sponge.worldgentest.data.saplingData;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.Optional;

public class CrazyTreeTypeDataManipulatorBuilder implements DataManipulatorBuilder<CrazyTreeTypeData, ImmutableCrazyTreeTypeData> {

    @Override
    public CrazyTreeTypeData create() {
        return new CrazyTreeTypeData(CrazyTreeType.OAK);
    }

    @Override
    public Optional<CrazyTreeTypeData> createFrom(DataHolder dataHolder) {
        return Optional.of(dataHolder.get(CrazyTreeTypeData.class).orElse(new CrazyTreeTypeData(CrazyTreeType.OAK)));
    }

    @Override
    public Optional<CrazyTreeTypeData> build(DataView container) throws InvalidDataException {
        // todo: check Queries.CONTENT_VERSION
        if (container.contains(SaplingKeys.CRAZY_TREE_TYPE.getQuery())) {
            System.out.println(container.get(SaplingKeys.CRAZY_TREE_TYPE.getQuery()).get());

            final CrazyTreeType treeType = CrazyTreeType.valueOf((String) container.get(SaplingKeys.CRAZY_TREE_TYPE.getQuery()).get());
            return Optional.of(new CrazyTreeTypeData(treeType));
        }
        return Optional.empty();
    }
}