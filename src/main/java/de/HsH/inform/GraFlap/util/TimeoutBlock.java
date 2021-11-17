package de.HsH.inform.GraFlap.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.HsH.inform.GraFlap.exception.GraFlapException;

public class TimeoutBlock {

	//since this value is set by constructor mandatorily
    private long timeoutSeconds=Long.MAX_VALUE;

    public TimeoutBlock(long timeoutSeconds){
        this.timeoutSeconds=timeoutSeconds;
    }

    public void addBlock(Callable<String> task) throws GraFlapException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task);
        //AtomicBoolean timeout = new AtomicBoolean(false);
        String reason =  "Most probable reason: endless loop in submission.";

        try{
        	//try grading within timeout 
        	future.get(timeoutSeconds, TimeUnit.SECONDS);
        }catch (TimeoutException e){
        	//if timeout exceeded
            //future.cancel(true);
            throw new GraFlapException("Test execution time exceeded " + timeoutSeconds + " seconds. Test terminated. " + reason);
            //timeout.set(true);
        }catch (ExecutionException  e){
        	//if execution error occurred 
            if(!e.getCause().getMessage().contains("heap")){
            	reason="";
            }
            throw new GraFlapException("Test terminated: " + e.getCause().getMessage() + ". " + reason);
        }catch (Exception  e){
        	//anything else went wrong
            throw new GraFlapException("Test terminated: " + e.getMessage());
        }finally{
        	//anyways cleanup
        	future.cancel(true);
        	executor.shutdownNow();
        }

        //if (timeout.get()){ throw new GraFlapException("Test execution time exceeded " + timeoutSeconds + " seconds. Test terminated. " + reason); }

    }

}

