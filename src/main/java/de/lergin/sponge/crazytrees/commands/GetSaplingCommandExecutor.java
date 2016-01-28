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
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Optional;

public class GetSaplingCommandExecutor implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player;

        Optional<Player> target = args.getOne("player");
        if (target.isPresent()) {
            player = target.get();
        } else if (src instanceof Player) {
            player = (Player) src;
        }else{
            return CommandResult.empty();
        }


        //create the Data from  the arguments
        ItemStack sapling = ItemStack.of(ItemTypes.SAPLING, (int) args.getOne("amount").orElse(1));

        CrazyTree.Builder crazyTreeBuilder = ((CrazyTreeType) args.getOne("treeType").orElse(CrazyTreeType.OAK)).getBuilder();

        crazyTreeBuilder.woodBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("woodBlock").orElse(BlockTypes.LOG.getId())
                ).get()
        );

        crazyTreeBuilder.leaveBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("leaveBlock").orElse(BlockTypes.LEAVES.getId())
                ).get()
        );

        crazyTreeBuilder.treeHeight(
                (Integer) args.getOne("height").orElse(crazyTreeBuilder.getTreeHeightMax()),
                (Integer) args.getOne("height").orElse(crazyTreeBuilder.getTreeHeightMin())
        );

        crazyTreeBuilder.placeBlockUnderTree(false);
        crazyTreeBuilder.replaceBlocks(new ArrayList<>());
        crazyTreeBuilder.groundBlocks(new ArrayList<>());

        CrazyTree crazyTree = crazyTreeBuilder.build();

        System.out.println(crazyTree.getGroundBlocks());
        System.out.println(crazyTree.getReplaceBlocks());

        sapling.offer(
                new CrazySaplingManipulatorBuilder().setTree(crazyTree).create()
        );

        //only for the good locking
        sapling.offer(Keys.DISPLAY_NAME, Text.of(crazyTree.getName()));

        ArrayList<ItemEnchantment> itemEnchantments = new ArrayList<>();
     //   itemEnchantments.add(new ItemEnchantment(Enchantments.UNBREAKING, 0));
        sapling.offer(Keys.ITEM_ENCHANTMENTS, itemEnchantments);

        ArrayList<Text> loreTexts = new ArrayList<>();
        loreTexts.add(TranslationHelper.p(
                player,
                "player.info.wood_block",
                crazyTree.getWoodBlock().getType().getTranslation().get(player.getLocale())
        ));
        loreTexts.add(TranslationHelper.p(
                player,
                "player.info.leave_block",
                crazyTree.getLeaveBlock().getType().getTranslation().get(player.getLocale())
        ));
        loreTexts.add(TranslationHelper.p(player, "player.info.height_block", crazyTree.getTreeHeightMax()));
        sapling.offer(Keys.ITEM_LORE, loreTexts);



        Optional<ItemStack> optionalInHandItem = player.getItemInHand();

        if(optionalInHandItem.isPresent()){
            Boolean isOffered = player.getInventory().offer(sapling).getRejectedItems().isEmpty();

            if(!isOffered){
                player.sendMessage(TranslationHelper.p(player, "player.warn.not_enough_space_in_inventory"));
            }
        }else{
            player.setItemInHand(sapling);
        }

        return CommandResult.success();
    }

    private ItemStack getSapling(CommandContext args){
        ItemStack sapling = ItemStack.of(ItemTypes.SAPLING, (int) args.getOne("amount").orElse(1));

        CrazyTree.Builder crazyTreeBuilder = ((CrazyTreeType) args.getOne("treeType").orElse(CrazyTreeType.OAK)).getBuilder();

        crazyTreeBuilder.woodBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("woodBlock").orElse(BlockTypes.LOG.getId())
                ).get()
        );

        crazyTreeBuilder.leaveBlock(
                Sponge.getRegistry().getType(
                        CatalogTypes.BLOCK_TYPE,
                        (String) args.getOne("leaveBlock").orElse(BlockTypes.LEAVES.getId())
                ).get()
        );

        crazyTreeBuilder.treeHeight(
                (Integer) args.getOne("height").orElse(crazyTreeBuilder.getTreeHeightMax()),
                (Integer) args.getOne("height").orElse(crazyTreeBuilder.getTreeHeightMin())
        );

        crazyTreeBuilder.placeBlockUnderTree(false);
        crazyTreeBuilder.replaceBlocks(new ArrayList<>());

        CrazyTree crazyTree = crazyTreeBuilder.build();

        sapling.offer(
                new CrazySaplingManipulatorBuilder().setTree(crazyTree).create()
        );

        sapling.offer(Keys.DISPLAY_NAME, Text.of(crazyTree.getName()));

        ArrayList<ItemEnchantment> itemEnchantments = new ArrayList<>();
        itemEnchantments.add(new ItemEnchantment(Enchantments.UNBREAKING, 0));
        sapling.offer(Keys.ITEM_ENCHANTMENTS, itemEnchantments);

        ArrayList<Text> loreTexts = new ArrayList<>();
        loreTexts.add(
                Text.of("WoodBlock: " + crazyTree.getWoodBlock().toString()));
        loreTexts.add(Text.of("LeaveBlock: " + crazyTree.getLeaveBlock().toString()));
        loreTexts.add(Text.of("Height: " + crazyTree.getTreeHeightMax()));
        sapling.offer(Keys.ITEM_LORE, loreTexts);

        return sapling;
    }
}
