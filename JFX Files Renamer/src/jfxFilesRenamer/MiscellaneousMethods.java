package jfxFilesRenamer;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import jfxFilesRenamer.Enumerators.Validation;
import jfxFilesRenamer.Stores.Store_Files;
import jfxFilesRenamer.Stores.Store_NameValidator;



public class MiscellaneousMethods {
	
	
	public static void CreateFolders() {

		final String applicationLocation = ClassBaseLocation.getBaseLocation(Main.class);
		
		// Create a folder to save renaming histories
		final File historyFolder = Paths.get(File.separator, applicationLocation, "JFX Files Renamer History").toFile();
		if (!historyFolder.exists()) 
			historyFolder.mkdirs();
	}
	
	
	public static Alert createMessageBox(AlertType Type, String Title, String HeaderText, String ContentText) {
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		Alert alert = null;
		try {
			alert = new Alert(Type);
			alert.setTitle(Title);
			alert.setHeaderText(HeaderText);
			alert.setContentText(ContentText);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return alert;
	}


	public static Alert createMessageBox(AlertType type, String title, String headerText, String contentText,
			double width, double height, NodeOrientation orientation) {
		// https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Alert.html
		Alert alert = null;
		try {
			alert = new Alert(type);
			alert.setTitle(title);
			alert.setHeaderText(headerText);
			alert.setContentText(contentText);
			alert.getDialogPane().setPrefSize(width, height);
			alert.getDialogPane().getScene().setNodeOrientation(orientation);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}

		return alert;
	}
	

	public static HashMap<String, Integer> getParentFoldersMap(ObservableList<Store_Files> obL_Files) {

		HashMap<String, Integer> parentFoldersMap = new HashMap<>();

		for (Store_Files store_Files : obL_Files) {
			if (!parentFoldersMap.containsKey(store_Files.getParentFolder())) 
				parentFoldersMap.put(store_Files.getParentFolder(), 0);
		}

		return parentFoldersMap;
	}


	public static boolean isValidName(String text) {
		Pattern pattern = Pattern.compile(
				"# Match a valid Windows filename (unspecified file system).          \n" +
						"^                                # Anchor to start of string.        \n" +
						"(?!                              # Assert filename is not: CON, PRN, \n" +
						"  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n" +
						"    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n" +
						"    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n" +
						"  )                              # LPT6, LPT7, LPT8, and LPT9...     \n" +
						"  (?:\\.[^.]*)?                  # followed by optional extension    \n" +
						"  $                              # and end of string                 \n" +
						")                                # End negative lookahead assertion. \n" +
						"[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n" +
						// "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n" +
						"[^<>:\"/\\\\|?*\\x00-\\x1F\\.]  # Last char is not a dot.  \n" +
						"$                                # Anchor to end of string.            ", 
						Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(text);
		boolean isMatch = matcher.matches();
		return isMatch;
	}



	public static boolean isValidName(String text, boolean allowSpaceAndDotAtEnd) {

		Pattern pattern = null;

		if (allowSpaceAndDotAtEnd) {
			pattern = Pattern.compile(
					"# Match a valid Windows filename (unspecified file system).          \n" +
							"^                                # Anchor to start of string.        \n" +
							"(?!                              # Assert filename is not: CON, PRN, \n" +
							"  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n" +
							"    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n" +
							"    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n" +
							"  )                              # LPT6, LPT7, LPT8, and LPT9...     \n" +
							"  (?:\\.[^.]*)?                  # followed by optional extension    \n" +
							"  $                              # and end of string                 \n" +
							")                                # End negative lookahead assertion. \n" +
							"[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n" +
							// "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n" +
							"$                                # Anchor to end of string.            ", 
							Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		} else {
			pattern = Pattern.compile(
					"# Match a valid Windows filename (unspecified file system).          \n" +
							"^                                # Anchor to start of string.        \n" +
							"(?!                              # Assert filename is not: CON, PRN, \n" +
							"  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n" +
							"    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n" +
							"    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n" +
							"  )                              # LPT6, LPT7, LPT8, and LPT9...     \n" +
							"  (?:\\.[^.]*)?                  # followed by optional extension    \n" +
							"  $                              # and end of string                 \n" +
							")                                # End negative lookahead assertion. \n" +
							"[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n" +
							"[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n" +
							"$                                # Anchor to end of string.            ", 
							Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		}


		Matcher matcher = pattern.matcher(text);
		boolean isMatch = matcher.matches();
		return isMatch;
	}


	public static LocalDateTime getFileDateTime(String fileLocation, String fileDateType) {

		LocalDateTime localDT = LocalDateTime.parse("1900-01-01T00:00:00");
		BasicFileAttributes attr = null;

		try {
			attr = Files.readAttributes(new File(fileLocation).toPath(), BasicFileAttributes.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (fileDateType) {
			case "Creation" :
				localDT = attr.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				break;

			case "Modification" :
				localDT = attr.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				break;

			case "Access" :
				localDT = attr.lastAccessTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
				break;
			default :
				break;
		}

		return localDT;
	}



	public static Store_NameValidator filenameValidator(String originalFilename, String renamedFilename) {

		File renamedFile = Paths.get(renamedFilename).toFile();
		Store_NameValidator storeNameValidator = new Store_NameValidator();

		if (originalFilename.equals(renamedFilename)) {
			storeNameValidator.setValidation(false);
			storeNameValidator.setFileStatus(Validation.NEWNAMEEQUALSORIGINALNAME);
			storeNameValidator.setStatusMessage("No need to rename");
		} else if (renamedFilename.trim().isEmpty()) {
			storeNameValidator.setValidation(false);
			storeNameValidator.setFileStatus(Validation.NEWNAMEEMPTY);
			storeNameValidator.setStatusMessage("The new name is empty");
		} else if (!MiscellaneousMethods.isValidName(renamedFilename)) {
			storeNameValidator.setValidation(false);
			storeNameValidator.setFileStatus(Validation.NEWNAMEHASILLEGALCHARACTERS);
			storeNameValidator.setStatusMessage("The new name contains an invalid character or more");
		} else if (renamedFile.exists()) {
			storeNameValidator.setValidation(false);
			storeNameValidator.setFileStatus(Validation.NEWNAMEALREADYEXISTS);
			storeNameValidator.setStatusMessage("A file with the new name already exists");
		} else {
			storeNameValidator.setValidation(true);
			storeNameValidator.setFileStatus(Validation.VALID);
			storeNameValidator.setStatusMessage("Ready to be renamed");
		}

		return storeNameValidator;
	}

}
