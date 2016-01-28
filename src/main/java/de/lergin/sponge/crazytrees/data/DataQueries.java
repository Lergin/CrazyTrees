package de.lergin.sponge.crazytrees.data;

import org.spongepowered.api.data.DataQuery;
import static org.spongepowered.api.data.DataQuery.of;

/**
 * Created by Malte on 25.01.2016.
 */
public class DataQueries {
    public static final DataQuery ITEM_STACKS = of("itemStacks");
    public static final DataQuery CRAZY_TREE = of("crazyTree");
    public static final DataQuery WOOD_BLOCK = of("woodBlock");
    public static final DataQuery LEAVE_BLOCK = of("leaveBlock");
    public static final DataQuery PLACE_BLOCK_UNDER_TREE = of("placeBlockUnderTree");
    public static final DataQuery UNDER_TREE_BLOCK = of("underTreeBlock");
    public static final DataQuery REPLACE_BLOCKS = of("replaceBlocks");
    public static final DataQuery GROUND_BLOCKS = of("groundBlocks");
    public static final DataQuery TREE_HEIGHT_MAX = of("treeHeightMax");
    public static final DataQuery TREE_HEIGHT_MIN = of("treeHeightMin");
}
