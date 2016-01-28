package de.lergin.sponge.crazytrees.commands;

import de.lergin.sponge.crazytrees.data.CrazyTreeKeys;
import de.lergin.sponge.crazytrees.data.itemDrop.ItemDropData;
import de.lergin.sponge.crazytrees.data.saplingData.CrazySaplingData;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class TestDataValidateCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> target = args.getOne("player");
        if (target.isPresent()) {
            Player player = target.get();
            Optional<CrazySaplingData> optional = player.get(CrazySaplingData.class);
            if (optional.isPresent()) {
                src.sendMessage(Text.of("Data available!"));
                System.out.println(optional.get().toString());
            }
        } else {
            if (src instanceof Player) {
                Player player = (Player) src;
                Optional<ItemDropData> optional = player.get(ItemDropData.class);
                Optional<CrazySaplingData> optional2 = player.get(CrazySaplingData.class);
                if (optional.isPresent()) {
                    src.sendMessage(Text.of("Data available!"));
                    System.out.println(optional.get().get(CrazyTreeKeys.ITEM_DROP).toString());
                }
            }
        }
        return CommandResult.success();
    }
}
