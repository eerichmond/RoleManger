package com.eerichmond.core.utils;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;

public final class CollectionUtils {
	
	private CollectionUtils() {}

    /**
     * Converts a collection of objects into a collection of strings by calling toString on each object.
     * @param items the objects to convert
     * @return A collection of strings
     */
    public static Collection<String> convertToStrings(Iterable<?> items) {
        Iterable<String> codes = Iterables.transform(items, new Function<Object, String>() {
            public String apply(Object item) {
                return item.toString();
            }
        });

        return Lists.newArrayList(codes);
    }

	/**
	 * Converts a collection of objects into a human readable list. Conjunction defaults to
	 * "and". Example
	 * ["item1"] = "item1"
	 * ["item1","item2"] = "item1 and item2"
	 * ["item1","item2","item3"] = "item1, item2 and item3"
	 * @param iterable a collection of LabelValue objects
	 * @return a string representing a human readable list.
	 */
	public static String toSentence(Iterable<?> iterable) {
		return toSentence(iterable, "and");
	}
	
	/**
	 * Converts a collection of objects into a human readable list. For example
	 * ["item1"] = "item1"
	 * ["item1","item2"] = "item1 and item2"
	 * ["item1","item2","item3"] = "item1, item2 and item3"
	 * @param iterable a collection of LabelValue objects
	 * @param conjunction the conjunction for the sentence (eg "and", "or", "nor")
	 * @return a string representing a human readable list.
	 */
	public static String toSentence(Iterable<?> iterable, String conjunction) {
		if (iterable == null) {
			return null;
		}
		
		StringBuilder sentence = new StringBuilder();

		boolean firstItem = true;
		
		for (Iterator<?> it = iterable.iterator(); it.hasNext();) {
			Object label = it.next();
			
			if (!firstItem) {
				sentence
					.append(it.hasNext() ? "," : " " + conjunction)
					.append(' ');
			}
			else {
				firstItem = false;
			}
			
			sentence.append(label);
		}
		
		return sentence.toString();
	}

}
