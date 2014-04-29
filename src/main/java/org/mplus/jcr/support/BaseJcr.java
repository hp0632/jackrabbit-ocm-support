package org.mplus.jcr.support;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("rawtypes")
public interface BaseJcr<T> {
	
	public T insert(T t);
	
	public T findByPath(String path);
	
	public T findByProperty(Class<?> clazz,Map paramMap);
	
	public Collection<T> findAll(Class<?> clazz);
	
	public Collection<T> findResultsByProperty(Class<?> clazz, Map paramMap);
	
	public Collection<T> findResultsLikeByProperty(Class<?> clazz,Map paramMap);
	
	public void delete(T t);
	
	
	public void update(T t);
}
