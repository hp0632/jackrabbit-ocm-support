package org.mplus.jcr.custom;

import java.util.Collection;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.exception.ObjectContentManagerException;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.mplus.jcr.support.AnnotationJcrSupport;
import org.mplus.jcr.support.BaseJcr;

@SuppressWarnings({"rawtypes","unchecked"})
public class AnnotationJcr<T> extends AnnotationJcrSupport implements BaseJcr<T>{
	
	private static final Log log = LogFactory.getLog(AnnotationJcr.class);
	
	public ObjectContentManager ocm(){
		ObjectContentManager ocm = null;
		try {
			ocm = getAnnotationOCM();
		} catch (AccessDeniedException e) {
			log.error(e.getMessage());
		} catch (ObjectContentManagerException e) {
			log.error(e.getMessage());
		} catch (NamespaceException e) {
			log.error(e.getMessage());
		} catch (UnsupportedRepositoryOperationException e) {
			log.error(e.getMessage());
		} catch (RepositoryException e) {
			log.error(e.getMessage());
		} 
		return ocm;
	}

	@Override
	public T insert(T t) {
		ocm().insert(t);
		ocm().save();
		return t;
	}

	/*
	 * entity @path=true field is not in the parammap
	 */
	@Override
	public T findByProperty(Class<?> clazz, Map paramMap) {
		if(paramMap == null || MapUtils.isEmpty(paramMap))
		{
			log.error("paramMap is null");
			return null;
		}
		Filter filter = createAnnotationFilter(clazz);
		addEqualTo(paramMap, filter);
		Query query = createAnnotationQuery(filter);
		T bed = (T) ocm().getObject(query);
		return bed;
	}

	/*
	 * entity @path=true field is not in the parammap
	 */
	@Override
	public Collection<T> findResultsByProperty(Class<?> clazz, Map paramMap) {
		if(paramMap == null || MapUtils.isEmpty(paramMap))
		{
			log.error("paramMap is null");
			return findAll(clazz);
		}
		Filter filter = createAnnotationFilter(clazz);
		addEqualTo(paramMap, filter);
		Query query = createAnnotationQuery(filter);
		Collection<T> bed = ocm().getObjects(query);
		return bed;
	}
	
	@Override
	public Collection<T> findAll(Class<?> clazz) {
		Filter filter = createAnnotationFilter(clazz);
		Query query = createAnnotationQuery(filter);
		Collection<T> beds = ocm().getObjects(query);
		return beds;
	}
	
	/*
	 * entity @path=true field is not in the parammap
	 */
	@Override
	public Collection<T> findResultsLikeByProperty(Class<?> clazz, Map paramMap) {
		if(paramMap == null || MapUtils.isEmpty(paramMap))
		{
			log.error("paramMap is null");
			return findAll(clazz);
		}
		Filter filter = createAnnotationFilter(clazz);
		addLike(paramMap, filter);
		Query query = createAnnotationQuery(filter);
		Collection<T> bed = ocm().getObjects(query);
		return bed;
	}

	@Override
	public void delete(T t) {
		ocm().remove(t);
		ocm().save();
		ocm().refresh(true);
	}

	@Override
	public void update(T t) {
		ocm().update(t);
		ocm().save();
	}
	
	@Override
	public T findByPath(String path) {
		T t = (T) ocm().getObject(path);
		return t;
	}
	

}
