package de.lergin.sponge.crazytrees.data;

import de.lergin.sponge.crazytrees.data.itemDrop.ItemDrop;
import de.lergin.sponge.crazytrees.data.saplingData.CrazySapling;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazyTreeKeys {
    public static final Key<Value<ItemDrop>> ITEM_DROP = KeyFactory.makeSingleKey(ItemDrop.class, Value.class,
            DataQuery.of("ITEM_DROP"));
    public static final Key<Value<CrazyTree>> CRAZY_TREE = KeyFactory.makeSingleKey(CrazyTree.class, Value.class,
            DataQuery.of("CRAZY_SAPLING"));
}
