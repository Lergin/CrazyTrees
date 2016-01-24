package de.lergin.sponge.worldgentest.util;

import com.google.inject.Inject;
import de.lergin.sponge.worldgentest.worldgentest;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.ResourceBundleTranslation;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Malte on 23.01.2016.
 */
public final class TranslationHelper {

    @Inject
    private static Logger logger = worldgentest.instance().getLogger();

    private static final Function<Locale, ResourceBundle> LOOKUP_FUNC = new Function<Locale, ResourceBundle>() {
        @Nullable
        @Override
        public ResourceBundle apply(Locale input) {
            return ResourceBundle.getBundle("crazyTrees", input);
        }
    };

    private TranslationHelper() {}

    private static Locale logLanguage = Locale.getDefault();

    /**
     * returns the translation of the key (as text) in the language of the server
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated text
     */
    public static Text t(String key, Object... args) {
        return Text.of(s(key, args));
    }

    /**
     * returns the translation of the key in the language of the server
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated string
     */
    public static String s(String key, Object... args) {
        return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(args);
    }

    /**
     * returns the translation of the key in the given language
     * @param local the language
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated string
     */
    public static String s(Locale local, String key, Object... args) {
        try {
            return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(local, args);
        }catch(MissingFormatArgumentException e){
            if(!key.equals("warn.translation.too_many_arguments")){
                logger.warn(l("warn.translation.too_many_arguments", local.toLanguageTag(), key) + " (ERROR CODE: 1)");
            }else{
                logger.warn(String.format("Translation for \"warn.translation.too_many_arguments\" in language %s " +
                        "wants too many arguments", local.toLanguageTag()));
            }

            return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(local);
        }
    }

    /**
     * returns the translation of the key (as text) in the language of the player
     * @param player player that's language should be used
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated text
     */
    public static Text p(Player player, String key, Object... args){
        return Text.of(new ResourceBundleTranslation(key, LOOKUP_FUNC).get(player.getLocale(), args));
    }

    /**
     * returns the translation of the key in the log language
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated string
     */
    public static String l(String key, Object... args) {
        return s(logLanguage, key, args);
    }

    /**
     * sets the language for the logging to the give local
     * @param locale the local of the language
     */
    public static void setLogLanguage(Locale locale){
        logLanguage = locale;
    }
}
