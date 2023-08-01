
package com.compAndBen.config;
import com.compAndBen.exception.TooManyRequestException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SimpleRateLimiter {

    public  Semaphore semaphore;
    private int maxPermits;
    private TimeUnit timeUnit;
    private ScheduledExecutorService scheduler;
    private int timeperiod;

    //before the object execution you need to create one more class and make things work
    //this class is static class and has lot of things in it
    public static SimpleRateLimiter create(int maxPermits, TimeUnit timeUnit, int timeperiod)
    {
        SimpleRateLimiter limiter=new SimpleRateLimiter(maxPermits,timeUnit,timeperiod);
        limiter.schdulePermitReplenishment();
        return limiter;
    }


    private  SimpleRateLimiter( int maxPermits, TimeUnit timeUnit, int timeperiod) {

        this.maxPermits = maxPermits;
        this.timeUnit = timeUnit;
        this.timeperiod = timeperiod;
    }
    public void schdulePermitReplenishment()
    {
        scheduler= Executors.newScheduledThreadPool(10);
        //that mean 10 threads can run at a time
        scheduler.scheduleAtFixedRate(()->
        {
            semaphore.release(maxPermits-semaphore.availablePermits());
        },0,timeperiod,timeUnit);

    }
    public boolean tryAcquire()
    {
        if(!semaphore.tryAcquire())
        {
            throw new TooManyRequestException("Too Many request for resource");
        }
        return semaphore.tryAcquire();
    }
    //last task is to stop the schedule limiter and make the changes to the application all around

    public void stop()
    {
        scheduler.shutdownNow();
    }





}