package de.lergin.sponge.worldgentest.data.saplingData;

import static org.spongepowered.api.data.DataQuery.of;
import static org.spongepowered.api.data.key.KeyFactory.makeSingleKey;

import de.lergin.sponge.worldgentest.crazyTrees.CrazyTreeType;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.data.value.mutable.Value;

public class SaplingKeys {
   // public static final Key<Value<CrazyTreeType>> CRAZY_TREE_TYPE = makeSingleKey(CrazyTreeType.class, Value.class, of("CrazyTreeType"));
    public static final Key<Value<BlockState>> CRAZY_TREE_LOG = makeSingleKey(BlockState.class, Value.class, of("CrazyTreeLog"));
    public static final Key<Value<BlockState>> CRAZY_TREE_LEAVE = makeSingleKey(BlockState.class, Value.class, of("CrazyTreeLeave"));
}
