//*********************************************************************************//
//                               All Extensions v1.1                               //
//*********************************************************************************//

package jfxFilesRenamer.Extensions;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import jfxFilesRenamer.Main;

public class AllExtensions {

	public AllExtensions() {
		super();
	}

	// static {
	// CreateExtList();
	// }


	//**********************************************************************************
	//********************************* Public Methods *********************************
	//**********************************************************************************

	public static String getBaseName(String filename) {

		FilenameInformation filenameInformation = new FilenameInformation();

		//**********************************************************************************
		//Split the file name to parts
		//**********************************************************************************
		List<String> partsList = splitFilename(filename);


		//**********************************************************************************
		//Extract Base Filename & Extension
		//**********************************************************************************
		filenameInformation = extractBaseNameAndExtension(filename, partsList);


		//**********************************************************************************
		//Detect if is it Complexe or Simple Extension, then return Base Name
		//**********************************************************************************
		return extractBaseName(filenameInformation);
	}


	public static String getExtension(String filename) {

		FilenameInformation filenameInformation = new FilenameInformation();

		//**********************************************************************************
		//Split the file name to parts
		//**********************************************************************************
		List<String> partsList = splitFilename(filename);


		//**********************************************************************************
		//Extract Base Filename & Extension
		//**********************************************************************************
		filenameInformation = extractBaseNameAndExtension(filename, partsList);


		//**********************************************************************************
		//Detect if is it Complexe or Simple Extension, then return the Extension
		//**********************************************************************************
		return extractExtension(filenameInformation);
	}



	public static boolean hasExtension(String filename) {

		FilenameInformation filenameInformation = new FilenameInformation();

		//**********************************************************************************
		//Split the file name to parts
		//**********************************************************************************
		List<String> partsList = splitFilename(filename);

		//**********************************************************************************
		//Extract Base Filename & Extension
		//**********************************************************************************
		filenameInformation = extractBaseNameAndExtension(filename, partsList);

		//**********************************************************************************
		//Detect if is it Complexe or Simple Extension
		//**********************************************************************************
		return extractExtensionStatus(filenameInformation);

	}


	public static boolean isExtension(String extension) {

		try {
			if (extension.trim().isEmpty()) {
				return false;

			} else {
				char firstCharacter = extension.charAt(0);

				//**********************************************************************************
				// Search in Complexe
				//**********************************************************************************
				extensionValidation(extension, "Complexe");
				if (extensionValidation(extension, "Complexe"))
					return true;

				//**********************************************************************************
				// Search in Commons
				//**********************************************************************************
				if (extensionValidation(extension, "All Commons"))
					return true;

				//**********************************************************************************
				// Search in Alphanumeric files if not found previously
				//**********************************************************************************
				String extensionsFile = "";
				// if (Character.isLetter(TheFirstCharacter) && Character.UnicodeBlock.of(TheFirstCharacter) != Character.UnicodeBlock.ARABIC) {
				if (Character.isLetter(firstCharacter) && Character.UnicodeBlock.of(firstCharacter) == Character.UnicodeBlock.BASIC_LATIN) {
					extensionsFile = "Alphanumeric/".concat(String.valueOf(firstCharacter).toUpperCase());
				} else if (Character.isDigit(firstCharacter)) {
					extensionsFile = "Alphanumeric/Numerics";
				} else {
					extensionsFile = "Alphanumeric/Symbols";
				}

				if (extensionValidation(extension, extensionsFile))
					return true;

				//**********************************************************************************
				// Search in "All -Except Commons" if not found previously
				//**********************************************************************************
				if (extensionValidation(extension, "All -Except Commons"))
					return true;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return false;
	}







	//**********************************************************************************
	//********************************* Private Methods ********************************
	//**********************************************************************************

	private static List<String> splitFilename(String filename) {

		List<String> partsList = null;
		partsList = new ArrayList<String>(Arrays.asList(filename.split("\\.")));
		// ThePartsList.removeAll(Arrays.asList(""));
		partsList.removeAll(Arrays.asList(null,""));

		return partsList;
	}


	private static Boolean extensionValidation(String extension, String extensionsFile) {

		String extensionsFilePath = "Resources/Extensions/".concat(extensionsFile);

		InputStream is = Main.class.getResourceAsStream(extensionsFilePath);

		if (is != null) {		
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			// System.out.println(reader.lines().collect(Collectors.joining(System.lineSeparator())));
			return reader.lines().anyMatch(x -> Objects.equals(x, extension));		
		}

		return false;
	}


	private static FilenameInformation extractBaseNameAndExtension(String filename, List<String> partsList) {

		FilenameInformation filenameInformation = new FilenameInformation();

		if (partsList.size() == 0) {
			return filenameInformation;	
		}


		int partsListCount = 0;
		partsListCount = partsList.size();

		if (partsListCount == 0) {
			return filenameInformation;		

		} else if (partsListCount == 1) {
			filenameInformation.setBaseFilename_SimpleExt(partsList.get(0).trim());
			filenameInformation.setBaseFilename_ComplexeExt("");		
			filenameInformation.setSimpleExtension("");
			filenameInformation.setComplexeExtension("");

		} else if (partsListCount == 2) {
			filenameInformation.setBaseFilename_SimpleExt(partsList.get(0).trim());
			filenameInformation.setBaseFilename_ComplexeExt("");			
			filenameInformation.setSimpleExtension(partsList.get(1).trim());
			filenameInformation.setComplexeExtension("");

		} else if (partsListCount >= 3) {

			//Get the base name	(False for simple extension, True for complexe extension)			
			filenameInformation.setBaseFilename_SimpleExt(extractBaseName(partsList, false));
			filenameInformation.setBaseFilename_ComplexeExt(extractBaseName(partsList, true));

			//Get extension
			filenameInformation.setSimpleExtension(String.join(".", partsList.get(partsListCount-1).trim()));
			filenameInformation.setComplexeExtension(String.join(".", partsList.get(partsListCount-2).trim()
					.concat(".")
					.concat(partsList.get(partsListCount-1).trim())));
		}

		return filenameInformation;		
	}



	private static String extractBaseName(FilenameInformation filenameInformation) {

		if (!filenameInformation.getComplexeExtension().isEmpty()) {
			boolean isComplexe = isExtension(filenameInformation.getComplexeExtension());

			if (isComplexe) {
				return filenameInformation.getBaseFilename_ComplexeExt();
			} else {
				return filenameInformation.getBaseFilename_SimpleExt();
			}
		} else {
			return filenameInformation.getBaseFilename_SimpleExt();
		}

	}

	private static String extractBaseName(List<String> filenameParts, boolean isComplexeExt) {

		List<String> filenamePartsList = new ArrayList<>();
		int partsListCount = filenameParts.size();

		if (isComplexeExt) {
			for (int i = 0; i < partsListCount-2; i++) {
				filenamePartsList.add(filenameParts.get(i).trim());
			}
		} else {
			for (int i = 0; i < partsListCount-1; i++) {
				filenamePartsList.add(filenameParts.get(i).trim());
			}
		}

		return String.join(".", filenamePartsList);
	}


	private static boolean extractExtensionStatus(FilenameInformation filenameInformation) {

		if (!filenameInformation.getComplexeExtension().isEmpty()) {
			boolean isComplexe = isExtension(filenameInformation.getComplexeExtension());

			if (isComplexe) {
				return isComplexe;
			} else {
				return isExtension(filenameInformation.getSimpleExtension());
			}
		} else {
			return isExtension(filenameInformation.getSimpleExtension());
		}

	}


	private static String extractExtension(FilenameInformation filenameInformation) {

		if (!filenameInformation.getComplexeExtension().isEmpty()) {
			boolean isComplexe = isExtension(filenameInformation.getComplexeExtension());

			if (isComplexe) {
				return filenameInformation.getComplexeExtension();
			} else {
				return filenameInformation.getSimpleExtension();
			}
		} else {
			return filenameInformation.getSimpleExtension();
		}
	}

}

