package de.lergin.sponge.worldgentest.data.saplingData;

import org.spongepowered.api.block.BlockState;

import java.util.Comparator;

/**
 * Created by Malte on 21.01.2016.
 */
public class BlockStateComparator implements Comparator<BlockState> {
    @Override
    public int compare(BlockState o1, BlockState o2) {
        return o1.equals(o2)?0:1;
    }
}
