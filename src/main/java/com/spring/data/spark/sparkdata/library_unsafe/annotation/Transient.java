package com.spring.data.spark.sparkdata.library_unsafe.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


// Generally this annotation is used to prevent loading some fields from data classes
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {
}
