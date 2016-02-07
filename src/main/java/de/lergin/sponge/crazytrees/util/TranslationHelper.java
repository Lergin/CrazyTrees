package de.lergin.sponge.crazytrees.util;

import com.google.inject.Inject;
import de.lergin.sponge.crazytrees.CrazyTreesMain;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.ResourceBundleTranslation;
import org.spongepowered.api.text.translation.Translatable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * class for translating messages
 */
public final class TranslationHelper {

    @Inject
    private static Logger logger = CrazyTreesMain.instance().getLogger();

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
     * returns the translation of the key (as text) in the given language
     * @param local the language
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated text
     */
    public static Text t(Locale locale, String key, Object... args) {
        return Text.of(s(locale, key, args));
    }

    /**
     * returns the translation of the key in the language of the server
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated string
     */
    public static String s(String key, Object... args) {
        return s(Locale.getDefault(), key, args);
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
            ArrayList<Object> argsTranslated = new ArrayList<>();

            for(Object arg: args){
                if(arg instanceof Translatable){
                    arg = ((Translatable) arg).getTranslation().get(local);
                }

                argsTranslated.add(arg);
            }


            return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(local, argsTranslated.toArray());
        }catch(MissingFormatArgumentException e){
            // we need to check if the message is our error message so we are not creating a endless loop
            if(key.equals("warn.translation.too_many_arguments")){
                logger.warn(String.format("Translation for \"warn.translation.too_many_arguments\" in language \"%s\" " +
                        "wants too many arguments", local.toLanguageTag()));
            }else{
                logger.warn(l("warn.translation.too_many_arguments", local.toLanguageTag(), key) + " (ERROR CODE: 1)");
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
        if(ConfigHelper.getNode("translation", "usePlayerLanguage").getBoolean()){
            return t(player.getLocale(), key, args);
        }else{
            return t(key, args);
        }

    }

    /**
     * returns the translation of the key (as string) in the language of the player
     * @param player player that's language should be used
     * @param key resourceBundle key
     * @param args replace arguments
     * @return the translated string
     */
    public static String ps(Player player, String key, Object... args){
        if(ConfigHelper.getNode("translation", "usePlayerLanguage").getBoolean()){
            return s(player.getLocale(), key, args);
        }else{
            return s(key, args);
        }

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
