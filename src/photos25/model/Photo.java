package photos25.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Photo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8713878208226985069L;
	private String name;
	private String username;
	private String caption;
	private Calendar cal;
	private Date date;
	private File picture;
	private ArrayList<Tag> tags;
	private String path;
	
	
	public Photo(String name, String username, String caption, File picture){
		this.name = name;
		this.username = username;
		this.caption = caption;
		this.picture = picture;
		cal = new GregorianCalendar();
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND,0);
		this.date = cal.getTime();
		this.tags = new ArrayList<Tag>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCaption() {
		return caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public Date getDate() {
		return date;
	}
	
	public File getPicture() {
		return picture;
	}
	
	public void setPicture(File picture) {
		this.picture = picture;
	}
	
	public ArrayList<Tag> getTags() {
		return tags;
	}
	
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	
	public void setPath(String location){
		this.path = location;
	}
	
	public String getPath(){
		return path;
	}
	
}
