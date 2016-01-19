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
import de.lergin.sponge.worldgentest.crazyTrees.oak.OakTree;

/**
 * Created by Malte on 18.01.2016.
 */
public class CrazyTreeBuilder {
    //from Dendrology
    public static CrazyTree.Builder EWACALY         = new EwcalyTree.Builder().treeHeight(24,3);
    public static CrazyTree.Builder EWACALY_LARGE   = new EwcalyTreeLarge.Builder().treeHeight(32,24);
    public static CrazyTree.Builder CEDRUM          = new CedrumTree.Builder().treeHeight(19,10);
    public static CrazyTree.Builder CEDRUM_LARGE    = new CedrumTreeLarge.Builder().treeHeight(24,12);
    public static CrazyTree.Builder DELNAS          = new DelnasTree.Builder().treeHeight(11,6);
    public static CrazyTree.Builder HEKUR           = new HekurTree.Builder().treeHeight(0,0);
    public static CrazyTree.Builder HEKUR_LARGE     = new HekurTreeLarge.Builder().treeHeight(0,0);
    public static CrazyTree.Builder KIPARIS         = new KiparisTree.Builder().treeHeight(17,5);
    public static CrazyTree.Builder KULIST          = new KulistTree.Builder().treeHeight(11,6);
    public static CrazyTree.Builder KULIST_LARGE    = new KulistTreeLarge.Builder().treeHeight(18,9);
    public static CrazyTree.Builder LATA            = new LataTree.Builder().treeHeight(21,15);
    public static CrazyTree.Builder NUCIS           = new NucisTree.Builder().treeHeight(23,15);
    public static CrazyTree.Builder SALYX           = new SalyxTree.Builder().treeHeight(6,0);
    public static CrazyTree.Builder TUOPA           = new TuopaTree.Builder().treeHeight(4,1);


    //like Vanilla
    public static CrazyTree.Builder OAK             = new OakTree.Builder();
}
