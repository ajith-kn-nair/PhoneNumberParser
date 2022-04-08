package com.voxsmart.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NumberParserTest {
	
	static Map<String, Integer> countryCodes = Stream.of(new Object[][] { 
	     { "UK", 44 }, 
	     { "FR", 33 },
	     { "US", 1 }, 
	 }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
	
	static Map<String, String> nationalTrunkPrefixes = Stream.of(new String[][] {
		  { "UK", "0" }, 
		  { "FR", "0" },
		  { "US", "1" },
		}).collect(Collectors.toMap(data -> data[0], data -> data[1]));
	
	static NumberParser numberParser;

	@BeforeAll
	public static void setup() {
		numberParser = new NumberParser(countryCodes, nationalTrunkPrefixes);
	}
	
	@Test
	void testWithEmptyPhoneNumbers() {
		assertNull(numberParser.parse(null, null));
		assertNull(numberParser.parse("", ""));
		assertNull(numberParser.parse(" ", " "));
		assertNull(numberParser.parse("07277822334", " "));
		assertNull(numberParser.parse(null, "+447866866886"));
	}
	
	@Test
	void testUKtoUKParsing() {
		assertEquals(numberParser.parse("07277822334", "+447866866886"), "+447277822334");
	}
	
	@Test
	void testUStoUSParsing() {
		assertEquals(numberParser.parse("1312233244", "+1212233200"), "+1312233244");
	}
	
	@Test
	void testUKtoUSParsing() {
		assertEquals(numberParser.parse("+1312233244", "+447866866886"), "+1312233244");
	}
}
