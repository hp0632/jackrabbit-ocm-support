package org.mplus.jcr.core;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SingleSession {
	
	private static final Log log = LogFactory.getLog(SingleSession.class);
	
	private static Session session;

	/**
	 * 单例模式（双重检验锁） 进行session的初始化
	 * @return
	 */
	public static Session getInstance(){
		if(session == null){
			synchronized (JcrRepository.class) {
				if(session == null){
					try {
						session = new JcrRepository().register();
					} catch (RepositoryException e) {
						log.error(e.getMessage());
					}
				}
			}
		}
		return session;
	}
}
