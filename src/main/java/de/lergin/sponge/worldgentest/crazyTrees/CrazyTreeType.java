package de.lergin.sponge.worldgentest.crazyTrees;

import de.lergin.sponge.worldgentest.crazyTrees.dendrology.*;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.cedrum.CedrumTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.cedrum.CedrumTreeLarge;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.ewacly.EwcalyTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.ewacly.EwcalyTreeLarge;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.hekur.HekurTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.hekur.HekurTreeLarge;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.kulist.KulistTree;
import de.lergin.sponge.worldgentest.crazyTrees.dendrology.kulist.KulistTreeLarge;
import de.lergin.sponge.worldgentest.crazyTrees.vanilla.oak.OakTree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Malte on 20.01.2016.
 */
public enum CrazyTreeType {
    //from Dendrology
    EWACALY(new EwcalyTree.Builder().treeHeight(24,3)),
    EWACALY_LARGE(new EwcalyTreeLarge.Builder().treeHeight(32,24)),
    CEDRUM(new CedrumTree.Builder().treeHeight(19,10)),
    CEDRUM_LARGE(new CedrumTreeLarge.Builder().treeHeight(24,12)),
    DELNAS(new DelnasTree.Builder().treeHeight(11,6)),
    HEKUR(new HekurTree.Builder().treeHeight(0,0)),
    HEKUR_LARGE(new HekurTreeLarge.Builder().treeHeight(0,0)),
    KIPARIS(new KiparisTree.Builder().treeHeight(17,5)),
    KULIST(new KulistTree.Builder().treeHeight(11,6)),
    KULIST_LARGE(new KulistTreeLarge.Builder().treeHeight(18,9)),
    LATA(new LataTree.Builder().treeHeight(21,15)),
    NUCIS(new NucisTree.Builder().treeHeight(23,15)),
    SALYX(new SalyxTree.Builder().treeHeight(6,0)),
    TUOPA(new TuopaTree.Builder().treeHeight(4,1)),

    //like Vanilla
    OAK(new OakTree.Builder().treeHeight(14,4));


    CrazyTree.Builder builder;

    CrazyTreeType(CrazyTree.Builder crazyTreeBuilder){
        builder = crazyTreeBuilder;
    }

    public CrazyTree.Builder getBuilder(){
        return builder;
    }

    private static final List<CrazyTreeType> TREE_TYPES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final Random RANDOM = new Random();

    public static CrazyTreeType random()  {
        return TREE_TYPES.get(RANDOM.nextInt(TREE_TYPES.size()));
    }
}
