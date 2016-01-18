package de.lergin.sponge.worldgentest;

import static org.spongepowered.api.command.args.GenericArguments.onlyOne;
import static org.spongepowered.api.command.args.GenericArguments.playerOrSource;
import static org.spongepowered.api.command.args.GenericArguments.seq;
import static org.spongepowered.api.command.args.GenericArguments.world;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.CatalogType;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.block.PoweredData;
import org.spongepowered.api.data.type.*;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.chunk.PopulateChunkEvent;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.storage.WorldProperties;

import java.util.Optional;

/**
 * Created by Malte on 02.01.2016.
 */
@Plugin(id = "example", name = "Example Project", version = "1.0")
public class worldgentest {
    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Teleports a player to another world"))
                        .arguments(seq(playerOrSource(Text.of("target")), onlyOne(world(Text.of("world")))))
                        .permission("worldstest.command.tpworld")
                        .executor((src, args) -> {
                            final Optional<WorldProperties> optWorldProperties = args.getOne("world");
                            final Optional<World> optWorld = Sponge.getServer().getWorld(optWorldProperties.get().getWorldName());
                            if (!optWorld.isPresent()) {
                                throw new CommandException(Text.of("World [", Text.of(TextColors.AQUA, optWorldProperties.get().getWorldName()),
                                        "] "
                                                + "was not found."));
                            }
                            for (Player target : args.<Player>getAll("target")) {
                                target.setLocation(new Location<>(optWorld.get(), optWorld.get().getProperties()
                                        .getSpawnPosition()));
                            }
                            return CommandResult.success();
                        })
                        .build(),
                "tpworld"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("just a test command"))
                        .permission("worldstest.command.test")
                        .executor(new test_command())
                        .build(),
                "test"
        );
    }

    @Listener
    public void onGameInitialization(GameInitializationEvent event) {
        Sponge.getGame().getRegistry().register(CatalogTypes.WORLD_GENERATOR_MODIFIER, new worldgen());
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event) {

        BlockSnapshot blockSnapshot = event.getTransactions().iterator().next().getOriginal();

        if (blockSnapshot.supports(Keys.TREE_TYPE)) {
            ItemStack rose = ItemTypes.DOUBLE_PLANT.getTemplate().createStack();
            rose.offer(Keys.DOUBLE_PLANT_TYPE, DoublePlantTypes.ROSE);
            Optional<Entity> optional = blockSnapshot.getLocation().get().getExtent().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
            if (optional.isPresent()) {
                Item item = (Item) optional.get();
                item.offer(Keys.REPRESENTED_ITEM, rose.createSnapshot());
                blockSnapshot.getLocation().get().getExtent().spawnEntity(item, event.getCause());
            }


            Optional<TreeType> treeTypeOptional = blockSnapshot.getState().get(Keys.TREE_TYPE);
            if (treeTypeOptional.isPresent() && treeTypeOptional.get().equals(TreeTypes.BIRCH)) {


                ItemStack apple = ItemTypes.APPLE.getTemplate().createStack();
                Optional<Entity> optionalApple = blockSnapshot.getLocation().get().getExtent().createEntity(EntityTypes.ITEM, blockSnapshot.getPosition());
                if (optionalApple.isPresent()) {
                    Item item = (Item) optionalApple.get();
                    item.offer(Keys.REPRESENTED_ITEM, apple.createSnapshot());
                    blockSnapshot.getLocation().get().getExtent().spawnEntity(item, event.getCause());
                }
            }
        }

    }

    @Inject
    Logger logger;

    @Listener
    public void onItemDrop(DropItemEvent.Destruct event) {

        

        logger.info(event.getCause().toString());
    }
}
