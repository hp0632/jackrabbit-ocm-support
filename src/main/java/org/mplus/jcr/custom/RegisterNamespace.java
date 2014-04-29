package org.mplus.jcr.custom;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mplus.jcr.core.init.Namespace;


public class RegisterNamespace implements Namespace{
	
	private static final Log log = LogFactory.getLog(RegisterNamespace.class);
	
	public static final String OCM_NAMESPACE_PREFIX = "ocm";

    public static final String OCM_NAMESPACE = "http://jackrabbit.apache.org/ocm";


	@Override
	public void init(Session session) throws RepositoryException {
		  log.info("Register namespace");
	       String[] jcrNamespaces = session.getWorkspace().getNamespaceRegistry().getPrefixes();
	       boolean createNamespace = true;
	       for (int i = 0; i < jcrNamespaces.length; i++) {
	           if (jcrNamespaces[i].equals(OCM_NAMESPACE_PREFIX)) {
	               createNamespace = false;
	               log.debug("Jackrabbit OCM namespace exists.");
	           }
	       }
	       if (createNamespace) {
	           session.getWorkspace().getNamespaceRegistry().registerNamespace(OCM_NAMESPACE_PREFIX, OCM_NAMESPACE);
	           log.info("Successfully created Jackrabbit OCM namespace.");
	       }

	       if (session.getRootNode() != null) {
	           log.info("Jcr session setup successfull.");
	       }
	}

}
