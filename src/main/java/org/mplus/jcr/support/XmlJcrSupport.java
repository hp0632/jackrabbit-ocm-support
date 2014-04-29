package org.mplus.jcr.support;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;

public class XmlJcrSupport extends JcrSupport{
	
	private static final Log log = LogFactory.getLog(XmlJcrSupport.class);
	
	/**
	 * xml ObjectContentManager register
	 * @return
	 * @throws AccessDeniedException
	 * @throws NamespaceException
	 * @throws UnsupportedRepositoryOperationException
	 * @throws RepositoryException
	 */
	public ObjectContentManager getXmlOcm() throws AccessDeniedException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		log.info("get xml ocm");
		ObjectContentManager ocm=new JcrOcm().xmlOcm(getSession());
		return ocm;
	}
	
	/**
	 * xml query manager 
	 * @return
	 */
	public QueryManager getXmlQueryManager(){
		QueryManager qm = null;
		try {
			qm = getXmlOcm().getQueryManager();
		} catch (RepositoryException e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return qm;
	}
	
	/**
	 * get xml filter for clazz
	 * @param clazz
	 * @return
	 */
	public Filter createXmlFilter(Class<?> clazz){
		return  getXmlQueryManager().createFilter(clazz);
	}
	
	/**
	 * xml query
	 * @param clazz
	 * @return
	 */
	public Query createXmlQuery(Filter filter){
		Query query = getXmlQueryManager().createQuery(filter);
		String jcrExpression = getXmlQueryManager().buildJCRExpression(query);
		log.info(jcrExpression);
		System.out.println(jcrExpression);
		return query;
	}
}
