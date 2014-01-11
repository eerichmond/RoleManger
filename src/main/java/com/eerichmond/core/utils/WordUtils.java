package com.eerichmond.core.utils;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WordUtils {
	private static final Set<String> LOWER_CASE_WORDS = Sets.newHashSet( "a", "an", "the", "and", "but", "or", "nor", "for", "so", "yet",
		"about", "above", "across", "after", "against", "along", "among", "around", "at", "before", "behind", "below", "beneath",
		"beside", "between", "beyond", "but", "by", "despite", "down", "during", "except", "for", "from", "in", "inside", "into",
		"like", "near", "of", "off", "on", "onto", "out", "outside", "over", "past", "since", "through", "throughout", "till", "to",
		"toward", "under", "underneath", "until", "up", "upon", "with", "within", "without" );

	private static final Set<String> UPPER_CASE_WORDS = Sets.newHashSet(
		"USA", "DN", "VA", "US", "EU", "CA", "HQ", "I", "II", "III", "IV", "VI", "VII", "VIII", "IX", "XI", "XII", "XIII",
        "VC", "GM", "VP", "TV" );

	private static final Set<String> UPPER_CASE_PREFIXES = Sets.newHashSet( "Mc", "O'" );

	private WordUtils() { }
	
	/**
	 * Takes in a sentence and returns best guesses at proper casing.
	 * @param sentence the sentence/name to be proper case
	 * @return the sentence/name in proper case
	 */
	public static String properCase(String sentence) {
		if (StringUtils.isEmpty(sentence)) {
			return sentence;
		}

		Pattern pattern = Pattern.compile("\\b([\\w']+)\\b");
		Matcher matcher = pattern.matcher(sentence);
		StringBuffer sb = new StringBuffer(sentence.length());
		
		while(matcher.find()) {
			String word = properCaseWord(matcher.group(1));
			matcher.appendReplacement(sb, word);
		}
		
		matcher.appendTail(sb);
		
		// Always upper case the first character;
		return StringUtils.capitalize(sb.toString());
	}

	/**
	 * Proper cases an individual word.
	 * @param word the word to proper case
	 * @return the proper cased word.
	 */
	private static String properCaseWord(String word) {
		String wordUpperCase = word.toUpperCase(Locale.getDefault());
		String wordLowerCase = word.toLowerCase(Locale.getDefault());
		
		// Is this word defined as fixed-case word?
		if (UPPER_CASE_WORDS.contains(wordUpperCase)) {
			return wordUpperCase;
		}
		
		// Is this word defined as all-lower case?
		if (LOWER_CASE_WORDS.contains(wordLowerCase)) {
			return wordLowerCase;
		}

		// Ok, this is a normal word; upper case the first letter
		if (word.length() == 1) {
			return wordUpperCase;
		}

		String wordCapitalized = StringUtils.capitalize(wordLowerCase);

		// Check if this word starts with one of the upper-casing prefixes
		// Note: Only one of the upper-casing prefixes applies.
		for (String prefix : UPPER_CASE_PREFIXES) {
			if (wordCapitalized.startsWith(prefix) && word.length() > prefix.length()) {
				return prefix +	StringUtils.capitalize(wordCapitalized.substring(prefix.length()));
			}
		}

		return wordCapitalized;
	}

	/**
	 * Converts a sentence to a URL friendly lowercase slug where spaces are replaced with dashes.
	 * @param sentence the sentence to convert to a slug.
	 */
	public static String dasherize(String sentence) {
		if (StringUtils.isEmpty(sentence)) {
			return sentence;
		}

		return sentence.toLowerCase().replaceAll("[^\\w\\d-]", "-");
	}

	/**
	 * Converts a dashed or underscored string into a sentence with spaces.
	 * @param sentence the dashed/underscored sentence to convert to a human readable sentence.
	 */
	public static String humanize(String sentence) {
		if (StringUtils.isEmpty(sentence)) {
			return sentence;
		}

		return StringUtils.capitalize(sentence.replaceAll("[-_]", " "));
	}
}
