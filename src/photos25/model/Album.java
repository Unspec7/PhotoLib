package photos25.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5586038399535054803L;
	private String name;
	private String username;
	private ArrayList<Photo> photos;
	
	public Album(String name, String username) {
		this.name = name;
		this.username = username;
		photos = new ArrayList<Photo>();
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setUser(String username) {
		this.username = username;
	}
	
	public String getUser() {
		return this.username;
	}
	
	public ArrayList<Photo> getPhotos() {
		return this.photos;
	}
	
	public void addPhoto(Photo p) {
		photos.add(p);
	}
	
	public void removePhoto(Photo p) {
		photos.remove(p);
	}
	
	public Photo findPhoto(Photo wanted){
		for (Photo p: photos){
			if (p.getName().equals(wanted.getName()));
		}
		return null;
	}
	
	public int sizeofAlbum(){
		int counter = 0;
		for (Photo p : photos){
			if (p != null){
				counter++;
			}
		}
		return counter;
	}
	
	public Photo getYoungest(){
		Photo currentSmallest = photos.get(0);
		for (Photo p: photos){
			if (p.getDate().compareTo(currentSmallest.getDate()) < 0){
				currentSmallest = p;
			}
		}
		return currentSmallest;
	}
	
	public Photo getOldest(){
		Photo currentOldest = photos.get(0);
		for (Photo p: photos){
			if (p.getDate().compareTo(currentOldest.getDate()) > 0){
				currentOldest = p;
			}
		}
		return currentOldest;
	}
}
