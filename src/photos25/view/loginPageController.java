package photos25.view;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.stage.*;
import photos25.model.*;
import photos25.Photos;

public class loginPageController {
	@FXML private TextField username;
    private Stage dialogStage;
    private Photos photos;
    
    public loginPageController(){}
    
    @FXML void initialize(){
    	handleEnter();
    }
    
    private void handleEnter(){
    	username.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)){
                    onLogin();
                }
            }
        });
    }
    
    public void setMain(Photos photos){
    	this.photos = photos;
    }
    
    @FXML private void onLogin(){
    	if (valid()){
    		User temp = findUser(username.getText());
    		username.clear();
    		if (temp != null){
    			this.photos.setUser(temp);
    			this.photos.launchLanding();
    		}
    		else{
    			showError("User not found");
    		}
    	}
    }
    
    private User findUser(String name){
    	for (User u: photos.getUserBase()){
    		if (u.getUsername().equals(name)){
    			return u;
    		}
    	}
    	return null;
    }
    
    @FXML private void onCreate(){
    	if (valid()){
	    	User newUser = new User( username.getText() );
	    	username.clear();
	    	if (contained(newUser)){
	    		showError("User already exists");
	    	}
	    	else{
	    		this.photos.getUserBase().add(newUser);
	    		this.photos.setUser(newUser);
				this.photos.launchLanding();
	    	}
    	}
    }
    
    private boolean contained(User myUser){
    	for (User u: photos.getUserBase()){
    		if (u.getUsername().equals(myUser.getUsername())){
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean valid(){
    	String errorMessage = "";
    	if (username.getText() == null || username.getText().length() == 0){
    		errorMessage += "No username detected\n";
    	}
    	if (errorMessage.length() == 0) {
            return true;
        } else {
        	Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Login");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    private void showError(String error){
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Error");
        alert.setHeaderText("Following error has occured");
        alert.setContentText(error);
        alert.showAndWait();
    }
}
