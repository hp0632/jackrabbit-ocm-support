package org.mplus.jcr.custom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mplus.jcr.core.init.InitRepository;
import org.mplus.jcr.utils.JcrContant;
import org.mplus.jcr.utils.PropertyUtil;

@SuppressWarnings("rawtypes")
public class SimpleInitRepository implements InitRepository{
	
	private static final Log log = LogFactory.getLog(SimpleInitRepository.class);
	
	private Map<String,List<Class>> classMaps = new ConcurrentHashMap<String,List<Class>>();
	private Map<String,Object[]> xmlMaps = new ConcurrentHashMap<String,Object[]>();


	@Override
	public List<Class> loadCrudAnnotaionClass() {
		List<Class> classes = new ArrayList<Class>();
		Properties pro = PropertyUtil.load();
		try {
			String clazzes = pro.getProperty(JcrContant.REPOSITORY_CLASSES);
			String[] classs = clazzes.split(",");
			for (String clazz : classs) {
				classes.add(Class.forName(clazz).newInstance().getClass());
			}
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (InstantiationException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		}
		classMaps.put("classKey", classes);
		return classes;
	}

	@Override
	public String[] loadCrudXmlClass() {
		Properties pro = PropertyUtil.load();
		String clazzes = pro.getProperty(JcrContant.REPOSITORY_FILES);
		String[] files = clazzes.split(",");
		xmlMaps.put("xmlKey", files);
		return files;
	}
	
	
	public List<Class> getAnnotationClasses() {
		return classMaps.get("classKey");
	}

	
	public String[] getXmlMaps() {
		return (String[]) xmlMaps.get("xmlKey");
	}

}
