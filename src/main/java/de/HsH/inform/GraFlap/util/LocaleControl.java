package de.HsH.inform.GraFlap.util;

import de.HsH.inform.GraFlap.GraFlap;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Changes the ResourceBundle Locale behaviour to defaulting to Locale.Root, not to System Default
 * @author Mathias Sonderfeld (07-2021)
 * @version {@value GraFlap#version}
 */
public class LocaleControl extends ResourceBundle.Control {

    @Override
    public Locale getFallbackLocale( String baseName, Locale locale){
        return Locale.ROOT;
    }
}
