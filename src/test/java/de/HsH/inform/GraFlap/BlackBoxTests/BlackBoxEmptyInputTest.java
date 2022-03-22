package de.HsH.inform.GraFlap.BlackBoxTests;

import de.HsH.inform.GraFlap.GraFlap;
import de.HsH.inform.GraFlap.TestwordsUtil;
import de.HsH.inform.GraFlap.entity.Arguments;
import de.HsH.inform.GraFlap.entity.Mode;
import de.HsH.inform.GraFlap.entity.Type;
import de.HsH.inform.GraFlap.exception.GraFlapException;
import org.junit.jupiter.api.*;

import java.util.*;


public class BlackBoxEmptyInputTest{

    @TestFactory
    Collection<DynamicTest> testEmptyInputs(){
        HashSet<DynamicTest> tests = new HashSet<>();
        for(Mode mode : Mode.values()){
            Arguments arguments = new Arguments();
            arguments.setTaskTitle("EmptyInput Test");
            arguments.setUserLanguage(Locale.GERMAN);
            arguments.setReference("");
            arguments.setMode(mode);
            arguments.setType(Type.NON);
            arguments.setTestwords(TestwordsUtil.emptyTestwords);
            arguments.setNumberOfWords(50);
            arguments.setStudentAnswer("");
            tests.add(DynamicTest.dynamicTest("EmptyInput Test - " + mode.name(), () -> {
                GraFlapException e = Assertions.assertThrows(GraFlapException.class, () -> GraFlap.processSubmission(arguments));
                Assertions.assertTrue( e.getMessage().contains("Test terminated"));
            }));
        }
        return tests;
    }
}
