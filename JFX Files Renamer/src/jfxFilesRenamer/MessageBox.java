package jfxFilesRenamer;

import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;


public class MessageBox {

	// This Class is not complete //
	
		
	public static Alert createMessageBox(AlertType type, String title, String headerText, String contentText,
			double width, double height, NodeOrientation orientation) {
		
		Alert alert = new Alert(type);
		
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		
		// alert.getDialogPane().setPrefSize(width, height);
		alert.getDialogPane().getScene().setNodeOrientation(orientation);

		TextArea textArea = new TextArea(contentText);
		textArea.setEditable(false);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		// alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setContent(expContent);

		return alert;
		
	}
	
	
	
	
	public static Alert createMessageBox(AlertType type, String title, String headerText, String contentText, NodeOrientation orientation) {
		
		Alert alert = new Alert(type);
		
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText("");
		
		alert.getDialogPane().getScene().setNodeOrientation(orientation);

		TextArea textArea = new TextArea(contentText);
		textArea.setEditable(false);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		// alert.getDialogPane().setExpandableContent(expContent);
		alert.getDialogPane().setContent(expContent);

		return alert;
	}

}
