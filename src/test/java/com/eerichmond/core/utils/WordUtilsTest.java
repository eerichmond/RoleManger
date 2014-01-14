package com.eerichmond.core.utils;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class WordUtilsTest {

	@Test
	public void properCase_allUpperCase() {
		assertEquals("This Is Proper Cased", WordUtils.properCase("THIS IS PROPER CASED"));
	}

	@Test
	public void properCase_allLowerCase() {
		assertEquals("This Is Proper Cased", WordUtils.properCase("this is proper cased"));
	}
	
	@Test
	public void ProperCase_preservesPadding() {
		assertEquals("  Preserves Padding  ", WordUtils.properCase("  preserves padding  "));
	}

	@Test
	public void properCase_excludesLowerCaseWords() {
		assertEquals("The Cat Jumped over a Cow", WordUtils.properCase("THE CAT JUMPED OVER A COW"));
	}

	@Test
	public void properCase_excludesAcronyms() {
		assertEquals("U.S. US II IV", WordUtils.properCase("U.S. US II IV"));
	}

	@Test
	public void properCase_ignoresApostrophes() {
		assertEquals("Mr McDonalds's Car", WordUtils.properCase("mr Mcdonalds's car"));
	}

	@Test
	public void dasherize_sentence_returnsDashesInsteadOfSpaces() throws Exception {
		assertThat(WordUtils.dasherize("This is a sentence"), is("this-is-a-sentence"));
	}

}
