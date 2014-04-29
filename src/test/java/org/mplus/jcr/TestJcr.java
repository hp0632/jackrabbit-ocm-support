package org.mplus.jcr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.ImportUUIDBehavior;
import javax.jcr.ItemExistsException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import javax.jcr.version.VersionException;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.jettison.json.JSONException;
import org.junit.Test;
import org.mplus.jcr.core.SingleSession;
import org.mplus.jcr.support.JcrSupport;
import org.mplus.jcr.test.vo.BlogEntryDTO;

@SuppressWarnings("rawtypes")
public class TestJcr extends JcrSupport {

	private Session session = SingleSession.getInstance();

	
	public static ArrayList getBlogList() {

		ArrayList blogEntryList = new ArrayList();
		Session session = null;
		try {
			Node rootNode = session.getRootNode();
			NodeIterator blogEntryNodeIterator = rootNode.getNodes();
			while (blogEntryNodeIterator.hasNext()) {
				Node blogEntry = blogEntryNodeIterator.nextNode();
				if (blogEntry.getName().equals("blogEntry") == false)
					continue;
				String title = blogEntry.getProperty("title").getString();
				String blogContent = blogEntry.getProperty("blogContent")
						.getString();
				Value creationTimeValue = (Value) blogEntry.getProperty(
						"creationTime").getValue();
				String userName = blogEntry.getProperty("userName").getString();
				BlogEntryDTO blogEntryDTO = new BlogEntryDTO(userName, title,
						blogContent, creationTimeValue.getDate());
				blogEntryList.add(blogEntryDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return blogEntryList;
	}

	public Session insertBlogEntry(BlogEntryDTO blogEntryDTO) throws Exception {
		try {
			Node rootNode = session.getRootNode();
			Node blogEntry = rootNode.addNode("test1");
			Node blog = blogEntry.addNode("t");
			blog.setProperty("title", blogEntryDTO.getTitle());
			blog.setProperty("blogContent", blogEntryDTO.getBlogContent());
			blog.setProperty("creationTime", blogEntryDTO.getCreationTime());
			blog.setProperty("userName", blogEntryDTO.getUserName());
			session.save();
			// 删除blogEntry
			// rootNode.getNode("test").remove();
			// session.save();
			// 查询
			// Node node = rootNode.getNode("blogEntry");
			// System.out.println(node.getProperty("title").getString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return session;
	}

	public static void main(String[] args) {
		Session session = null;
		try {
			BlogEntryDTO blog = new BlogEntryDTO();
			blog.setBlogContent("aaaa");
			blog.setCreationTime(Calendar.getInstance());
			blog.setTitle("abc");
			blog.setUserName("troy");
			session = new TestJcr().insertBlogEntry(blog);
			// Node root = session.getRootNode();
			// root.getNode("blogEntry").remove();
			// session.save();
			// Node node = root.getNode("blogEntry");
			// System.out.println(node.getProperty("title").getString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testQuery() throws Exception {
		try {
			Node rootNode = session.getRootNode();
			Node node = rootNode.getNode("test1/t");
			System.out.println(node.getProperty("creationTime").getValue()
					.getDate().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void exportXml() throws FileNotFoundException {
		File file = new File("d:/t.xml");
		FileOutputStream fos = new FileOutputStream(file);
		Session session = null;
		try {
			BlogEntryDTO blog = new BlogEntryDTO();
			blog.setBlogContent("aaaa");
			blog.setCreationTime(Calendar.getInstance());
			blog.setTitle("ccc");
			blog.setUserName("troy");
			session = new TestJcr().insertBlogEntry(blog);
			session.exportSystemView("/blog", fos, false, false);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void importXml() {
		File file = new File("d:/b.xml");
		Session session = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			session.importXML("/", fis,
					ImportUUIDBehavior.IMPORT_UUID_CREATE_NEW);
			Node rootNode = session.getRootNode();
			Node node = rootNode.getNode("blogABC/b");
			System.out.println(node.getProperty("title").getString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testJcrMap() {
		Map map = new HashMap();
		map.put("a", "a1");
		map.put("b", 3);
		map.put("c", new Date());
		try {
			Node testmap = getRootNode().addNode("testmap");
//			saveNodeFromMap(testmap, map);
			session.save();
			System.out.println(getRootNode().getNode("testmap")
					.getProperty("c").getString());
		} catch (ItemExistsException e) {
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (VersionException e) {
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (LockException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testJson() {
		String json = "{'a':1,'b':'b1','c':'" + new Date() + "'}";
		Node testmap;
		try {
			testmap = getRootNode().addNode("testjson");
//			saveNodeFromJson(testmap, json);
			session.save();
			System.out.println(getRootNode().getNode("testjson")
					.getProperty("c").getString());
		} catch (ItemExistsException e) {
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (VersionException e) {
			e.printStackTrace();
		} catch (ConstraintViolationException e) {
			e.printStackTrace();
		} catch (LockException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testInsertCatagory() {
		String json = "{'a':1,'b':'b1','d':'" + new Date() + "'}";
//		new JcrImpl().insertToRepository(json);
		try {
			System.out.println(getRootNode().getNode("catagory1")
					.getProperty("d").getString());
		} catch (ValueFormatException e) {
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} 
	}

	@Test
	public void testCatagory() throws ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException, RepositoryException {
//		Node node = saveCatagorys();
//		System.out.println(node.getName());
	}

	@Test
	public void testUpdateNode() {
		try {
			Node rootNode = session.getRootNode();
			Node updateNode = rootNode.getNode("blogEntry");
			String un = updateNode.getProperty("title").getString();
			updateNode.setProperty("title", "abc");
			session.save();
			System.out.println(updateNode.getProperty("title").getString());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveNew() {
		try {
			Node rootNode = session.getRootNode();
			Node testNode = rootNode.getNode("test1");
			Node tt = testNode.addNode("tt");
			tt.setProperty("title", "www");
			tt.setProperty("content", 2324);
			tt.setProperty("date", Calendar.getInstance());
			session.save();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getNodes() {
		try {
			Node rootNode = session.getRootNode();
			List<String> list = getChildNodes(rootNode, "blogEntry");
			for (String string : list) {
				System.out.println(string);
			}
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		// 查询list集合 test1下面有t和tt两个节点

	}

	public List<String> getChildNodes(Node node, String... nodeNames)
			throws RepositoryException {
		if (nodeNames == null && CollectionUtils.sizeIsEmpty(nodeNames)) {

		}
		List<String> list = new ArrayList<String>();
		PropertyIterator pi = node.getProperties();
		while (pi.hasNext()) {
			Property property = pi.nextProperty();
			if (property.isMultiple()) {
				Value[] values = property.getValues();
				if (values == null) {
					continue;
				} else {
					for (Value value : values) {
						System.out.println(value.getString()+"****************");
					}
				}
			} else {
				System.out.println(property.getValue().getString());
			}
		}
		
		NodeIterator childNodes = node.getNodes();
		while (childNodes.hasNext()) {
			Node child = childNodes.nextNode();
			getChildNodes(child);
		}
		return list;
	}

	@Test
	public void testSearchSql() throws RepositoryException{
		Workspace ws = session.getWorkspace();
		QueryManager qm = ws.getQueryManager();
		String sql = "/test1";
		Query query = qm.createQuery(sql, Query.XPATH);
		QueryResult qr = query.execute();
		RowIterator ri = qr.getRows();
		while(ri.hasNext()){
			Row row = ri.nextRow();
			Value[] value = row.getValues();
			for (Value value2 : value) {
				System.out.println(value2.getString());
			}
		}
	}
}
