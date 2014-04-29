package org.mplus.jcr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.AccessDeniedException;
import javax.jcr.NamespaceException;
import javax.jcr.RepositoryException;
import javax.jcr.UnsupportedRepositoryOperationException;

import org.apache.jackrabbit.ocm.exception.ObjectContentManagerException;
import org.apache.jackrabbit.ocm.manager.ObjectContentManager;
import org.apache.jackrabbit.ocm.query.Filter;
import org.apache.jackrabbit.ocm.query.Query;
import org.apache.jackrabbit.ocm.query.QueryManager;
import org.junit.Test;
import org.mplus.jcr.custom.AnnotationJcr;
import org.mplus.jcr.test.vo.BlogEntryDTO;
import org.mplus.jcr.test.vo.Person;

public class TestEntity extends AnnotationJcr<Person>{
	
	@Test
	public void testInsert() throws AccessDeniedException, ObjectContentManagerException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		BlogEntryDTO blog = insert();
		getAnnotationOCM().insert(blog);
		getAnnotationOCM().save();
		blog.setTitle("testSave");
		getAnnotationOCM().update(blog);
		getAnnotationOCM().save();
	}
	
	
	@Test
	public void testInsert2() throws AccessDeniedException, ObjectContentManagerException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		BlogEntryDTO blog = insert();
//		insert(blog);
		
	}
	
	@Test
	public void testPersonInsert(){
		Person p = buildPerson("hp2");
		p.setPath("/hp");
		insert(p);
	}
	
	/*
	 *  /troy---3dc30a20-cb7a-4e87-9527-669089f8a546----- troy
		/troy[2]---380e582c-ebbf-4526-bc33-8a021c0be028----- troy
		/troy[3]---7111d4e8-89da-4c0e-9bd7-dd7561810fb4----- hp
	 */
	
	@Test
	public void testSearchPersons() throws ObjectContentManagerException, RepositoryException{
		Collection<Person> pps = getAnnotationOCM().getObjects(Person.class, "/troy[2]//");
		for (Person person : pps) {
			System.out.println(person.getPath() + "---"+ person.getId() + "----- "+person.getName());
		}
	}
	
	@Test
	public void testdelPerson() throws ObjectContentManagerException, RepositoryException{
		Person p = buildPerson("troy");
		p.setPath("/troy");
		getAnnotationOCM().remove(p);
		getAnnotationOCM().save();
	}
	
	@Test
	public void testxmlInsert() throws AccessDeniedException, ObjectContentManagerException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		BlogEntryDTO blog = new BlogEntryDTO();
		blog.setBlogContent("testupdate");
		blog.setCreationTime(Calendar.getInstance());
		blog.setTitle("update");
		blog.setUserName("hp");
		blog.setPath("/blog");
//		getXmlOcm().insert(blog);
//		getXmlOcm().save();
		
	}
	
	@Test
	public void testSearch() {
		Map map = new HashMap();
		map.put("title","save");
//		map.put("uuid", "9e4aec9a-2d10-4037-80d7-328b75fc8e90");
		BlogEntryDTO blog = insert();
//		BlogEntryDTO bed = (BlogEntryDTO) findByProperty(blog.getClass(), map);
//		System.out.println(bed.getTitle() + " ---- "+bed.getUuid());
	}
	
	
	@Test
	public void testSearchs() {
		Map map = new HashMap();
		map.put("title", "save");
		BlogEntryDTO blog = insert();
//		Collection<BlogEntryDTO> bed = findResultsByProperty(blog.getClass(), map);
//		System.out.println(bed.size());
	}
	
	@Test
	public void testSearchslike() {
		Map map = new HashMap();
		map.put("title", "save");
		BlogEntryDTO blog = insert();
//		Collection<BlogEntryDTO> bed = findResultsLikeByProperty(blog.getClass(), map);
//		for (BlogEntryDTO blogEntryDTO : bed) {
//			System.out.println(blogEntryDTO.getPath() + " --- " +blogEntryDTO.getBlogContent() + "----"+blogEntryDTO.getUserName());
//		}
	}
	
	
	@Test
	public void testSearches() {
		BlogEntryDTO blog = insert();
//		Collection<BlogEntryDTO> bed = findAll(blog.getClass());
//		for (BlogEntryDTO blogEntryDTO : bed) {
//			System.out.println(blogEntryDTO.getBlogContent() +"---"+ blogEntryDTO.getTitle());
//		}
//		System.out.println(bed.size());
	}
	
	
	@Test
	public void testFinds() throws AccessDeniedException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		QueryManager qm = getAnnotationOCM().getQueryManager();
		Filter filter = qm.createFilter(BlogEntryDTO.class);
		filter.addEqualTo("uuid", "9e4aec9a-2d10-4037-80d7-328b75fc8e90");
		Query query = qm.createQuery(filter);
		String jcrExpression = qm.buildJCRExpression(query);
		System.out.println(jcrExpression);
		Collection<BlogEntryDTO> collection = getAnnotationOCM().getObjects(query);
		for (BlogEntryDTO object : collection) {
			System.out.println(object.getTitle());
		}
	}
	
	@Test
	public void testDel() throws AccessDeniedException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
		QueryManager qm = getAnnotationOCM().getQueryManager();
		Filter filter = qm.createFilter(BlogEntryDTO.class);
		Query query = qm.createQuery(filter);
		getAnnotationOCM().remove(query);
		getAnnotationOCM().save();
	}
	
	
	@Test
	public void testUpdate() throws AccessDeniedException, NamespaceException, UnsupportedRepositoryOperationException, RepositoryException{
	}
	
	
	public BlogEntryDTO insert(){
		BlogEntryDTO blog = new BlogEntryDTO();
		blog.setBlogContent("testsave");
		blog.setCreationTime(Calendar.getInstance());
		blog.setTitle("save");
		blog.setUserName("hp");
		blog.setPath("/blogEntry");
		return blog;
	}
	
	
	  @Test
	  public void testPersonList()
	    {
	        try
	        {
	        	ObjectContentManager ocm = getAnnotationOCM();
	    		
	        	Person aPerson = buildPerson("PERSON1");
	        	aPerson.setPath("/person");
	        	ocm.insert(aPerson);
	        	ocm.save();
	        	
	        	QueryManager qm = ocm.getQueryManager();
	    		Filter filter = qm.createFilter(Person.class);
	    		filter.addEqualTo("id", "5364286b-ca98-456d-a2aa-5c60311414f6");
	    		Query query = qm.createQuery(filter);
	    		String jcrExpression = qm.buildJCRExpression(query);
	    		System.out.println(jcrExpression);
	    		Collection<Person> collection = ocm.getObjects(query);
	    		for (Person object : collection) {
	    			System.out.println(object.getName() + "----" + object.getId());
	    		}
	        	
//	        	System.out.println(aPerson.getId());
//	        	String oldParentId = new String(aPerson.getId().toCharArray());
//	        	List<String> childIds = new ArrayList<String>(); 
//	        	for (Person p : aPerson.getChildren()){
//	        		System.out.println(p.getId());
//	        		childIds.add(new String(p.getId().toCharArray())+"********");
//	        	}
//	        	aPerson.setName("UPDATED1");
//	        	ocm.update(aPerson);
//	        	Person fb1Person = (Person)ocm.getObject("/person");
//	        	System.out.println(fb1Person.getId()+"------"+fb1Person.getName());
	        	
	        	//To assert that the ids of the objects in the 
	        	//collection has not changed during update.
//	        	for (Person p : fb1Person.getChildren()){
//	        		System.out.println(childIds.contains(p.getId()));
//	        	}
//	        	
//	        	Person newChild = new Person();
//	        	newChild.setName("CHILD2");
//	        	
//	        	fb1Person.getChildren().add(newChild);
//	        	ocm.update(fb1Person);
//	        	
//	        	Person fb2Person = (Person)ocm.getObject("/person");
//	        	System.out.println(fb2Person);
//	        	System.out.println(fb2Person.getName());
//	        	System.out.println(fb2Person.getId());
//	        	
//	        	//To assert that the ids of the objects in the 
//	        	//collection has not changed during update.
//	        	String child2Id = null;
//	        	for (Person p : fb2Person.getChildren()){
//	        		if (!"CHILD2".equals(p.getName()))
//	        			System.out.println(childIds.contains(p.getId())+"-------");
//	        		else{
//	        			System.out.println(p.getId());
//	        			child2Id = new String(p.getId().toCharArray());
//	        			System.out.println(childIds.contains(p.getId()));
//	        		}
//	        	}
//	        	
//	        	//Now remove everyone but CHILD2 and do the update once again
//	        	List<Person> peopleToRemove = new ArrayList<Person>();
//	        	for (Person p : fb2Person.getChildren()){
//	        		if (!"CHILD2".equals(p.getName()))
//	        			peopleToRemove.add(p);
//	        	}
//	        	
//	        	for (Person p : peopleToRemove){
//	        		fb2Person.getChildren().remove(p);
//	        	}
//	        	
//	        	ocm.update(fb2Person);
//	        	
//	        	Person fb3Person = (Person)ocm.getObject("/person");
//	        	System.out.println(fb3Person.getChildren().size());
//	        	System.out.println(fb3Person.getChildren().get(0).getId());
	        	
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	    
	    public Person buildPerson(String name){
	    	Person p = new Person();
	    	p.setName(name);
	    	Person aChild = new Person();
	    	aChild.setName("CHILD1");
	    	List<Person> children = new ArrayList<Person>();
	    	children.add(aChild);
	    	p.setChildren(children);
	    	return p;
	    }
}
