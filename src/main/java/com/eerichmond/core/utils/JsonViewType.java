package com.eerichmond.core.utils;

public class JsonViewType {
	/**
	 * Basic information
	 */
	public static class Basic { }

	/**
	 * User for detail and edit of entity
	 */
	public static class Detail extends Basic { }


	/**
	 * includes create & modified information
	 */
	public static class FullDetail extends Detail { }
}
