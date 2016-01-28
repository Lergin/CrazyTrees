package de.lergin.sponge.crazytrees.commands;

import de.lergin.sponge.crazytrees.data.saplingData.CrazySaplingManipulatorBuilder;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import de.lergin.sponge.crazytrees.util.TranslationHelper;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

/**
 * Created by Malte on 28.01.2016.
 */
public class GetSaplingCommandExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> target = args.getOne("player");
        if (target.isPresent()) {
            giveSaplingToPlayer(target.get(), args);
        } else {
            if (src instanceof Player) {
                giveSaplingToPlayer((Player) src, args);
            }
        }
        return CommandResult.success();
    }

    private void giveSaplingToPlayer(Player player, CommandContext args){
        Optional<ItemStack> optionalInHandItem = player.getItemInHand();

        if(optionalInHandItem.isPresent()){
            Boolean isOffered = player.getInventory().offer(getSapling(args)).getRejectedItems().isEmpty();

            if(!isOffered){
                player.sendMessage(TranslationHelper.p(player, "player.warn.not_enough_space_in_inventory"));
            }
        }else{
            player.setItemInHand(getSapling(args));
        }
    }

    private ItemStack getSapling(CommandContext args){
        ItemStack sapling = ItemStack.of(ItemTypes.SAPLING, (int) args.getOne("amount").orElse(1));

        CrazyTree.Builder crazyTree = ((CrazyTreeType) args.getOne("treeType").orElse(CrazyTreeType.OAK)).getBuilder();

        crazyTree.woodBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("woodBlock").orElse(BlockTypes.LOG.getId())
                ).get()
        );

        crazyTree.leaveBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("leaveBlock").orElse(BlockTypes.LEAVES.getId())
                ).get()
        );

        crazyTree.treeHeight(
                (Integer) args.getOne("height").orElse(crazyTree.getTreeHeightMax()),
                (Integer) args.getOne("height").orElse(crazyTree.getTreeHeightMin())
        );

        sapling.offer(
                new CrazySaplingManipulatorBuilder().setTree(crazyTree.build()).create()
        );

        return sapling;
    }
}
