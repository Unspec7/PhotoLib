package photos25.view;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class newAlbumController {
	private landingPageController controls;
	private Stage dialogStage;
	@FXML private TextField albumName;
	@FXML private TextField imagePath;
	private String path;
	
	
	public void setController(landingPageController controls){
		this.controls = controls;
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	@FXML public void initialize(){
		albumName.clear();
		imagePath.clear();
		handleEnter();
	}
	
	private void showError(String error){
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Error");
        alert.setHeaderText("Following error has occured");
        alert.setContentText(error);
        alert.showAndWait();
    }
	
	@FXML private void onLoadImage(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Find image");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
				new ExtensionFilter("All Files", "*.*")
				);
		File selectedFile = fileChooser.showOpenDialog(dialogStage);
		try {
			if (selectedFile != null && ImageIO.read(selectedFile) != null){
				path = selectedFile.getAbsolutePath();
				imagePath.setText(path);
			}
			else{
				selectedFile = null;
				imagePath.clear();
				showError("File not an image");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void onCreate(){
		if (imagePath.getText().length() == 0 && albumName.getText().length() != 0){
			showError("No valid image loaded");
			return;
		}
		else if (imagePath.getText().length() != 0 && albumName.getText().length() == 0){
			showError("No album name detected");
			return;
		}
		else if (imagePath.getText().length() == 0 && albumName.getText().length() == 0){
			showError("No image and album name detected");
		}
		else{
			//Create code
		}
	}
	
	private void handleEnter(){
    	albumName.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)){
                    onCreate();
                }
            }
        });
    	
    	imagePath.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)){
                    onCreate();
                }
            }
        });

    }
}
