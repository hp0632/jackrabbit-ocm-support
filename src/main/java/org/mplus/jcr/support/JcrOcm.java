package org.mplus.jcr.support;

import java.util.List;

import javax.jcr.Session;

import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.manager.impl.ObjectContentManagerImpl;
import org.apache.jackrabbit.ocm.mapper.Mapper;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.AnnotationMapperImpl;
import org.apache.jackrabbit.ocm.mapper.impl.digester.DigesterMapperImpl;
import org.mplus.jcr.core.init.OCM;
import org.mplus.jcr.custom.RepositoryContext;
import org.mplus.jcr.exception.JcrException;
import org.mplus.jcr.utils.PropertyUtil;

@SuppressWarnings("rawtypes")
public class JcrOcm implements OCM{

	
	@Override
	public ObjectContentManager annotationOcm(Session session,Object...objects) {
		List<Class> list = null;
		try{
			if (objects == null || PropertyUtil.isEmpty(objects)) {
				list = new RepositoryContext().initAnnotationClass();
				new RepositoryContext().initNamespace(session);
			}else{
				list = new RepositoryContext(objects[0]).initAnnotationClass();
				new RepositoryContext(objects[0]).initNamespace(session);
			}
		}catch(JcrException e){
			throw new JcrException("init annotation class is failure");
		}
		Mapper mapper = new AnnotationMapperImpl(list);
		ObjectContentManager ocm = new ObjectContentManagerImpl(session, mapper);
		return ocm;
	}
	

	@Override
	public ObjectContentManager xmlOcm(Session session,Object...objects) {
		String[] files = null;
		try {
			if (objects == null || PropertyUtil.isEmpty(objects)) {
				files = new RepositoryContext().initXmlClass();
				new RepositoryContext().initNamespace(session);
			}else{
				files = new RepositoryContext(objects[0]).initXmlClass();
				new RepositoryContext(objects[0]).initNamespace(session);
			}
		}catch(JcrException e){
			throw new JcrException("init xml class is failure ");
		}
		Mapper mapper=new DigesterMapperImpl(files);
		ObjectContentManager ocm = new ObjectContentManagerImpl(session, mapper);
		return ocm;
	}

}
