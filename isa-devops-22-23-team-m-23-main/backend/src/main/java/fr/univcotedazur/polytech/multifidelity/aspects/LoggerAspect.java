package fr.univcotedazur.polytech.multifidelity.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggerAspect.class);
    private static final String PREFIX = "MFC:Rest-Controller:";

    @Pointcut("execution(public * fr.univcotedazur.polytech.multifidelity.controllers..*(..))")
    private void allControllerMethods() {
    } // This enables to attach the pointcut to a method name we can reuse below

    @Before("allControllerMethods()")
    public void logMethodNameAndParametersAtEntry(JoinPoint joinPoint) {
        String prefix = PREFIX + joinPoint.getThis() + ": ";
        String message = prefix + "Called " + joinPoint.getSignature().getName() + " with parameters: " + Arrays.toString(joinPoint.getArgs());
        LOG.info(message);
    }

    @AfterReturning(pointcut = "allControllerMethods()", returning = "resultVal")
    public void logMethodReturningProperly(JoinPoint joinPoint, Object resultVal) {
        String prefix = PREFIX + joinPoint.getThis() + ": ";
        String message = prefix + "Returned " + joinPoint.getSignature().getName() + " with value: " + resultVal;
        LOG.info(message);
    }

    @AfterThrowing(pointcut = "allControllerMethods()", throwing = "exception")
    public void logMethodException(JoinPoint joinPoint, Exception exception) {
        String prefix = PREFIX + joinPoint.getThis() + ": ";
        String message = prefix + "Exception from " + joinPoint.getSignature().getName() + " with exception: " + exception;
        LOG.warn(message);
    }
}
