package com.eerichmond.core.codes;

@ConvertByCode
public enum FileTypeWithCode implements ControlCode {
	PDF ("PDF"),
	WORD ("DOC"),
	EXCEL ("XLS");

	private final String code;
	
	private FileTypeWithCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	public String getDescription() {
		return name();
	}

}
