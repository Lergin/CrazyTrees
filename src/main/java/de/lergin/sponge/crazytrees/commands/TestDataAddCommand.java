package de.lergin.sponge.crazytrees.commands;

import de.lergin.sponge.crazytrees.data.itemDrop.ItemDrop;
import de.lergin.sponge.crazytrees.data.itemDrop.ItemDropDataManipulatorBuilder;
import de.lergin.sponge.crazytrees.data.saplingData.CrazyTreeTypeData;
import de.lergin.sponge.crazytrees.data.saplingData.SaplingData;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class TestDataAddCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        src.sendMessage(Text.of("SADSADDSADSADSA"));

        Optional<Player> target = args.getOne("player");
        Optional<CrazyTreeType> integer = args.getOne("amount");
        if (target.isPresent()) {
            Player player = target.get();
            player.offer(new SaplingData(BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState()));
            player.offer(new CrazyTreeTypeData(CrazyTreeType.DELNAS));
            player.offer(new ItemDropDataManipulatorBuilder().setItemDrop(new ItemDrop("hello", player.getWorld().getName(), player.getLocation().getPosition(), ItemStack.of(ItemTypes.ITEM_FRAME, 23))).create());
        } else {
            if (src instanceof Player) {
                Player player = (Player) src;
                player.sendMessage(Text.of("WEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE"));
                player.offer(new SaplingData( BlockTypes.LOG.getDefaultState(), BlockTypes.LEAVES.getDefaultState()));
//                player.offer(new CrazyTreeTypeData(integer.get()));
                player.offer(new ItemDropDataManipulatorBuilder().setItemDrop(new ItemDrop("hello", player.getWorld().getName(), player.getLocation().getPosition(), ItemStack.of(ItemTypes.ITEM_FRAME, 23))).create());
            }
        }
        return CommandResult.success();
    }
}
