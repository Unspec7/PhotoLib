package photos25.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import photos25.Photos;
import photos25.model.*;

public class landingPageController {
	private Stage dialogStage;
	private Photos photos;
	private ArrayList<Album> userAlbums;
	@FXML private Label name;
	@FXML private Label size;
	@FXML private Label date;
	@FXML private TilePane tile;
	private User myUser;
	
	public void setMain(Photos photos){
    	this.photos = photos;
    	myUser = this.photos.getUser();
    	setAlbum();
    	loadAlbums();
    }
	
	private void labelUpdate(Album myAlbum){
		if (myAlbum != null){
			String temp = Integer.toString(myAlbum.sizeofAlbum());
			size.setText(temp);
			name.setText(myAlbum.getName());
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			
			temp = sdf.format( myAlbum.getYoungest().getDate() ) + " - " + sdf.format( myAlbum.getOldest().getDate() );
			
			date.setText(temp);
		}
		else{
			size.setText("");
			date.setText("");
			name.setText("");
		}
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
	
	@FXML public void initialize(){
		labelUpdate(null);
	}
	
	private void setAlbum(){
		this.userAlbums = myUser.getAlbumlist();
	}
	
	private void showError(String error){
        Alert alert = new Alert(AlertType.ERROR);
        alert.initOwner(dialogStage);
        alert.setTitle("Error");
        alert.setHeaderText("Following error has occured");
        alert.setContentText(error);
        alert.showAndWait();
    }
	
	private void loadAlbums(){
		for (Album a : userAlbums){
			ImageView newImageView;
			newImageView = createImage( a.getPhotos().get(0), a );
			tile.getChildren().addAll(newImageView);
		}
	}
	
	private ImageView createImage(Photo myPhoto, Album tempAlbum){
		ImageView imageView = null;
		String path = myPhoto.getPath();
		File tempImage = new File(path);
		
		try {
			final Image image = new Image(new FileInputStream(tempImage), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            launchPhotoDisplay(tempAlbum);
                        }
                        if(mouseEvent.getClickCount() == 1){
                        	//Update label if only clicked once
                        	labelUpdate(tempAlbum);
                        	}
                        }
                    }
                });
        }
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
		
		return imageView;
	}
	
	private void launchPhotoDisplay(Album myAlbum){
		try{
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Photos.class.getResource("view/photoDisplay.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		Stage photoStage = new Stage();
    	    photoStage.setTitle(myAlbum.getName());
    	    photoStage.initOwner(dialogStage);
    	    Scene scene = new Scene(page);
    	    photoStage.setScene(scene);
    	    photoDisplayController controller = loader.getController();
    	    controller.setDialogStage(photoStage);
    	    controller.setController(this);
    	    photoStage.showAndWait();
    	}
    	catch (IOException e){
   			e.printStackTrace();
   		}
	}
	
	public ArrayList<Album> getUserAlbums(){
		return userAlbums;
	}
	
	@FXML void logout(){
		dialogStage.close();
	}
	
	@FXML void create(){
		try{
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Photos.class.getResource("view/newAlbum.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		Stage photoStage = new Stage();
    	    photoStage.setTitle("New Album");
    	    photoStage.initOwner(dialogStage);
    	    Scene scene = new Scene(page);
    	    photoStage.setScene(scene);
    	    newAlbumController controller = loader.getController();
    	    controller.setDialogStage(photoStage);
    	    controller.setController(this);
    	    photoStage.showAndWait();
    	}
    	catch (IOException e){
   			e.printStackTrace();
   		}
	}
}
