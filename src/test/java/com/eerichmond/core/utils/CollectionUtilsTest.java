package com.eerichmond.core.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class CollectionUtilsTest {

	@Test
	public void printZeroItemsReturnsEmptyString() {
		List<SampleLabelValue> collection = new ArrayList<SampleLabelValue>();
		
		assertEquals("", CollectionUtils.toSentence(collection));
	}
	
	@Test
	public void printOneItemReturnsItem() {
		@SuppressWarnings("serial")
		List<SampleLabelValue> collection = new ArrayList<SampleLabelValue>() {{ add(SampleLabelValue.FIRST); }};
		
		String actualResult = CollectionUtils.toSentence(collection);
		
		String expectedResult = SampleLabelValue.FIRST.toString();
		
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void printTwoItemsReturnsItemsAnded() {
		@SuppressWarnings("serial")
		List<SampleLabelValue> collection = new ArrayList<SampleLabelValue>() {{ add(SampleLabelValue.FIRST); add(SampleLabelValue.SECOND); }};
		
		String actualResult = CollectionUtils.toSentence(collection);
		
		String expectedResult = SampleLabelValue.FIRST.toString() + " and " + SampleLabelValue.SECOND.toString();
		
		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void printThreeItemsCollectionReturnsItemsCommaAndAnded() {
		@SuppressWarnings("serial")
		List<SampleLabelValue> collection = new ArrayList<SampleLabelValue>() {{ add(SampleLabelValue.FIRST); add(SampleLabelValue.SECOND); add(SampleLabelValue.THIRD); }};
		
		String actualResult = CollectionUtils.toSentence(collection);
		
		String expectedResult = SampleLabelValue.FIRST.toString() + ", " + SampleLabelValue.SECOND.toString() + " and "+ SampleLabelValue.THIRD.toString();
		
		assertEquals(expectedResult, actualResult);
	}

	private enum SampleLabelValue {
		FIRST,
		SECOND,
		THIRD
	}
}
