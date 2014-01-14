package com.eerichmond.core.codes;

import com.eerichmond.core.domain.StringToEnumConverter;
import org.junit.Test;
import org.springframework.core.convert.converter.Converter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class ControlCodeConverterFactoryTest {

	@Test
	public void getConverter_forFileTypeWithCode_returnsControlCodeConverterByCode() {
		ControlCodeConverterFactory factory = new ControlCodeConverterFactory();
		
		Converter<String, FileTypeWithCode> converter = factory.getConverter(FileTypeWithCode.class);
		
		assertThat(converter, is(instanceOf(ControlCodeConverterFactory.ControlCodeConverterByCode.class)));
	}
	
	@Test
	public void getConverter_forFileType_returnsStringToEnumConverter() {
		ControlCodeConverterFactory factory = new ControlCodeConverterFactory();
		
		Converter<String, FileType> converter = factory.getConverter(FileType.class);
		
		assertThat(converter, is(instanceOf(StringToEnumConverter.class)));
	}

	@Test
	public void getConverter_forSystemTypeIncorrectlyTaggedConvertByCode_returnsStringToEnumConverter() {
		ControlCodeConverterFactory factory = new ControlCodeConverterFactory();
		
		Converter<String, SystemType> converter = factory.getConverter(SystemType.class);
		
		assertThat(converter, is(instanceOf(StringToEnumConverter.class)));
	}
	
}
