package de.lergin.sponge.crazytrees.listener;

import com.flowpowered.math.vector.Vector3d;
import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.data.saplingData.CrazySaplingData;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
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

                if(location.getPosition().equals(Vector3d.ZERO))
                    return;

                if(location.getBlockType() == BlockTypes.AIR)
                    return;

                location = location.add(event.getTargetSide().toVector3d());

                CrazyTree crazyTree = crazySaplingData.get(CrazyTreeKeys.CRAZY_TREE).get();


                if(crazyTree.canPlaceAt(location)){
                    crazyTree.placeObject(location.getExtent(), new Random(), location);

                    event.setCancelled(true);

                    itemStack.setQuantity(itemStack.getQuantity() - 1);
                    if(player.get(Keys.GAME_MODE).get() != GameModes.CREATIVE){
                        player.setItemInHand(itemStack);
                    }
                }
            }
        }
    }
}
