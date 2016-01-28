package de.lergin.sponge.crazytrees.listener;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.data.saplingData.CrazySaplingData;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.Random;

public class SaplingPlaceListener {
    @Listener
    public void onBlockPlace(InteractBlockEvent.Secondary event, @First Player player) {

        Optional<ItemStack> optionalItemStack = player.getItemInHand();

        if(optionalItemStack.isPresent()){
            Optional<CrazySaplingData> optionalCrazySaplingData =
                    optionalItemStack.get().get(CrazySaplingData.class);

            if(optionalCrazySaplingData.isPresent()){
                CrazySaplingData crazySaplingData = optionalCrazySaplingData.get();

                CrazyTree crazyTree = crazySaplingData.get(CrazyTreeKeys.CRAZY_TREE).get();

                player.sendMessage(Text.of(crazyTree.getTreeHeightMin()));
                player.sendMessage(Text.of(crazyTree.getTreeHeightMax()));
                player.sendMessage(Text.of(crazyTree.getWoodBlock().toString()));
                player.sendMessage(Text.of(crazyTree.getLeaveBlock().toString()));

                Location<World> location = event.getTargetBlock().getLocation().get();

                crazyTree.placeObject(location.getExtent(), new Random(), location);
            }
        }
    }
}
