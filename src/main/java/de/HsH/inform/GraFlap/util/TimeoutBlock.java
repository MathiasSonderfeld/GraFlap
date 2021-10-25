package de.HsH.inform.GraFlap.util;

import de.HsH.inform.GraFlap.exception.GraFlapException;

public class TimeoutBlock {

    private final long timeoutMilliSeconds;
    private long timeoutInteval=100;

    public TimeoutBlock(long timeoutMilliSeconds){
        this.timeoutMilliSeconds=timeoutMilliSeconds;
    }

    public void addBlock(Runnable runnable) throws GraFlapException {
        long collectIntervals=0;
        Thread timeoutWorker=new Thread(runnable);
        timeoutWorker.start();
        do{
            if(collectIntervals>=this.timeoutMilliSeconds){
                timeoutWorker.interrupt();
                throw new GraFlapException("Test execution time exceeded " + timeoutMilliSeconds +" milli seconds. Test terminated.");
            }
            collectIntervals+=timeoutInteval;
            try {
                Thread.sleep(timeoutInteval);
            }catch(InterruptedException e){
                // do nothing
            }

        }while(timeoutWorker.isAlive() && !timeoutWorker.isInterrupted());

    }

    /**
     * @return the timeoutInteval
     */
    public long getTimeoutInteval() {
        return timeoutInteval;
    }

    /**
     * @param timeoutInteval the timeoutInteval to set
     */
    public void setTimeoutInteval(long timeoutInteval) {
        this.timeoutInteval = timeoutInteval;
    }

}
