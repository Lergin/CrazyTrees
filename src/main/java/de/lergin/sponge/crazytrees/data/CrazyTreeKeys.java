package de.lergin.sponge.crazytrees.data;

import de.lergin.sponge.crazytrees.data.itemDrop.ItemDrop;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

/**
 * Created by Malte on 25.01.2016.
 */
public class CrazyTreeKeys {
    public static final Key<Value<ItemDrop>> ITEM_DROP = KeyFactory.makeSingleKey(ItemDrop.class, Value.class, DataQuery.of("ITEM_DROP"));
}
