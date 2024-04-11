package com.spring.data.spark.sparkdata.starter.model;

import com.spring.data.spark.sparkdata.library_unsafe.annotation.Source;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Source("data/speakers.json") // point to bd file or sth;
public class Speaker {
    private String name;
    private int age;
}
