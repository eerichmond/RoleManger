package com.eerichmond.core.codes;

import org.apache.commons.lang3.text.WordUtils;

public enum ProcessStatusCode implements ControlCode {
	NOT_STARTED,
	IN_PROGRESS,
	PENDING_VERIFICATION,
	COMPLETE;

	@Override
	public String getCode() { return name(); }

	@Override
	public String getDescription() {
		return WordUtils.capitalizeFully(name().replace("_", " "));
	}
	
}