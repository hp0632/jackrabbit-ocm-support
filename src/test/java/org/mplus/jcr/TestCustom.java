package org.mplus.jcr;

import java.util.Collection;

import org.junit.Test;
import org.mplus.jcr.custom.AnnotationJcr;
import org.mplus.jcr.test.vo.Custom;

public class TestCustom extends AnnotationJcr<Custom>{

	@Test
	public void insert(){
		Custom c = createCus();
		insert(c);
	}
	
	@Test
	public void testSearchs(){
		Custom c = createCus();
		Collection<Custom> cc = findAll(c.getClass());
		for (Custom custom : cc) {
			System.out.println(custom.getPath());
		}
	}
	
	public Custom createCus(){
		Custom custom = new Custom();
		custom.setAge(23);
		custom.setName("custom");
		custom.setPath("/custom/abc");
		return custom;
	}
}
