package com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.invocationHandlerFactory;

import com.spring.data.spark.sparkdata.library_unsafe.annotation.Source;
import com.spring.data.spark.sparkdata.library_unsafe.annotation.Transient;
import com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.DataExtractor;
import com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.dataresolver.DataExtractorResolver;
import com.spring.data.spark.sparkdata.library_unsafe.dataExtractors.spider.TransformationSpider;
import com.spring.data.spark.sparkdata.library_unsafe.finalizers.Finalizer;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.SparkInvocationHandler;
import com.spring.data.spark.sparkdata.library_unsafe.invocationHandler.sparkTransformations.SparkTransformation;
import com.spring.data.spark.sparkdata.library_unsafe.repository.SparkRepository;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolver;
import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolverImpl;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

// how to set all this fields?
@Component
@RequiredArgsConstructor
public class SparkInvocationHandlerFactory {

    private final Map<String, TransformationSpider> transformationSpiderMap;
    private final Map<String, Finalizer> finalizerMap;
    private final DataExtractorResolver resolver;

    @Setter
    private ConfigurableApplicationContext realContext;



    private List<SparkTransformation> transformations = new ArrayList<>();
    private WordResolver wordResolver = new WordResolverImpl();

    private Map<Method, Finalizer> methodFinalizerMap = new HashMap<>();



    public SparkInvocationHandler createSparkInvocationHandler(Class<? extends SparkRepository> sparkRepositoryImpl) {
        Class<?> modelClass = getModelClass(sparkRepositoryImpl);
        String pathToData = modelClass.getAnnotation(Source.class).value();
        Set<String> fieldNames = getFieldNames(sparkRepositoryImpl);
        DataExtractor dataExtractor = resolver.resolve(pathToData);
        Map<Method, List<SparkTransformation>> transformationChain = new HashMap<>();
        Method [] methods = sparkRepositoryImpl.getMethods();
        String finalizerType = "collect"; // def val for finalizers
        for (Method method : methods) {
            TransformationSpider transformationSpider = null;
            List<String> methodWords = wordResolver.resolveJavaMethodWords(method.getName());
            while(methodWords.size() > 1) {
                String transformationSpiderName = wordResolver.findAndRemoveMatchingMethodNamesIfExists(transformationSpiderMap.keySet(), methodWords);
                if(!transformationSpiderName.isEmpty()) {
                    transformationSpider = transformationSpiderMap.get(transformationSpiderName); // here we will always switch to the valid strategy
                }
                transformations.add(transformationSpider.getTransformation(methodWords ,fieldNames ));
            }
            transformationChain.put(method, transformations);
            if(methodWords.size() == 1) finalizerType = Introspector.decapitalize(methodWords.remove(0));
            methodFinalizerMap.put(method, finalizerMap.get(finalizerType));
        }



        return SparkInvocationHandler.builder()
                .model(modelClass)
                .pathToData(pathToData)
                .dataExtractor(dataExtractor)
                .transformationChain(transformationChain)
                .finalizerMap(methodFinalizerMap)
                .context(realContext)
                .build();

    }

    private Set<String> getFieldNames(Class<?> modelClass) {
        return Arrays.stream(modelClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !Collection.class.isAssignableFrom(field.getType())) // This part avoid downloading collections as fields
                .map(Field::getName)
                .collect(Collectors.toSet());
    }

    private Class<?> getModelClass(Class<? extends SparkRepository> sparkRepositoryImpl) {
        ParameterizedType parameterizedType = (ParameterizedType) sparkRepositoryImpl.getGenericInterfaces()[0];
        return  (Class<?>) parameterizedType.getActualTypeArguments()[0];

    }
}
