package de.HsH.inform.GraFlap.convert;

import de.HsH.inform.GraFlap.JflapWrapper.entity.Submission;
import de.HsH.inform.GraFlap.entity.SubmissionType;
import de.HsH.inform.GraFlap.exception.GraFlapException;

/**
 * Helper class with static method to read and convert the given submission string for derivation input
 * @author Benjamin Held (07-27-2016)
 * @since 08-02-2016
 * @version 0.1.0
 */
public class DerivationParser {

    public static Submission<String[]> openDerivation(String submissionString) throws GraFlapException {
        String[] input = submissionString.split("=>");
        if (input.length == 0) {
            throw new GraFlapException("Error [DerivationParser]: derivation is empty.");
        }

        return new Submission<>(submissionString, input, SubmissionType.DERIVATION);
    }
}
