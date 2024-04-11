package com.spring.data.spark.sparkdata;

import com.spring.data.spark.sparkdata.library_unsafe.wordsresolver.WordResolverImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


class ApplicationTests {

	@Test
	void wordResolverMethodTest() {
		WordResolverImpl wordResolver = new WordResolverImpl();
		String [] expectedResult = {"find", "By", "Name", "Of"};
		Assert.assertArrayEquals(expectedResult, wordResolver.resolveJavaMethodWords("findByNameOf").toArray());
	}

}
