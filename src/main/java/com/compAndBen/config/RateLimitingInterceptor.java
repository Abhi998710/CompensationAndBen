package com.compAndBen.config;

package com.compAndBen.config;

import ch.qos.logback.core.joran.conditional.ElseAction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

//intercept the rate limiter here

//this works as the component and to inherit the properties from the field we

@Component
@PropertySource("classpath:security.properties")
public class RateLimitingInterceptor implements HandlerInterceptor {

    @Value("${rate.limit.enabled}")
    private boolean enabled;
    @Value("${rate.limit}")
    private int rateLimit;
    @Value("${rate.limit.time.frame}")
    private int timePeriod;
    @Value("${rate.limit.time.unit}")
    private String timeUnit;
    public Map<String,SimpleRateLimiter> limiters=new ConcurrentHashMap<>();
    //in the simpleratelimiting semaphore concept plays an important role everytime
    public boolean prehandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {
       //this is the prehandle method which is used to

        if(!enabled)
        {
            return true;
        }
        String ipadd=request.getHeader("X-FORWARDED-FOR");
        if(ipadd==null)
        {
           ipadd=request.getRemoteAddr();
           System.out.print(ipadd);
        }
        if(ipadd==null)
        {
            return true;
        }
        String clientid=ipadd;
        SimpleRateLimiter rateLimiter=getLimiter(clientid);
        return rateLimiter.tryAcquire();



    }
//if ipp address rate limiting is enable and request contains ip address
    //extract ipaddress as the client id and pass it to get limitter

    private SimpleRateLimiter getLimiter(String clientid)
    {
        //we are extracting the simplelimiter by using the ipaddress and that is the best thing
        if(limiters.containsKey(clientid))
        {
            return limiters.get(clientid);
        }
        else
        {
            synchronized (clientid.intern())
            {
                if(limiters.containsKey(clientid))
                {
                    return limiters.get(clientid);
                }
                SimpleRateLimiter limiter=createRateLimiter(clientid);
                limiters.put(clientid,limiter);
                return limiter;
                //its not accessible outside the bracket and that is the truth
            }


        }




    }
    private SimpleRateLimiter createRateLimiter(String clientId)
    {
        TimeUnit timeU=null;

        if(timeUnit.toString().trim().compareTo("MINUTES")==0)
        {
            timeU=TimeUnit.MINUTES;

        }
        else if(timeUnit.toString().trim().compareTo("SECONDS")==0)
        {
            timeU=TimeUnit.SECONDS;
        }
        else if(timeUnit.toString().trim().compareTo("HOURS")==0)
        {
            timeU=TimeUnit.HOURS;
        }
        return  SimpleRateLimiter.create(this.rateLimit*2,timeU,timePeriod);
        //we are taking asll the values and creating the ratelimiter by replenish method in the earlier method

    }





}