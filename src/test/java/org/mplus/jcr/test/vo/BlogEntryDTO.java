package org.mplus.jcr.test.vo;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

@SuppressWarnings("serial")
@Node
public class BlogEntryDTO implements Serializable{

	@Field(path=true)
	private String path;
	@Field(jcrName="ocm:userName")
	private String userName;
	@Field(jcrName="ocm:title")
    private String title;  
	@Field(jcrName="ocm:blogContent")
    private String blogContent; 
	@Field(jcrName="ocm:creationTime")
    private Calendar creationTime;
    
    
    public BlogEntryDTO(){
    	
    }
    
    public BlogEntryDTO(String userName,String title,String blogContent,Calendar creationTime){
    	this.userName = userName;
    	this.title = title;
    	this.blogContent = blogContent;
    	this.creationTime = creationTime;
    }
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public Calendar getCreationTime() {
		return creationTime;
	}
	public void setCreationTime(Calendar creationTime) {
		this.creationTime = creationTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
