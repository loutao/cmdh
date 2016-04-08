package com.ratio.deviceService.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Mesogene on 2015/5/19.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Expose {
    public abstract boolean deserialize();

    public abstract boolean serialize();
}