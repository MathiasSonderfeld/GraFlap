package de.HsH.inform.GraFlap.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleControl extends ResourceBundle.Control {

    @Override
    public Locale getFallbackLocale( String baseName, Locale locale){
        return Locale.ROOT;
    }
}
