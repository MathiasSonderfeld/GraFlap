package de.HsH.inform.GraFlap.loncapa.scoring;

import de.HsH.inform.GraFlap.loncapa.exception.GraFlapException;

/**
 *  Abstract class to generate the result as an index how many of the generated test word are tested correctly
 *  @author Ufuk Tosun (07-26-2012)
 *  @author Benjamin Held (04-09-2016)
 *  @since 09-17-2016
 *  @version 0.4.4
 */
public abstract class ScoringTest<T> {

    /**
     * the provided object when the constructor was called
     */
    protected T object;
    /**
     * the resulting score
     */
    protected int resultScore;

    /**
     * constructor which takes a generic object
     * @param object the object that should be used
     */
    protected ScoringTest(T object) {
        this.object = object;
        this.resultScore = 0;
    }

    public int returnScore() {
        return resultScore;
    }

    /**
     * abstract method where the words are tested
     * @throws GraFlapException throws a {@link GraFlapException} that occurs further within the calling hierarchy
     */
    protected abstract void testing() throws GraFlapException;

    /**
     * private method to calculate the resulting score
     * @return the calculated score
     */
    protected abstract int calculateResultScore();
}