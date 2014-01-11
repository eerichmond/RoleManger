package com.eerichmond.core.codes;

import org.apache.commons.lang3.text.WordUtils;

public enum ActiveStatusCode implements ControlCode {
	IN_PROGRESS,
	PENDING_APPROVAL,
	ACTIVE,
	INACTIVE,
	RETIRED;

	@Override
	public String getCode() { return name(); }

	@Override
	public String getDescription() { return WordUtils.capitalizeFully(name().replace("_", " ")); }
}
