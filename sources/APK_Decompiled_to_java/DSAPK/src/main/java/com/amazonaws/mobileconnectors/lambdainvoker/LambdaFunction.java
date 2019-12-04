package com.amazonaws.mobileconnectors.lambdainvoker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LambdaFunction {
    String functionName() default "";

    String invocationType() default "RequestResponse";

    String logType() default "None";

    String qualifier() default "";
}
