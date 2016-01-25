package de.lergin.sponge.crazytrees.data.itemDrop;

import com.flowpowered.math.vector.Vector3d;
import de.lergin.sponge.crazytrees.data.DataQueries;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Malte on 25.01.2016.
 */
public class ItemDrop implements DataSerializable {
    private double x;
    private double y;
    private double z;
    private List<String> groups;
    private String name;
    private String world;
    private ItemStack itemStack = ItemStack.of(ItemTypes.POISONOUS_POTATO, 3);

    public ItemDrop(String name, String world, Vector3d position, ItemStack itemStack) {
        this.x = position.getX();
        this.y = position.getY();
        this.z = position.getZ();
        this.name = name;
        this.world = world;
        this.itemStack = itemStack;
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorld() {
        return this.world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public List<String> getGroups() {
        if (this.groups == null) {
            this.groups = new ArrayList<>();
        }
        return this.groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }


    @Override
    public String toString() {
        return "Warp [name=" + this.name + ", world=" + this.world + ", x=" + this.x + ", y=" + this.y + ", z="
                + this.z + ", groups=" + this.groups + ", item_stack=" + this.itemStack.toString()
                + "]";
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(DataQueries.NAME, getName())
                .set(DataQueries.WORLD, getWorld())
                .set(DataQueries.X, getX())
                .set(DataQueries.Y, getY())
                .set(DataQueries.Z, getZ())
                .set(DataQueries.GROUPS, getGroups())
                .set(DataQueries.ITEM_STACK, itemStack);
    }
}
