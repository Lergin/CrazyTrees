package de.lergin.sponge.worldgentest.util;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.ResourceBundleTranslation;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
 * Created by Malte on 23.01.2016.
 */
public class TranslationHelper {
    private static final Function<Locale, ResourceBundle> LOOKUP_FUNC = new Function<Locale, ResourceBundle>() {
        @Nullable
        @Override
        public ResourceBundle apply(Locale input) {
            return ResourceBundle.getBundle("crazyTrees", input);
        }
    };

    private TranslationHelper() {}

    public static Text t(String key, Object... args) {
        return Text.of(s(key, args));
    }

    public static String s(String key, Object... args) {
        return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(args);
    }

    public static String s2(String key, Object... args) {
        return new ResourceBundleTranslation(key, LOOKUP_FUNC).get(Locale.GERMAN, args);
    }
}
