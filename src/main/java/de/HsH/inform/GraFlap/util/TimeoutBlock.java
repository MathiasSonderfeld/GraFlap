package de.HsH.inform.GraFlap.util;

import de.HsH.inform.GraFlap.exception.GraFlapException;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class TimeoutBlock {

    private long timeoutSeconds=30;

    public TimeoutBlock(long timeoutSeconds){
        this.timeoutSeconds=timeoutSeconds;
    }

    public void addBlock(Callable<String> task) throws GraFlapException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(task);
        AtomicBoolean timeout = new AtomicBoolean(false);
        String reason =  "Most probable reason: endless loop in submission.";

        try {
           future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            timeout.set(true);
        }catch (ExecutionException  e) {
            if (!e.getCause().getMessage().contains("heap")) { reason =""; }
            throw new GraFlapException("Test terminated: " + e.getCause().getMessage() + reason);
        }
        catch (Exception  e) {
            throw new GraFlapException("Test terminated: " + e.getMessage());
        }
        executor.shutdownNow();

        if (timeout.get()){ throw new GraFlapException("Test execution time exceeded " + timeoutSeconds + " seconds. Test terminated." + reason); }

    }

}

