package com.eerichmond.core.codes;

public enum FileType implements ControlCode {
	PDF ("PDF"),
	WORD ("DOC"),
	EXCEL ("XLS");

	private final String code;
	
	private FileType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return name();
	}

}
