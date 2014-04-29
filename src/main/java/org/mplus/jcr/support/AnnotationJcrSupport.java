package org.mplus.jcr.support;

import javax.jcr.RepositoryException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;


public class AnnotationJcrSupport extends JcrSupport{

	private static final Log log = LogFactory.getLog(AnnotationJcrSupport.class);
	
	/**
	 * annotation ObjectContentManager register
	 * @return
	 * @throws RepositoryException 
	 */
	public ObjectContentManager getAnnotationOCM() throws RepositoryException {
		log.info("get annotation ocm");
		ObjectContentManager ocm = new JcrOcm().annotationOcm(getSession());
		return ocm;
	}
	
	
	/**
	 * query manager 
	 * @return
	 */
	public QueryManager getAnnotationQueryManager(){
		QueryManager qm = null;
		try {
			qm = getAnnotationOCM().getQueryManager();
		} catch (RepositoryException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return qm;
	}
	
	/**
	 * get annotation filter for clazz
	 * @param clazz
	 * @return
	 */
	public Filter createAnnotationFilter(Class<?> clazz){
		return  getAnnotationQueryManager().createFilter(clazz);
	}
	
	/**
	 * annotation query
	 * @param clazz
	 * @return
	 */
	public Query createAnnotationQuery(Filter filter){
		Query query = getAnnotationQueryManager().createQuery(filter);
		String jcrExpression = getAnnotationQueryManager().buildJCRExpression(query);
		log.info(jcrExpression);
		System.out.println(jcrExpression);
		return query;
	}
	
}
