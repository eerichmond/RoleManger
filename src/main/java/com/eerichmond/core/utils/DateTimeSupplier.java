package com.eerichmond.core.utils;

import com.google.common.base.Supplier;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * Returns the current Joda date and time. This class is used in place of the static DateTime.now() to allow for
 * Dependency Injection during testing.
 */
@Component
public class DateTimeSupplier implements Supplier<DateTime> {
	@Override
	public DateTime get() {
		return DateTime.now();
	}
}
