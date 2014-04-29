package org.mplus.jcr.utils;


public class JsonUtils {

	public static boolean IsJson(String input){ 
	    input = input.trim(); 
	    return input.startsWith("{") && input.endsWith("}")  
	           || input.startsWith("[") && input.endsWith("]"); 
	} 
}
