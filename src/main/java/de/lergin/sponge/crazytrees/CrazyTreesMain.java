package de.lergin.sponge.crazytrees;

import static org.spongepowered.api.command.args.GenericArguments.onlyOne;
import static org.spongepowered.api.command.args.GenericArguments.playerOrSource;
import static org.spongepowered.api.command.args.GenericArguments.seq;
import static org.spongepowered.api.command.args.GenericArguments.world;

import com.google.inject.Inject;
import de.lergin.sponge.crazytrees.commands.*;
import de.lergin.sponge.crazytrees.data.itemDrop.*;
import de.lergin.sponge.crazytrees.listener.SaplingPlaceListener;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.trees.CrazyTreeType;
import de.lergin.sponge.crazytrees.data.saplingData.*;
import de.lergin.sponge.crazytrees.trees.vanilla.oak.OakTree;
import de.lergin.sponge.crazytrees.util.ConfigHelper;
import de.lergin.sponge.crazytrees.util.TranslationHelper;
import org.slf4j.Logger;
import org.spongepowered.api.CatalogTypes;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

import java.nio.file.Path;
import java.util.*;


@Plugin(id = "crazyTrees", name = "Crazy Trees", version = "0.1")
public class CrazyTreesMain {
    @Inject
    @ConfigDir(sharedRoot = false)
    public Path confDir;

    @Inject
    private Logger logger;

    public Logger getLogger() {
        return logger;
    }


    private static CrazyTreesMain instance;

    public static CrazyTreesMain instance() {
        return instance;
    }

    @Listener
    public void gameConstruct(GameConstructionEvent event) {
        instance = this;
    }

    @Listener
    public void onGamePreInitialization(GamePreInitializationEvent event) {
        ConfigHelper.loadConfig();

        //translation setup
        Locale.setDefault(
                Locale.forLanguageTag(
                        ConfigHelper.getNode("translation", "defaultLanguage").getString("en")
                )
        );

        logger.info(
                TranslationHelper.s(Locale.ENGLISH, "translation.default.set", Locale.getDefault().toLanguageTag())
        );


        final Locale logLanguage = Locale.forLanguageTag(
                ConfigHelper.getNode("translation", "logLanguage").getString("en")
        );

        TranslationHelper.setLogLanguage(logLanguage);
        logger.info(TranslationHelper.l("translation.log.set", logLanguage.toLanguageTag()));
    }

    @Listener
    public void onGameInitialization(GameInitializationEvent event) {

        //init customData
        Sponge.getDataManager().register(ItemDropData.class, ImmutableItemDropData.class,
                new ItemDropDataManipulatorBuilder());
        Sponge.getDataManager().registerBuilder(ItemDrop.class, new ItemDropBuilder());

        Sponge.getDataManager().register(CrazySaplingData.class, ImmutableCrazySaplingData.class,
                new CrazySaplingManipulatorBuilder());
        Sponge.getDataManager().registerBuilder(CrazyTree.class, new OakTree.Builder());


        //init worldGeneratorModifiers
        Sponge.getRegistry().register(CatalogTypes.WORLD_GENERATOR_MODIFIER, new CrazyForestGeneratorModifier());


        //init eventListener
        Sponge.getEventManager().registerListeners(this, new SaplingPlaceListener());


        //init commands
        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Validates data"))
                        .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                        .executor(new TestDataValidateCommand())
                        .build(),
                "validateData");

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Validates data"))
                        .arguments(GenericArguments.optional(GenericArguments.onlyOne(GenericArguments.player(Text.of("player")))))
                        .executor(new TestDataAddCommand())
                        .build(),
                "addData"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("just a test command"))
                        .executor(new TestCommand())
                        .build(),
                "test"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Teleports a player to another world"))
                        .arguments(seq(playerOrSource(Text.of("target")), onlyOne(world(Text.of("world")))))
                        .permission("worldstest.command.tpworld")
                        .executor(new TestTpWorld())
                        .build(),
                "tpworld"
        );

        Sponge.getCommandManager().register(
                this,
                CommandSpec.builder()
                        .description(Text.of("Teleports a player to another world"))
                        .arguments(
                                seq(
                                        playerOrSource(Text.of("target")),
                                        GenericArguments.onlyOne(
                                                GenericArguments.enumValue(Text.of("treeType"), CrazyTreeType.class)
                                        ),
                                        GenericArguments.optional(
                                                GenericArguments.onlyOne(
                                                        GenericArguments.integer(Text.of("amount"))
                                                )
                                        ),
                                        GenericArguments.optional(GenericArguments.string(Text.of("woodBlock"))),
                                        GenericArguments.optional(GenericArguments.string(Text.of("leaveBlock"))),
                                        GenericArguments.optional(
                                                GenericArguments.onlyOne(
                                                        GenericArguments.integer(Text.of("height"))
                                                )
                                        )
                                )
                        )
                        .permission("worldstest.command.tpworld")
                        .executor(new GetSaplingCommandExecutor())
                        .build(),
                "crazySapling"
        );
    }

    @Listener void onGameStopping(GameStoppingEvent event){
        ConfigHelper.saveConfig();
    }
}
