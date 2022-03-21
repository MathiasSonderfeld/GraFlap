package de.HsH.inform.GraFlap.util;

import java.util.concurrent.*;

import de.HsH.inform.GraFlap.exception.GraFlapException;

public class TimeoutBlock {

	//since this value is set by constructor mandatorily
    private long timeoutSeconds=Long.MAX_VALUE;

    public TimeoutBlock(long timeoutSeconds){
        this.timeoutSeconds=timeoutSeconds;
    }

    public void addBlock(Callable<String> task) throws GraFlapException {
        FutureTask<String> futureTask = new FutureTask<>(task);
        Thread gradeThread = new Thread(futureTask);
        String reason =  "Most probable reason: endless loop in submission.";
        gradeThread.start();
        try{
        	//try grading within timeout
        	futureTask.get(timeoutSeconds, TimeUnit.SECONDS);

        }
        catch (TimeoutException e){
        	//if timeout exceeded
             throw new GraFlapException("Test execution time exceeded " + timeoutSeconds + " seconds. Test terminated. " + reason);
         }
        catch (ExecutionException  e){
        	//if execution error occurred
            if(!e.getCause().getMessage().contains("heap")){
            	reason="";
            }
            throw new GraFlapException("Test terminated: " + e.getCause().getMessage() + ". " + reason);
        }
        catch (Exception  e){
        	//anything else went wrong
            throw new GraFlapException("Test terminated: " + e.getMessage());
        }finally{
            gradeThread.interrupt();
            gradeThread.stop();
        }

    }

}

