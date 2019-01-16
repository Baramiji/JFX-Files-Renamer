package jfxFilesRenamer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		// LCD anti aliasing texts
		System.setProperty("prism.lcdtext", "false");

		// Create folders
		MiscellaneousMethods.CreateFolders();


		try {
			Parent root = FXMLLoader.load(getClass().getResource("Forms/FormMain.fxml"));			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("CSS/application.css").toExternalForm());
			primaryStage.setTitle("JFX Files Renamer v1.0 Beta 1");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Resources/Images/Logo.png")));
			primaryStage.setMinWidth(1057);
			primaryStage.setMinHeight(733);
			primaryStage.setMaximized(true);
			primaryStage.setScene(scene);	
			primaryStage.show();			
		} catch(Exception e) {
			//e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}


}
