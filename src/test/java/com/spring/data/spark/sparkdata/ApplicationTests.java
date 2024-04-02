package com.spring.data.spark.sparkdata;

import com.spring.data.spark.sparkdata.library.wordsresolver.WordResolverImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


class ApplicationTests {

	@Test
	void contextLoads() {
		WordResolverImpl wordResolver = new WordResolverImpl();
		String [] expectedResult = {"find", "By", "Name", "Of"};
		Assert.assertArrayEquals(expectedResult, wordResolver.resolveJavaMethodWords("findByNameOf").toArray());
	}

}
