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
    private List<ItemStack> itemStacks = new ArrayList<>();

    public ItemDrop(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void addItemStack(ItemStack itemStack){
        this.itemStacks.add(itemStack);
    }

    @Override
    public String toString() {
        return "ItemDrop [itemStacks=" + this.getItemStacks().toString() + "]";
    }

    @Override
    public int getContentVersion() {
        return 1;
    }

    @Override
    public DataContainer toContainer() {
        return new MemoryDataContainer()
                .set(DataQueries.ITEM_STACKS, itemStacks);
    }
}
