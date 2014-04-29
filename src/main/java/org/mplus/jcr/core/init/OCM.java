package org.mplus.jcr.core.init;

import javax.jcr.Session;

import org.apache.jackrabbit.ocm.manager.ObjectContentManager;

public interface OCM {

	public ObjectContentManager annotationOcm(Session session,Object...objects);
	
	public ObjectContentManager xmlOcm(Session session,Object...objects);
}
