package jfxFilesRenamer.Forms;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import jfxFilesRenamer.Main;

public class ControllerAbout implements Initializable {

	@FXML private AnchorPane anchorPane; 
	@FXML private Label Label_Logo;
	@FXML private Label Label_Basmala;
	@FXML private Label Label_ApplicationTitle;
	@FXML private Hyperlink Hyperlink_Site;

	public ControllerAbout() {

	}


	//**************************************************************
	//************************ initialize **************************
	//**************************************************************	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		InputStream is = Main.class.getResourceAsStream("Resources/Fonts/Maghribi_2.ttf");
		Font font = Font.loadFont(is, 26);
		Label_Basmala.setFont(Font.font(font.getName(), FontWeight.BOLD, 26));
		Label_ApplicationTitle.setFont(Font.font(font.getName(), FontWeight.BOLD, 20));
		Label_Basmala.setTextFill(Color.BLACK);
		Label_ApplicationTitle.setTextFill(Color.BLUE);


		final Image image_Logo = new Image(Main.class.getResource("Resources/Images/Logo.png").toString(), 80, 80, true, true);
		Label_Logo.setText("");
		Label_Logo.setGraphic(new ImageView(image_Logo));



		Hyperlink_Site.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				Application application = new Application() {
					@Override
					public void start(Stage primaryStage) throws Exception { }
				};
				
				application.getHostServices().showDocument("https://www.google.com/");
			}
		});
	}

}
