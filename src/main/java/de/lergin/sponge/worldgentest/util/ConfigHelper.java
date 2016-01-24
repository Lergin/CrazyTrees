package de.lergin.sponge.worldgentest.util;

import de.lergin.sponge.worldgentest.worldgentest;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Malte on 24.01.2016.
 */
public final class ConfigHelper {
    private static final ConfigurationLoader<CommentedConfigurationNode> loader =
            HoconConfigurationLoader.builder().setPath(worldgentest.instance().confDir).build();

    private static final Logger logger = worldgentest.instance().getLogger();

    private static ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

    public static void loadConfig(){
        try {
            rootNode = loader.load();

            if(rootNode.getChildrenList().isEmpty()){
                logger.warn(TranslationHelper.l("warn.config.no_config_or_empty"));

                loadDefaultConfig();
            }
        } catch(IOException e) {
            logger.warn(TranslationHelper.l("warn.config.could_not_load"));

            loadDefaultConfig();
        }
    }

    private static void loadDefaultConfig(){
        final URL jarConfigFile = ConfigHelper.class.getResource("defaultConfig.conf");
        final ConfigurationLoader<CommentedConfigurationNode> loader =
                HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

        try {
            rootNode = loader.load();
        } catch (IOException e1) {
            logger.error(TranslationHelper.l("error.config.could_not_load_default"));
        }
    }

    public static void saveConfig(){
        try {
            loader.save(rootNode);
        } catch(IOException e) {
            logger.warn(TranslationHelper.l("warn.config.could_not_save"));
        }
    }

    public static ConfigurationNode getNode(Object... objects){
        return rootNode.getNode(objects);
    }

}
