package de.lergin.sponge.crazytrees;

import com.google.inject.Inject;
import de.lergin.sponge.crazytrees.commands.*;
import de.lergin.sponge.crazytrees.data.itemDrop.*;
import de.lergin.sponge.crazytrees.listener.SaplingPlaceListener;
import de.lergin.sponge.crazytrees.trees.CrazyTree;
import de.lergin.sponge.crazytrees.data.saplingData.*;
import de.lergin.sponge.crazytrees.trees.vanilla.oak.OakTree;
import de.lergin.sponge.crazytrees.util.ConfigHelper;
import de.lergin.sponge.crazytrees.util.TranslationHelper;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.*;
import org.spongepowered.api.plugin.Plugin;

import java.util.*;


@Plugin(id = "crazyTrees", name = "Crazy Trees", version = "1.0")
public class CrazyTreesMain {
    @Inject
    @DefaultConfig(sharedRoot = true)
    public ConfigurationLoader<CommentedConfigurationNode> configManager;


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
        //Sponge.getRegistry().register(CatalogTypes.WORLD_GENERATOR_MODIFIER, new CrazyForestGeneratorModifier());


        //init eventListener
        Sponge.getEventManager().registerListeners(this, new SaplingPlaceListener());


        //init commands
        Sponge.getCommandManager().register(
                this,
                GetSaplingCommandExecutor.getCommandSpec(),
                ConfigHelper.getNode("commands", "getSapling", "command").getString("getSapling")
        );
    }

    @Listener
    public void onGameGameStoppedEvent(GameStoppedEvent event){
        ConfigHelper.saveConfig();
    }
}
