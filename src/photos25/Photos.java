package photos25;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import photos25.model.User;
import photos25.view.adminPageController;
import photos25.view.landingPageController;
import photos25.view.loginPageController;


public class Photos extends Application implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6563970937495832947L;
	private Stage primaryStage;
	private ArrayList<User> userbase = new ArrayList<User>();
	private User user;
	
    public ArrayList<User> getUserBase() {
        return userbase;
    }
    
    public void setUser(User u){
    	this.user = u;
    }
    
    public User getUser(){
    	return user;
    }
    
    public void save(ArrayList<User> userbase) throws FileNotFoundException, IOException {
    	System.out.println("Saving");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data" + File.separator + "user.ser"));
		oos.writeObject(userbase);
		oos.close();
    }
    
    @SuppressWarnings ("unchecked")
	public ArrayList<User> load() throws FileNotFoundException, IOException, ClassNotFoundException {
		System.out.println("loading");
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data" + File.separator + "user.ser"));
		System.out.println("loaded");
		ArrayList<User> users = (ArrayList<User>) ois.readObject();
		ois.close();
		return users;
	}
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Login");
        User admin = new User("admin");
        userbase.add(admin);
        admin = new User("stock");
        userbase.add(admin);
        try{
        	userbase = load();
        }
        catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
        
        loginTime();
    }
    
    public void loginTime(){
    	try {
	    	// Load login page
	    	FXMLLoader loader = new FXMLLoader();
	    	loader.setLocation(Photos.class.getResource("view/loginPage.fxml"));
	    	AnchorPane loginScreen = (AnchorPane) loader.load();
			
			loginPageController controller = loader.getController();
	        controller.setMain(this);
	        
	        Scene scene = new Scene(loginScreen);
	        primaryStage.setScene(scene);
	        primaryStage.show();
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void launchLanding(){
    	try{
    		FXMLLoader loader = new FXMLLoader();
    		if (user.getUsername().equals("admin")){
    			loader.setLocation(Photos.class.getResource("view/adminPage.fxml"));
    		}
    		else{
    			loader.setLocation(Photos.class.getResource("view/landingPage.fxml"));
    		}
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		Stage dialogStage = new Stage();
    	    dialogStage.setTitle(user.getUsername());
    	    dialogStage.initOwner(primaryStage);
    	    Scene scene = new Scene(page);
    	    dialogStage.setScene(scene);
    	    if (user.getUsername().equals("admin")){
    	    	adminPageController controller = loader.getController();
    	    	controller.setDialogStage(dialogStage);
        	    controller.setMain(this);
    	    }
    	    else{
    	    	landingPageController controller = loader.getController();
    	    	controller.setDialogStage(dialogStage);
        	    controller.setMain(this);
    	    }
            onClosing(dialogStage);
    	    dialogStage.showAndWait();
    	}
    	catch (IOException e){
   			e.printStackTrace();
   		}
    }
    
    public Stage getPrimaryStage(){
    	return primaryStage;
    }
    
    public void onClosing(Stage dialogStage){
    	dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
        	
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            	try {
					save(userbase);
					primaryStage.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            
        });
    	dialogStage.setOnHiding(new EventHandler<WindowEvent>() {
        	
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            	try {
					save(userbase);
					primaryStage.toFront();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            
        });
    }

    public static void main(String[] args){
    	launch(args);
    }
}