package de.lergin.sponge.crazytrees.data.saplingData;

import com.google.common.collect.ImmutableList;
import de.lergin.sponge.crazytrees.data.DataQueries;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.persistence.DataBuilder;
import org.spongepowered.api.util.persistence.InvalidDataException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazySaplingBuilder implements DataBuilder<CrazySapling> {
    @Override
    public Optional<CrazySapling> build(DataView dataView) throws InvalidDataException {
        if (dataView.contains(DataQueries.ITEM_STACKS)) {
            CrazyTree crazyTree =
                    (CrazyTree) dataView.get(DataQueries.CRAZY_TREE).get();

            CrazySapling crazySapling = new CrazySapling(
                    crazyTree
            );
            return Optional.of(crazySapling);
        }
        return Optional.empty();
    }
}
