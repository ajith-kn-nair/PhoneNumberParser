package com.voxsmart.parser;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class NumberParser {
	private static final String PLUS = "+";
	private static final String PLUS_REGEX = "\\+";
	
	Map<String, Integer> countryCodes;
	Map<String, String> nationalTrunkPrefixes;
	
	public NumberParser(Map<String, Integer> countryCodes, Map<String, String> nationalTrunkPrefixes) {
		this.countryCodes = countryCodes;
		this.nationalTrunkPrefixes = nationalTrunkPrefixes;
	}
	
	public String parse(String dialledNumber, String userNumber) {
	
		if(StringUtils.isNotBlank(dialledNumber) && StringUtils.isNotBlank(userNumber)) {
			
			if(dialledNumber.startsWith(PLUS)) return dialledNumber;
			
			String country = null;
			String countryCode = null;
			
			for (Map.Entry<String, Integer> entry : countryCodes.entrySet()) {
		        if(userNumber.replaceAll(PLUS_REGEX, StringUtils.EMPTY).startsWith(String.valueOf(entry.getValue()))) {
		        	country = entry.getKey();
		        	countryCode = String.valueOf(entry.getValue());
		        }
		    }
			
			return buildParsedPhoneNumber(dialledNumber, countryCode, findCountryPrefix(country));
		}
		
		return null;
	}

	private String findCountryPrefix(String country) {
		String prefix = null;
		
		for (Map.Entry<String, String> entry : nationalTrunkPrefixes.entrySet()) {
		    if(country.equals(entry.getKey())) {
		    	prefix = entry.getValue();
		    }
		}
		
		return prefix;
	}

	private String buildParsedPhoneNumber(String dialledNumber, String countryCode, String prefix) {
		return PLUS+countryCode+dialledNumber.replaceFirst(prefix, StringUtils.EMPTY);
	}
}
