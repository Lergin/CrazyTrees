package de.lergin.sponge.worldgentest.crazyTrees;

import de.lergin.sponge.worldgentest.crazyTrees.dendrology.cedrum.CedrumTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.ewacly.EwcalyTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.ewacly.EwcalyTreeLarge;
import de.lergin.sponge.worldgentest.crazyTrees.oak.OakTree;

/**
 * Created by Malte on 18.01.2016.
 */
public class CrazyTreeBuilder {
    //from Dendrology
    public static CrazyTree.Builder EWACALY = new EwcalyTree.Builder().treeHeight(3,0);
    public static CrazyTree.Builder EWACALY_LARGE = new EwcalyTreeLarge.Builder().treeHeight(26,8);
    public static CrazyTree.Builder CEDRUM = new CedrumTree.Builder().treeHeight(19,10);
    public static CrazyTree.Builder CEDRUM_LARGE = new CedrumTree.Builder().treeHeight(24,12);

    //like Vanilla
    public static CrazyTree.Builder OAK = new OakTree.Builder();
}
