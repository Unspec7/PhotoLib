package photos25.model;

import java.io.Serializable;

public class Tag implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5994388855500988432L;
	private String tagName;  //location, person etc
	private String tagValue;  // New Brunswick, Jeff, Brian etc
	
	public Tag(String tagName, String tagValue){
		this.tagName = tagName;
		this.tagValue = tagValue;
	}
	
	public void setTagName(String tagName){
		this.tagName = tagName;
	}
	
	public String getTagName(){
		return this.tagName;
	}
	
	public void setTagValue(String tagValue){
		this.tagValue = tagValue;
	}
	
	public String getTagValue(){
		return this.tagValue;
	}
	
}
