package org.mplus.jcr.support;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.ocm.query.Filter;
import org.mplus.jcr.core.SingleSession;

/**
 * @author Administrator
 *
 *	 提供第三方以服务器仓库形式存放
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class JcrSupport{
	
    public static final String OCM_NAMESPACE_PREFIX = "ocm";

    public static final String OCM_NAMESPACE = "http://jackrabbit.apache.org/ocm";


	/**
	 * get session 
	 * @return
	 */
	public Session getSession(){
		return SingleSession.getInstance();
	}
	
	/**
	 * destory the session  when it is unuse
	 */
	public void destroy(){
		getSession().logout();
	}
	
	/**
	 * get root node
	 * @return
	 * @throws RepositoryException
	 */
	public Node getRootNode() throws RepositoryException{
		return getSession().getRootNode();
	}
	

	/**
	 * add equal to
	 * @param paramMap
	 * @param filter
	 */
	public Filter addEqualTo(Map paramMap,Filter filter){
		Set set = paramMap.entrySet();
		Iterator<Entry<String,Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String,Object> entry = it.next();
			if (entry.getValue() != null) {
				filter.addEqualTo(entry.getKey(), entry.getValue());
			}
		}
		return filter;
	}
	
	/**
	 * add equal to
	 * @param paramMap
	 * @param filter
	 */
	public Filter addEqualTo(String key,Object value,Filter filter){
		if (key != null && value != null) {
			filter.addEqualTo(key,value);
		}
		return filter;
	}
	
	
	/**
	 * add like 
	 * @param paramMap
	 * @param filter
	 * @param likeStatus
	 * @return
	 */
	public Filter addLike(Map paramMap,Filter filter){
		Set set = paramMap.entrySet();
		Iterator<Entry<String,Object>> it = set.iterator();
		while(it.hasNext()){
			Entry<String,Object> entry = it.next();
			if (entry.getValue() != null) {
				filter.addLike(entry.getKey(), "%"+entry.getValue()+"%");
			}
			
		}
		return filter;
	}
	
	/**
	 * add like 
	 * @param paramMap
	 * @param filter
	 * @param likeStatus
	 * @return
	 */
	public Filter addLike(String key,Object value,Filter filter){
		if (key != null && value != null) {
			filter.addLike(key,"%"+value+"%");
		}
		return filter;
	}
	
	
	/**
	 * import xml  ,this xml must handle jcr specification
	 * @param path
	 * @param is
	 * @throws PathNotFoundException
	 * @throws ItemExistsException
	 * @throws ConstraintViolationException
	 * @throws VersionException
	 * @throws InvalidSerializedDataException
	 * @throws LockException
	 * @throws IOException
	 * @throws RepositoryException
	 */
	public void importXml(String path,InputStream is) throws PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, IOException, RepositoryException{
		getSession().importXML(path, is, ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
	}
	
	
	/**
	 * export xml ,this xml must handle jcr specification
	 * @param path
	 * @param os
	 * @throws PathNotFoundException
	 * @throws IOException
	 * @throws RepositoryException
	 */
	public void exportXml(String path,OutputStream os) throws PathNotFoundException, IOException, RepositoryException{
		getSession().exportSystemView(path, os, false, false);
	}
	
}
