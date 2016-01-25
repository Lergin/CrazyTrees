package de.lergin.sponge.crazytrees.commands;

import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.translator.ConfigurateTranslator;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.io.*;
import java.util.Random;
import java.util.concurrent.Callable;


/**
 * Created by Malte on 02.01.2016.
 */
public class TestCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
     /*   Player player = (Player) src;

        int x  = player.getLocation().getBlockX() + 2;
        int y  = player.getLocation().getBlockY();
        int z  = player.getLocation().getBlockZ();

        CrazyTree crazyTree = CrazyTreeType.OAK.getBuilder()
                .leaveBlock(BlockTypes.SPONGE)
                .woodBlock(BlockTypes.OBSIDIAN)
                .placeBlockUnderTree(false)
                .build();


        crazyTree.placeObject(player.getWorld(), new Random(), x+3, y, z);


        return CommandResult.success();*/

        Player player = (Player) src;
        try {

            ItemStackSnapshot itemSnapshot = player.getItemInHand().get().createSnapshot();
            DataContainer dataContainer = itemSnapshot.toContainer();

            Text msg1ContainerName = Text.of(TextColors.YELLOW, "First container: " + dataContainer.toString());
            player.sendMessage(msg1ContainerName);

            StringWriter writer = new StringWriter();
            HoconConfigurationLoader loader = HoconConfigurationLoader.builder().setSink(() -> new BufferedWriter(writer)).build();

            loader.save(ConfigurateTranslator.instance().translateData(dataContainer));

            //Reader
            String toString = writer.toString();
            StringReader reader = new StringReader(toString);
            DataView view = ConfigurateTranslator.instance().translateFrom(HoconConfigurationLoader.builder().setSource(() -> new BufferedReader(reader)).build().load());

            DataContainer container = view.getContainer();

            Text msg2ContainerName = Text.of(TextColors.YELLOW, "Second container: " + container.toString());
            player.sendMessage(msg2ContainerName);
        } catch (IOException ex) {
            player.sendMessages(Text.of("SADSADSDADSADDSA"));
        }
        return CommandResult.empty();
    }
}
