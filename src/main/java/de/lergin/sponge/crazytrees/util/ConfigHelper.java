package de.lergin.sponge.crazytrees.util;

import de.lergin.sponge.crazytrees.CrazyTreesMain;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.slf4j.Logger;
import org.spongepowered.api.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

/**
 * Class for working with the configuration
 */
public final class ConfigHelper {
/*    private static final ConfigurationLoader<CommentedConfigurationNode> loader =
            HoconConfigurationLoader.builder().setPath(CrazyTreesMain.instance().confDir).build();
*/

    private static final ConfigurationLoader<CommentedConfigurationNode> loader =
            CrazyTreesMain.instance().configManager;

    private static final Logger logger = CrazyTreesMain.instance().getLogger();

    private static ConfigurationNode rootNode = loader.createEmptyNode(ConfigurationOptions.defaults());

    /**
     * loads the config file
     */
    public static void loadConfig(){
        try {
            rootNode = loader.load();
        } catch(IOException e) {
            logger.warn(TranslationHelper.l("warn.config.could_not_load"));
        }


        final URL jarConfigFile = ConfigHelper.class.getResource("defaultConfig.conf");
        final ConfigurationLoader<CommentedConfigurationNode> defaultLoader =
                HoconConfigurationLoader.builder().setURL(jarConfigFile).build();

        try {
            rootNode.mergeValuesFrom(defaultLoader.load());
        } catch (IOException e1) {
            logger.error(TranslationHelper.l("error.config.could_not_load_default"));
        }
    }

    /**
     * saves the config
     */
    public static void saveConfig(){
        try {
            loader.save(rootNode);
            logger.info(TranslationHelper.l("info.config.saved"));
        } catch(IOException e) {
            logger.warn(TranslationHelper.l("warn.config.could_not_save"));
        }
    }

    /**
     * returns the configuration node of the given path
     * @param objects the path to the node
     * @return the configuration node
     */
    public static ConfigurationNode getNode(Object... objects){
        return rootNode.getNode(objects);
    }
}
