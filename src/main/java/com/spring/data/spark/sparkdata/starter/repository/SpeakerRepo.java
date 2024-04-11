package com.spring.data.spark.sparkdata.starter.repository;

import com.spring.data.spark.sparkdata.library_unsafe.repository.SparkRepository;
import com.spring.data.spark.sparkdata.starter.model.Speaker;

import java.util.List;


// write plugin for auto-complete
public interface SpeakerRepo extends SparkRepository<Speaker> {
        List<Speaker> findByAgeBetween(int min, int max);
        long findByAgeGreaterThanCount(int min);
}
