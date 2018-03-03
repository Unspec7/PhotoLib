package photos25.view;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import photos25.Photos;
import photos25.model.*;

public class adminPageController {
	@FXML private TableView<User> listUsers;
	@FXML private TableColumn<User, String> usernameList;
	@FXML private TextField addUser;
	private ObservableList<User> tempList;
	private Stage dialogStage;
	
	private Photos photos;
	
	public void setMain(Photos photos){
    	this.photos = photos;
    	tempList = FXCollections.observableArrayList(photos.getUserBase());
    	listUsers.setItems(tempList);
    }
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	@FXML private void initialize(){
		usernameList.setCellValueFactory(cellData -> new SimpleStringProperty( cellData.getValue().getUsername() ));
		handleEnterkey();
	}
	
	private void handleEnterkey(){
		addUser.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    add();
                }
            }
        });
	}
	
	@FXML private void handleDeletekey(KeyEvent e){
		if (e.getCode().equals(KeyCode.DELETE)){
			delete();
		}
	}
	
	@FXML private void delete(){
		addUser.clear();
		User selectedItem = listUsers.getSelectionModel().getSelectedItem();
		if (selectedItem != null){
			if (selectedItem.getUsername().equals("admin")){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Errord");
				alert.setHeaderText("Cannot delete administrator");
				alert.showAndWait();
			}
			else if (selectedItem.getUsername().equals("stock")){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Errord");
				alert.setHeaderText("Cannot delete stock");
				alert.showAndWait();
			}
			else{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(dialogStage);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Are you sure you want to delete this user?");
				alert.showAndWait();
				
				if (alert.getResult() == ButtonType.OK){
					tempList.remove(selectedItem);
					photos.getUserBase().remove(selectedItem);
				}
			}
			
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(dialogStage);
			alert.setTitle("Error");
			alert.setHeaderText("A user was not selected for deletion");
			
			alert.showAndWait();
		}
	}
	@FXML private void add(){
		if (valid()){
			User newUser = new User( addUser.getText() );
			addUser.clear();
	    	if (contained(newUser)){
	    		showError("User already exists");
	    	}
	    	else{
	    		tempList.add(newUser);
	    		this.photos.getUserBase().add(newUser);
	    	}
		}
	}
	
	private boolean contained(User myUser){
    	for (User u: photos.getUserBase()){
    		if (u.getUsername().equals(myUser.getUsername()) ){
    			return true;
    		}
    	}
    	return false;
    }
	
	private boolean valid(){
    	String errorMessage = "";
    	if (addUser.getText() == null || addUser.getText().length() == 0){
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
