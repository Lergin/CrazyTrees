package de.lergin.sponge.crazytrees.data;

import org.spongepowered.api.data.DataQuery;
import static org.spongepowered.api.data.DataQuery.of;

/**
 * Created by Malte on 25.01.2016.
 */
public class DataQueries {
    public static final DataQuery NAME = of("name");
    public static final DataQuery WORLD = of("world");
    public static final DataQuery X = of("x");
    public static final DataQuery Y = of("y");
    public static final DataQuery Z = of("z");
    public static final DataQuery GROUPS = of("groups");
    public static final DataQuery ITEM_STACK = of("itemStack");
}
