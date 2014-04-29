package org.mplus.jcr;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;

import org.mplus.jcr.utils.PropertyUtil;

public class Test {

	
	@org.junit.Test
	public void test() throws IOException{
		Properties pro = PropertyUtil.load("./src/test/java/org/mplus/jcr/jcr.properties");
		System.out.println(pro.getProperty("password") +"----------");
	}
	
	
	@org.junit.Test
	public void testClasses() throws ClassNotFoundException{
	}
	
}
