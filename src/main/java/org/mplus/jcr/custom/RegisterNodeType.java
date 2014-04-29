package org.mplus.jcr.custom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Workspace;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeTypeManager;

import org.apache.jackrabbit.core.nodetype.InvalidNodeTypeDefException;
import org.apache.jackrabbit.core.nodetype.NodeTypeManagerImpl;
import org.apache.jackrabbit.core.nodetype.NodeTypeRegistry;
import org.apache.jackrabbit.core.nodetype.xml.NodeTypeReader;
import org.apache.jackrabbit.spi.QNodeTypeDefinition;
import org.mplus.jcr.core.init.NodeType;

public class RegisterNodeType implements NodeType{

	@Override
	public void init(Session session) throws IOException, InvalidNodeTypeDefException, RepositoryException {
		   InputStream xml = new FileInputStream(
	                "./src/main/java/ocm.xml");

	        QNodeTypeDefinition[] types = NodeTypeReader.read(xml);
	        Workspace workspace = session.getWorkspace();
	        NodeTypeManager ntMgr = workspace.getNodeTypeManager();
	        NodeTypeRegistry ntReg = ((NodeTypeManagerImpl) ntMgr).getNodeTypeRegistry();

	        for (int j = 0; j < types.length; j++) {
	            QNodeTypeDefinition def = types[j];
	            try {
	                ntReg.getNodeTypeDef(def.getName());
	            } catch (NoSuchNodeTypeException nsne) {
	                ntReg.registerNodeType(def);
	            }

	        }
	}

}
