package com.eerichmond.core.codes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ControlCodeConverterByCodeTest {
	
	private final ControlCodeConverterFactory factory = new ControlCodeConverterFactory();
	private final ControlCodeConverterFactory.ControlCodeConverterByCode<FileTypeWithCode> converter =
			factory.new ControlCodeConverterByCode(FileTypeWithCode.class);

	@Test
	public void convert_forEnumCodeXLS_returnsFileTypePdf() {
		assertEquals(converter.convert("XLS"), FileTypeWithCode.EXCEL);
	}
	
	@Test
	public void convert_forEnumCodeXls_returnsFileTypePdf() {
		assertEquals(converter.convert("Xls"), FileTypeWithCode.EXCEL);
	}
	
	@Test
	public void convert_forEnumNameExcel_returnsNull() {
		assertNull(converter.convert("EXCEL"));
	}
	
	@Test
	public void convert_InvalidFileType_returnsNull() {
		assertNull(converter.convert("InvalidFileType"));
	}

}
