package org.mplus.jcr.core.init;

import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;

public interface NodeType {

	public void init(Session session) throws IOException, InvalidNodeTypeDefException, RepositoryException;
}
