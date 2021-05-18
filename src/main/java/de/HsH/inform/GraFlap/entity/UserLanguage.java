package de.HsH.inform.GraFlap.entity;

public enum UserLanguage {
    German, English;

    public static UserLanguage get(String langTag){
        switch(langTag.toLowerCase()){
            case "de":
                return German;

            case "en":
            default:
                return English;
        }
    }
}
