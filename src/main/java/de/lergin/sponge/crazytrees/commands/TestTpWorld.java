package de.lergin.sponge.crazytrees.commands;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.Optional;

/**
 * Created by Malte on 25.01.2016.
 */
public class TestTpWorld implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        final Optional<WorldProperties> optWorldProperties = commandContext.getOne("world");
        final Optional<World> optWorld = Sponge.getServer().getWorld(optWorldProperties.get().getWorldName());
        if (!optWorld.isPresent()) {
            throw new CommandException(Text.of("World [", Text.of(TextColors.AQUA, optWorldProperties.get().getWorldName()),
                    "] "
                            + "was not found."));
        }
        for (Player target : commandContext.<Player>getAll("target")) {
            target.setLocation(new Location<>(optWorld.get(), optWorld.get().getProperties()
                    .getSpawnPosition()));
        }
        return CommandResult.success();
    }
}
