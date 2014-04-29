package org.mplus.jcr.core;

import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.core.TransientRepository;
import org.mplus.jcr.utils.JcrContant;
import org.mplus.jcr.utils.PropertyUtil;

public class JcrRepository implements Register{

	private static final Log log = LogFactory.getLog(JcrRepository.class);
	
	static{
		System.setProperty(JcrContant.REPOSITORY_HOME,PropertyUtil.load().getProperty(JcrContant.REPOSITORY_HOME));
		System.setProperty(JcrContant.REPOSITORY_CONF, PropertyUtil.load().getProperty(JcrContant.REPOSITORY_CONF));
	}
	
	public Session register() throws RepositoryException{
		try {   
            Repository repository = new TransientRepository();   
            Session session = repository.login(new SimpleCredentials(PropertyUtil.load().getProperty(JcrContant.CREDENTIALUSER),   
            		PropertyUtil.load().getProperty(JcrContant.CREDENTIALPASS).toCharArray()));  
            return session;
        } catch (LoginException e) {
        	log.error("it is wrong when login ,"+e.getMessage());
            throw new LoginException(e);   
        } catch (RepositoryException e) {
        	log.error("repository is exception ,"+e.getMessage());
            throw new RepositoryException(e);               
        } 
	}
	
}
