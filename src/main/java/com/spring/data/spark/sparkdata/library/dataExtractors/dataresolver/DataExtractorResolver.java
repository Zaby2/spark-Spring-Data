package com.spring.data.spark.sparkdata.library.dataExtractors.dataresolver;

import com.spring.data.spark.sparkdata.library.dataExtractors.DataExtractor;

import java.util.Map;

public class DataExtractorResolver {

    Map<String, DataExtractor> dataExtractorMap; // how to fill this map??
    public DataExtractor resolve(String pathToData) {
        String fileExtension = pathToData.split("\\.")[1];
        return dataExtractorMap.get(fileExtension);
    }

}
