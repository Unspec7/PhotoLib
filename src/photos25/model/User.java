package photos25.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7253011184518956698L;
	private final String username;
	private ArrayList<Album> userAlbums;
	
	public User() {
		this.username = null;
	}
	public User(String username){
		this.username = username;
		this.userAlbums = new ArrayList<Album>();
	}
	
	public String getUsername(){
		return username;
	}
	
	public void addAlbum(Album a){
		userAlbums.add(a);
	}
	public void removeAlbum(Album a){
		userAlbums.remove(a);
	}
	
	public ArrayList<Album> getAlbum(){
		return userAlbums;
	}
	
	public Album findAlbum(Album wanted){
		for (Album a: userAlbums){
			if ( a.getName().equals(wanted.getName()) ){
				return a;
			}
		}
		return null;
	}
	public ArrayList<Album> getAlbumlist(){
		return userAlbums;
	}
}
