package org.mplus.jcr.core.init;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface InitRepository {

	public List<Class> loadCrudAnnotaionClass();
	
	public String[] loadCrudXmlClass();
	
}
