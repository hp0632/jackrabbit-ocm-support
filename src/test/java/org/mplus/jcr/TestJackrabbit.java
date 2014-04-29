package org.mplus.jcr;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;

import org.mplus.jcr.core.SingleSession;

//import sun.net.www.MimeTable;

public class TestJackrabbit {
	
	private Session session = SingleSession.getInstance();
	
    public static void main(String[] args) throws Exception {

      new TestJackrabbit().get();

    }
    
    
    public void get() throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException, FileNotFoundException, IOException{
    	  // 根节点
        Node rn = session.getRootNode();

        // 注册命名空间
        Workspace ws = session.getWorkspace();
        ws.getNamespaceRegistry().registerNamespace("wiki4",
                "http://www.damai.cm/wiki1/1.1");

        // 增加内容--节点的形式
        Node encyclopedia = rn.addNode("wiki:encyclopedia");
        Node p = encyclopedia.addNode("wiki:entry");
        p.setProperty("wiki:title", toStringValue("rose"));
        p.setProperty("wiki:content",
                toStringValue("A rose is a flowering shrub."));
        p.setProperty("wiki:category", new Value[] { toStringValue("flower"),
                toStringValue("plant"), toStringValue("rose") });

        Node n = encyclopedia.addNode("wiki:entry");
        n.setProperty("wiki:title", toStringValue("Shakespeare"));
        n.setProperty("wiki:content",
                toStringValue("A famous poet who likes roses."));
        n.setProperty("wiki:category", toStringValue("poet"));
        // 保存
        session.save();

        // 查找
        QueryManager qm = ws.getQueryManager();
        Query q = qm.createQuery(
                "//wiki:encyclopedia/wiki:entry[@wiki:title = 'rose']",
                Query.XPATH);// deprecated
        QueryResult result = q.execute();// 执行查询
        NodeIterator it = result.getNodes();
        // 然后就可以了遍历了
        while (it.hasNext()) {
            Node entry = it.nextNode();
            // 简单的输出，后面会有输出详细内容的方法
            System.out.println(entry.toString());
        }

        // 加入文件
//        File file = new File("d:\\33.jpg");
//        MimeTable mt = MimeTable.getDefaultTable();
//        String mimeType = mt.getContentTypeFor(file.getName());
//        if (mimeType == null) {
//            mimeType = "application/octet-stream";
//        }
//        Node fileNode = rn.addNode(file.getName(), "nt:file");
//        Node resNode = fileNode.addNode("jcr:content", "nt:resource");
//        resNode.setProperty("jcr:mimeType", mimeType);
//        resNode.setProperty("jcr:encoding", "");
        // 这里--用流加入
        // deprecated
        // resNode.setProperty("jcr:data", new FileInputStream(file));
        // 用Binary代替FileInputStream
//        Binary fileBinary = new BinaryImpl(new FileInputStream(file));
//        resNode.setProperty("jcr:data", fileBinary);
//
//        Calendar lastModified = Calendar.getInstance();
//        lastModified.setTimeInMillis(file.lastModified());
//        resNode.setProperty("jcr:lastModified", lastModified);
        // 保存
        session.save();

        // 递归打印所有节点
        printAll(rn);
    }

    private static org.apache.jackrabbit.value.StringValue toStringValue(
            String str) {
        return new org.apache.jackrabbit.value.StringValue(str);
    }

    /**
     * 打印所有节点
     */
    private static void printAll(Node node) throws RepositoryException {
        printFormat(node.toString());
        PropertyIterator propertys = node.getProperties();
        while (propertys.hasNext()) {
            Property entry = propertys.nextProperty();
            if (entry.isMultiple()) {
                Value[] values = entry.getValues();
                if (values == null) {
                    continue;
                }
                for (Value v : values) {
                    printFormat(v.getString());
                }
            } else {
                printFormat(entry.getValue().getString());
            }
        }

        NodeIterator entries = node.getNodes();
        while (entries.hasNext()) {
            Node entry = entries.nextNode();
            printAll(entry);
        }
    }

    private static void printFormat(Object str) {
        System.out.println("####################:" + str);
    }
}