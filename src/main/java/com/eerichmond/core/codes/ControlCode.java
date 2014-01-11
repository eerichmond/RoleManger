package com.eerichmond.core.codes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect(
	fieldVisibility = JsonAutoDetect.Visibility.NONE,
	getterVisibility = JsonAutoDetect.Visibility.NONE,
	isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
@JsonSerialize(using = ControlCodeSerializer.class)
public interface ControlCode {
	
	/**
	 * The unique string representation of the code object.
	 * @return
	 */
	@JsonProperty
	String getCode();

	/**
	 * Returns a friendly description (can contain spaces) of the code.
	 * @return
	 */
	@JsonProperty
	String getDescription();
	
}
