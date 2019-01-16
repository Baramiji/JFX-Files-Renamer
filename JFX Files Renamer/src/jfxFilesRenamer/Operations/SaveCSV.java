package jfxFilesRenamer.Operations;

import java.io.File;
import java.io.FileWriter;

public class SaveCSV {


	//**************************************************************
	//*********************** Declarations *************************
	//**************************************************************
	private static final String COMMA_DELIMITER = ";";
	private static final String NEW_LINE_SEPARATOR = System.lineSeparator();
	private static final String FILE_HEADER =  String.format("\"%s\";\"%s\";\"%s\"", "Old Name", "New Name", "Parent Folder");



	//**************************************************************
	//************************** Methods ***************************
	//**************************************************************
	
	public static void createHeader(FileWriter fileWriter) {

		try {
			fileWriter.append(FILE_HEADER.toString());
			fileWriter.append(NEW_LINE_SEPARATOR);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void write(FileWriter fileWriter, String oldName, String newOld) {

		String parentFolder = new File(oldName).getParent();
		
		try {
			fileWriter.append(String.format("\"%s\"", oldName));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.format("\"%s\"", newOld));
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(String.format("\"%s\"", parentFolder));
			fileWriter.append(NEW_LINE_SEPARATOR);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
