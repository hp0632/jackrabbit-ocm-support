package org.mplus.jcr.core;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

public interface Register {

	Session register() throws RepositoryException;
}
