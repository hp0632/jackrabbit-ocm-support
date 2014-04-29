package org.mplus.jcr.core.init;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface Namespace {

	public void init(Session session) throws RepositoryException;
}
