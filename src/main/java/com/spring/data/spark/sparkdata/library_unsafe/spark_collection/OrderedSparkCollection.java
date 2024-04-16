package com.spring.data.spark.sparkdata.library_unsafe.spark_collection;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;


public class OrderedSparkCollection<T> {
    private List<T> args = new ArrayList<>();

    public OrderedSparkCollection(Object[] args) {
        this.args = new ArrayList(asList(args));
    }

    public T takeAndRemove() {
        return args.remove(0);
    }
}
