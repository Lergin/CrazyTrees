package de.lergin.sponge.crazytrees.data.saplingData;

import de.lergin.sponge.crazytrees.data.DataQueries;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazySapling implements DataSerializable {
    private CrazyTree crazyTree;

    public CrazySapling(CrazyTree crazyTree) {
        this.crazyTree = crazyTree;
    }

    public CrazyTree getTree() {
        return crazyTree;
    }

    @Override
    public String toString() {
        return "CrazySapling [tree=" + this.getTree().toString() + "]";
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(DataQueries.CRAZY_TREE, crazyTree);
    }
}
