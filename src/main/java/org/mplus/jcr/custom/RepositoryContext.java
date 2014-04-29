package org.mplus.jcr.custom;

import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mplus.jcr.core.init.Context;
import org.mplus.jcr.core.init.InitRepository;
import org.mplus.jcr.core.init.Namespace;
import org.mplus.jcr.proxy.CglibsProxy;

@SuppressWarnings("rawtypes")
public class RepositoryContext implements Context{
	
	private static final Log log = LogFactory.getLog(RepositoryContext.class);
	
	private Object target;
	
	public RepositoryContext(){}
	
	
	public RepositoryContext(Object target){
		this.target = target;
	}
	
	public List<Class> initAnnotationClass(){
		if (target == null) {
			return getProxy().loadCrudAnnotaionClass();
		}else{
			return getRepository().loadCrudAnnotaionClass();
		}
	}
	
	
	public String[] initXmlClass(){
		if (target == null) {
			return getProxy().loadCrudXmlClass();
		}else{
			return getRepository().loadCrudXmlClass();
		}
	}
	
	public void initNamespace(Session session){
		try {
			if (target ==  null) {
				createNamespace().init(session);
			}else{
				getNamespace().init(session);
			}
		} catch (RepositoryException e) {
			log.error(e.getMessage());
		}
	}
	
	
	public InitRepository getProxy(){
		CglibsProxy cp = new CglibsProxy();
		return (InitRepository) cp.bind(new SimpleInitRepository());
	}
	
	
	public Namespace createNamespace(){
		CglibsProxy cp = new CglibsProxy();
		return (Namespace) cp.bind(new RegisterNamespace());
	}

	
	@Override
	public InitRepository getRepository() {
		CglibsProxy cp = new CglibsProxy();
		return (InitRepository) cp.bind(target);
	}


	@Override
	public Namespace getNamespace() {
		CglibsProxy cp = new CglibsProxy();
		return (Namespace) cp.bind(target);
	}

}
