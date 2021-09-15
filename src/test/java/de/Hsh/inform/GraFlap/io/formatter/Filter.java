package de.HsH.inform.GraFlap.io.formatter;

public class Filter {
    public static String filter(String in){
        return in.replaceAll("datetime=\"[0-9]*\"", "datetime=\"\"").replaceAll("\"GraFlap\" version=\"[0-9.]*\"", "\"GraFlap\" version=\"\"").replaceAll("\\s{2,}", "").trim();
    }
}
