package de.HsH.inform.GraFlap;

import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RegexBuilder {

    public static String checkAndClean(String regExp) throws GraFlapException {
        regExp = regExp.replaceAll(" ", "");
        regExp = regExp.replaceAll("\n", "");
        regExp = regExp.replaceAll("\r", "");
        regExp = regExp.replaceAll("\t", "");
      //  regExp = regExp.replaceAll("E", "");


        String regsRegex = "([a-zA-Z]|0|1|2|3|4|5|6|7|8|9|\\||\\(|\\)|\\*)+";
        Pattern p = Pattern.compile(regsRegex);

        if (!(p.matcher(regExp).matches())) {
            throw new GraFlapException("ERROR - There is something wrong with your regex. " + regExp);
        }
         try {
            Pattern.compile(regExp);
        } catch (PatternSyntaxException exception) {
            throw new GraFlapException("ERROR - There is something wrong with your regex: " + exception.getDescription()  );
        }

            return regExp;
    }
}
