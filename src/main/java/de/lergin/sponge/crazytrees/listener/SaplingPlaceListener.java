package de.lergin.sponge.crazytrees.listener;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.data.saplingData.CrazySaplingData;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.Random;

public class SaplingPlaceListener {
    @Listener
    public void onBlockPlace(InteractBlockEvent.Secondary event, @First Player player) {
        Optional<ItemStack> optionalItemStack = player.getItemInHand();

        if(optionalItemStack.isPresent()){
            ItemStack itemStack = optionalItemStack.get();

            Optional<CrazySaplingData> optionalCrazySaplingData = itemStack.get(CrazySaplingData.class);

            if(optionalCrazySaplingData.isPresent()){
                CrazySaplingData crazySaplingData = optionalCrazySaplingData.get();

                Location<World> location = event.getTargetBlock().getLocation().get();

                location = location.add(event.getTargetSide().toVector3d());

                CrazyTree crazyTree = crazySaplingData.get(CrazyTreeKeys.CRAZY_TREE).get();

                crazyTree.placeObject(location.getExtent(), new Random(), location);

                event.setCancelled(true);

                itemStack.setQuantity(itemStack.getQuantity() - 1);
                player.setItemInHand(itemStack);
            }
        }
    }
}
